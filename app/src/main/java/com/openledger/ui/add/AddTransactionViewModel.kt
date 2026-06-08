package com.openledger.ui.add

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.openledger.core.model.Transaction
import com.openledger.core.model.Category
import com.openledger.core.model.Account
import com.openledger.repository.TransactionRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import javax.inject.Inject

/**
 * 添加交易 UI 状态
 */
data class AddTransactionUiState(
    val type: Transaction.TransactionType = Transaction.TransactionType.EXPENSE,
    val amount: String = "",
    val selectedCategoryId: Long? = null,
    val selectedAccountId: Long? = null,
    val description: String = "",
    val isSaving: Boolean = false,
    val isSaved: Boolean = false,
    val errorMessage: String? = null
)

/**
 * 添加交易 ViewModel
 */
@HiltViewModel
class AddTransactionViewModel @Inject constructor(
    private val repository: TransactionRepository
) : ViewModel() {

    private val _uiState = MutableStateFlow(AddTransactionUiState())
    val uiState: StateFlow<AddTransactionUiState> = _uiState.asStateFlow()

    val categories: StateFlow<List<Category>> = repository.getAllCategories()
        .stateIn(viewModelScope, SharingStarted.Lazily, emptyList())

    val accounts: StateFlow<List<Account>> = repository.getVisibleAccounts()
        .stateIn(viewModelScope, SharingStarted.Lazily, emptyList())

    /**
     * 设置交易类型
     */
    fun setType(type: Transaction.TransactionType) {
        _uiState.value = _uiState.value.copy(type = type, selectedCategoryId = null)
    }

    /**
     * 设置金额
     */
    fun setAmount(amount: String) {
        _uiState.value = _uiState.value.copy(amount = amount)
    }

    /**
     * 设置分类
     */
    fun setCategory(categoryId: Long) {
        _uiState.value = _uiState.value.copy(selectedCategoryId = categoryId)
    }

    /**
     * 设置账户
     */
    fun setAccount(accountId: Long) {
        _uiState.value = _uiState.value.copy(selectedAccountId = accountId)
    }

    /**
     * 设置备注
     */
    fun setDescription(description: String) {
        _uiState.value = _uiState.value.copy(description = description)
    }

    /**
     * 保存交易记录
     */
    fun save() {
        val state = _uiState.value

        // 验证金额
        val amount = state.amount.toDoubleOrNull()
        if (amount == null || amount <= 0) {
            _uiState.value = state.copy(errorMessage = "请输入有效金额")
            return
        }

        _uiState.value = state.copy(isSaving = true)

        viewModelScope.launch {
            try {
                val transaction = when (state.type) {
                    Transaction.TransactionType.INCOME -> Transaction.createIncome(
                        amount = amount,
                        categoryId = state.selectedCategoryId,
                        accountId = state.selectedAccountId,
                        description = state.description.ifBlank { null }
                    )
                    Transaction.TransactionType.EXPENSE -> Transaction.createExpense(
                        amount = amount,
                        categoryId = state.selectedCategoryId,
                        accountId = state.selectedAccountId,
                        description = state.description.ifBlank { null }
                    )
                }
                repository.addTransaction(transaction)
                _uiState.value = _uiState.value.copy(isSaving = false, isSaved = true)
            } catch (e: Exception) {
                _uiState.value = _uiState.value.copy(
                    isSaving = false,
                    errorMessage = e.message
                )
            }
        }
    }

    /**
     * 清除错误
     */
    fun clearError() {
        _uiState.value = _uiState.value.copy(errorMessage = null)
    }
}
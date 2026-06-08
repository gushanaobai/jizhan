package com.openledger.ui.home

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
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import javax.inject.Inject

/**
 * 首页 UI 状态
 */
data class HomeUiState(
    val monthlyIncome: Double = 0.0,
    val monthlyExpense: Double = 0.0,
    val monthlyBalance: Double = 0.0,
    val totalBalance: Double = 0.0,
    val recentTransactions: List<Transaction> = emptyList(),
    val categories: List<Category> = emptyList(),
    val accounts: List<Account> = emptyList(),
    val isLoading: Boolean = true,
    val errorMessage: String? = null
)

/**
 * 首页 ViewModel
 *
 * 管理首页的业务逻辑和状态
 */
@HiltViewModel
class HomeViewModel @Inject constructor(
    private val repository: TransactionRepository
) : ViewModel() {

    private val _uiState = MutableStateFlow(HomeUiState())
    val uiState: StateFlow<HomeUiState> = _uiState.asStateFlow()

    init {
        loadData()
    }

    /**
     * 加载首页数据
     */
    private fun loadData() {
        viewModelScope.launch {
            _uiState.value = _uiState.value.copy(isLoading = true)

            try {
                // 加载本月收支数据
                combine(
                    repository.getThisMonthIncome(),
                    repository.getThisMonthExpense(),
                    repository.getThisMonthBalance(),
                    repository.getTotalBalance(),
                    repository.getRecentTransactions(10),
                    repository.getAllCategories(),
                    repository.getVisibleAccounts()
                ) { values ->
                    val monthlyIncome = values[0] as Double
                    val monthlyExpense = values[1] as Double
                    val monthlyBalance = values[2] as Double
                    val totalBalance = values[3] as Double
                    @Suppress("UNCHECKED_CAST")
                    val recentTransactions = values[4] as List<Transaction>
                    @Suppress("UNCHECKED_CAST")
                    val categories = values[5] as List<Category>
                    @Suppress("UNCHECKED_CAST")
                    val accounts = values[6] as List<Account>

                    HomeUiState(
                        monthlyIncome = monthlyIncome,
                        monthlyExpense = monthlyExpense,
                        monthlyBalance = monthlyBalance,
                        totalBalance = totalBalance,
                        recentTransactions = recentTransactions,
                        categories = categories,
                        accounts = accounts,
                        isLoading = false
                    )
                }.collect { state ->
                    _uiState.value = state
                }
            } catch (e: Exception) {
                _uiState.value = _uiState.value.copy(
                    isLoading = false,
                    errorMessage = e.message
                )
            }
        }
    }

    /**
     * 添加交易记录
     */
    fun addTransaction(
        type: Transaction.TransactionType,
        amount: Double,
        categoryId: Long?,
        accountId: Long?,
        description: String?
    ) {
        viewModelScope.launch {
            try {
                val transaction = when (type) {
                    Transaction.TransactionType.INCOME -> Transaction.createIncome(
                        amount = amount,
                        categoryId = categoryId,
                        accountId = accountId,
                        description = description
                    )
                    Transaction.TransactionType.EXPENSE -> Transaction.createExpense(
                        amount = amount,
                        categoryId = categoryId,
                        accountId = accountId,
                        description = description
                    )
                }
                repository.addTransaction(transaction)
            } catch (e: Exception) {
                _uiState.value = _uiState.value.copy(errorMessage = e.message)
            }
        }
    }

    /**
     * 删除交易记录
     */
    fun deleteTransaction(transaction: Transaction) {
        viewModelScope.launch {
            try {
                repository.deleteTransaction(transaction)
            } catch (e: Exception) {
                _uiState.value = _uiState.value.copy(errorMessage = e.message)
            }
        }
    }

    /**
     * 清除错误消息
     */
    fun clearError() {
        _uiState.value = _uiState.value.copy(errorMessage = null)
    }
}
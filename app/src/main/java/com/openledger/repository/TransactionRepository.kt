package com.openledger.repository

import com.openledger.core.database.dao.TransactionDao
import com.openledger.core.database.dao.CategoryDao
import com.openledger.core.database.dao.AccountDao
import com.openledger.core.model.Transaction
import com.openledger.core.model.Category
import com.openledger.core.model.Account
import com.openledger.core.common.utils.DateUtils
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.combine
import java.util.Date
import javax.inject.Inject
import javax.inject.Singleton

/**
 * 交易记录仓库
 *
 * 负责交易数据的业务逻辑处理
 */
@Singleton
class TransactionRepository @Inject constructor(
    private val transactionDao: TransactionDao,
    private val categoryDao: CategoryDao,
    private val accountDao: AccountDao
) {
    /**
     * 获取所有交易记录
     */
    fun getAllTransactions(): Flow<List<Transaction>> = transactionDao.getAll()

    /**
     * 获取最近的交易记录
     */
    fun getRecentTransactions(limit: Int = 10): Flow<List<Transaction>> =
        transactionDao.getRecent(limit)

    /**
     * 获取今日交易记录
     */
    fun getTodayTransactions(): Flow<List<Transaction>> {
        return transactionDao.getToday(DateUtils.getStartOfDay(), DateUtils.getEndOfDay())
    }

    /**
     * 获取本月交易记录
     */
    fun getThisMonthTransactions(): Flow<List<Transaction>> {
        return transactionDao.getThisMonth(DateUtils.getStartOfMonth(), DateUtils.getEndOfMonth())
    }

    /**
     * 获取本月收入
     */
    fun getThisMonthIncome(): Flow<Double> {
        return transactionDao.getThisMonthIncome(DateUtils.getStartOfMonth(), DateUtils.getEndOfMonth())
    }

    /**
     * 获取本月支出
     */
    fun getThisMonthExpense(): Flow<Double> {
        return transactionDao.getThisMonthExpense(DateUtils.getStartOfMonth(), DateUtils.getEndOfMonth())
    }

    /**
     * 获取本月结余
     */
    fun getThisMonthBalance(): Flow<Double> {
        return combine(getThisMonthIncome(), getThisMonthExpense()) { income, expense ->
            income - expense
        }
    }

    /**
     * 获取总收入
     */
    fun getTotalIncome(): Flow<Double> = transactionDao.getTotalIncome()

    /**
     * 获取总支出
     */
    fun getTotalExpense(): Flow<Double> = transactionDao.getTotalExpense()

    /**
     * 获取总余额
     */
    fun getTotalBalance(): Flow<Double> {
        return combine(getTotalIncome(), getTotalExpense()) { income, expense ->
            income - expense
        }
    }

    /**
     * 添加交易记录
     */
    suspend fun addTransaction(transaction: Transaction): Long {
        val id = transactionDao.insert(transaction)
        // 更新账户余额
        transaction.accountId?.let { accountId ->
            val amount = if (transaction.isIncome) transaction.amount else -transaction.amount
            accountDao.addBalance(accountId, amount)
        }
        return id
    }

    /**
     * 更新交易记录
     */
    suspend fun updateTransaction(oldTransaction: Transaction, newTransaction: Transaction) {
        // 恢复原账户余额
        oldTransaction.accountId?.let { accountId ->
            val reverseAmount = if (oldTransaction.isIncome) -oldTransaction.amount else oldTransaction.amount
            accountDao.addBalance(accountId, reverseAmount)
        }
        // 更新交易记录
        transactionDao.update(newTransaction)
        // 更新新账户余额
        newTransaction.accountId?.let { accountId ->
            val amount = if (newTransaction.isIncome) newTransaction.amount else -newTransaction.amount
            accountDao.addBalance(accountId, amount)
        }
    }

    /**
     * 删除交易记录
     */
    suspend fun deleteTransaction(transaction: Transaction) {
        // 恢复账户余额
        transaction.accountId?.let { accountId ->
            val reverseAmount = if (transaction.isIncome) -transaction.amount else transaction.amount
            accountDao.addBalance(accountId, reverseAmount)
        }
        transactionDao.delete(transaction)
    }

    /**
     * 软删除交易记录
     */
    suspend fun softDeleteTransaction(id: Long) {
        val transaction = transactionDao.getById(id)
        transaction?.let {
            // 恢复账户余额
            it.accountId?.let { accountId ->
                val reverseAmount = if (it.isIncome) -it.amount else it.amount
                accountDao.addBalance(accountId, reverseAmount)
            }
            transactionDao.softDelete(id)
        }
    }

    /**
     * 搜索交易记录
     */
    fun searchTransactions(query: String): Flow<List<Transaction>> =
        transactionDao.search(query)

    /**
     * 按类型获取交易记录
     */
    fun getTransactionsByType(type: Transaction.TransactionType): Flow<List<Transaction>> =
        transactionDao.getByType(type)

    /**
     * 按分类获取交易记录
     */
    fun getTransactionsByCategory(categoryId: Long): Flow<List<Transaction>> =
        transactionDao.getByCategoryId(categoryId)

    /**
     * 按账户获取交易记录
     */
    fun getTransactionsByAccount(accountId: Long): Flow<List<Transaction>> =
        transactionDao.getByAccountId(accountId)

    /**
     * 获取交易记录总数
     */
    fun getTransactionCount(): Flow<Int> = transactionDao.getCount()

    /**
     * 获取所有分类
     */
    fun getAllCategories(): Flow<List<Category>> = categoryDao.getAll()

    /**
     * 按类型获取分类
     */
    fun getCategoriesByType(type: Transaction.TransactionType): Flow<List<Category>> =
        categoryDao.getVisibleByType(type)

    /**
     * 获取所有账户
     */
    fun getAllAccounts(): Flow<List<Account>> = accountDao.getAll()

    /**
     * 获取可见账户
     */
    fun getVisibleAccounts(): Flow<List<Account>> = accountDao.getVisible()
}
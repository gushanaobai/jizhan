package com.openledger.core.database.dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update
import com.openledger.core.model.Transaction
import kotlinx.coroutines.flow.Flow
import java.util.Date

/**
 * 交易记录 DAO
 *
 * 提供交易记录的数据访问方法
 */
@Dao
interface TransactionDao {

    /**
     * 插入交易记录
     *
     * @param transaction 交易记录
     * @return 插入的记录ID
     */
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(transaction: Transaction): Long

    /**
     * 批量插入交易记录
     *
     * @param transactions 交易记录列表
     */
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAll(transactions: List<Transaction>)

    /**
     * 更新交易记录
     *
     * @param transaction 交易记录
     */
    @Update
    suspend fun update(transaction: Transaction)

    /**
     * 删除交易记录
     *
     * @param transaction 交易记录
     */
    @Delete
    suspend fun delete(transaction: Transaction)

    /**
     * 根据ID删除交易记录
     *
     * @param id 记录ID
     */
    @Query("DELETE FROM transactions WHERE id = :id")
    suspend fun deleteById(id: Long)

    /**
     * 软删除交易记录
     *
     * @param id 记录ID
     */
    @Query("UPDATE transactions SET is_deleted = 1, updated_at = :updatedAt WHERE id = :id")
    suspend fun softDelete(id: Long, updatedAt: Date = Date())

    /**
     * 根据ID获取交易记录
     *
     * @param id 记录ID
     * @return 交易记录
     */
    @Query("SELECT * FROM transactions WHERE id = :id AND is_deleted = 0")
    suspend fun getById(id: Long): Transaction?

    /**
     * 根据ID获取交易记录（Flow）
     *
     * @param id 记录ID
     * @return 交易记录Flow
     */
    @Query("SELECT * FROM transactions WHERE id = :id AND is_deleted = 0")
    fun getByIdFlow(id: Long): Flow<Transaction?>

    /**
     * 获取所有交易记录
     *
     * @return 交易记录列表
     */
    @Query("SELECT * FROM transactions WHERE is_deleted = 0 ORDER BY date DESC")
    fun getAll(): Flow<List<Transaction>>

    /**
     * 获取所有交易记录（分页）
     *
     * @param limit 限制数量
     * @param offset 偏移量
     * @return 交易记录列表
     */
    @Query("SELECT * FROM transactions WHERE is_deleted = 0 ORDER BY date DESC LIMIT :limit OFFSET :offset")
    suspend fun getAll(limit: Int, offset: Int): List<Transaction>

    /**
     * 根据类型获取交易记录
     *
     * @param type 交易类型
     * @return 交易记录列表
     */
    @Query("SELECT * FROM transactions WHERE type = :type AND is_deleted = 0 ORDER BY date DESC")
    fun getByType(type: Transaction.TransactionType): Flow<List<Transaction>>

    /**
     * 根据分类ID获取交易记录
     *
     * @param categoryId 分类ID
     * @return 交易记录列表
     */
    @Query("SELECT * FROM transactions WHERE category_id = :categoryId AND is_deleted = 0 ORDER BY date DESC")
    fun getByCategoryId(categoryId: Long): Flow<List<Transaction>>

    /**
     * 根据账户ID获取交易记录
     *
     * @param accountId 账户ID
     * @return 交易记录列表
     */
    @Query("SELECT * FROM transactions WHERE account_id = :accountId AND is_deleted = 0 ORDER BY date DESC")
    fun getByAccountId(accountId: Long): Flow<List<Transaction>>

    /**
     * 根据日期范围获取交易记录
     *
     * @param startDate 开始日期
     * @param endDate 结束日期
     * @return 交易记录列表
     */
    @Query("SELECT * FROM transactions WHERE date BETWEEN :startDate AND :endDate AND is_deleted = 0 ORDER BY date DESC")
    fun getByDateRange(startDate: Date, endDate: Date): Flow<List<Transaction>>

    /**
     * 根据日期范围和类型获取交易记录
     *
     * @param startDate 开始日期
     * @param endDate 结束日期
     * @param type 交易类型
     * @return 交易记录列表
     */
    @Query("SELECT * FROM transactions WHERE date BETWEEN :startDate AND :endDate AND type = :type AND is_deleted = 0 ORDER BY date DESC")
    fun getByDateRangeAndType(startDate: Date, endDate: Date, type: Transaction.TransactionType): Flow<List<Transaction>>

    /**
     * 搜索交易记录
     *
     * @param query 搜索关键词
     * @return 交易记录列表
     */
    @Query("SELECT * FROM transactions WHERE (description LIKE '%' || :query || '%' OR id IN (SELECT id FROM transactions WHERE category_id IN (SELECT id FROM categories WHERE name LIKE '%' || :query || '%'))) AND is_deleted = 0 ORDER BY date DESC")
    fun search(query: String): Flow<List<Transaction>>

    /**
     * 获取交易记录总数
     *
     * @return 交易记录总数
     */
    @Query("SELECT COUNT(*) FROM transactions WHERE is_deleted = 0")
    fun getCount(): Flow<Int>

    /**
     * 获取指定日期范围的交易记录总数
     *
     * @param startDate 开始日期
     * @param endDate 结束日期
     * @return 交易记录总数
     */
    @Query("SELECT COUNT(*) FROM transactions WHERE date BETWEEN :startDate AND :endDate AND is_deleted = 0")
    suspend fun getCountByDateRange(startDate: Date, endDate: Date): Int

    /**
     * 获取总收入
     *
     * @return 总收入
     */
    @Query("SELECT COALESCE(SUM(amount), 0) FROM transactions WHERE type = 'INCOME' AND is_deleted = 0")
    fun getTotalIncome(): Flow<Double>

    /**
     * 获取总支出
     *
     * @return 总支出
     */
    @Query("SELECT COALESCE(SUM(amount), 0) FROM transactions WHERE type = 'EXPENSE' AND is_deleted = 0")
    fun getTotalExpense(): Flow<Double>

    /**
     * 获取指定日期范围的总收入
     *
     * @param startDate 开始日期
     * @param endDate 结束日期
     * @return 总收入
     */
    @Query("SELECT COALESCE(SUM(amount), 0) FROM transactions WHERE type = 'INCOME' AND date BETWEEN :startDate AND :endDate AND is_deleted = 0")
    suspend fun getTotalIncomeByDateRange(startDate: Date, endDate: Date): Double

    /**
     * 获取指定日期范围的总支出
     *
     * @param startDate 开始日期
     * @param endDate 结束日期
     * @return 总支出
     */
    @Query("SELECT COALESCE(SUM(amount), 0) FROM transactions WHERE type = 'EXPENSE' AND date BETWEEN :startDate AND :endDate AND is_deleted = 0")
    suspend fun getTotalExpenseByDateRange(startDate: Date, endDate: Date): Double

    /**
     * 获取指定分类的总金额
     *
     * @param categoryId 分类ID
     * @return 总金额
     */
    @Query("SELECT COALESCE(SUM(amount), 0) FROM transactions WHERE category_id = :categoryId AND is_deleted = 0")
    suspend fun getTotalByCategoryId(categoryId: Long): Double

    /**
     * 获取指定账户的总金额
     *
     * @param accountId 账户ID
     * @return 总金额
     */
    @Query("SELECT COALESCE(SUM(amount), 0) FROM transactions WHERE account_id = :accountId AND is_deleted = 0")
    suspend fun getTotalByAccountId(accountId: Long): Double

    /**
     * 获取最近的交易记录
     *
     * @param limit 限制数量
     * @return 交易记录列表
     */
    @Query("SELECT * FROM transactions WHERE is_deleted = 0 ORDER BY date DESC LIMIT :limit")
    fun getRecent(limit: Int = 10): Flow<List<Transaction>>

    /**
     * 获取今日交易记录
     *
     * @param startOfDay 今日开始时间
     * @param endOfDay 今日结束时间
     * @return 交易记录列表
     */
    @Query("SELECT * FROM transactions WHERE date BETWEEN :startOfDay AND :endOfDay AND is_deleted = 0 ORDER BY date DESC")
    fun getToday(startOfDay: Date, endOfDay: Date): Flow<List<Transaction>>

    /**
     * 获取本月交易记录
     *
     * @param startOfMonth 本月开始时间
     * @param endOfMonth 本月结束时间
     * @return 交易记录列表
     */
    @Query("SELECT * FROM transactions WHERE date BETWEEN :startOfMonth AND :endOfMonth AND is_deleted = 0 ORDER BY date DESC")
    fun getThisMonth(startOfMonth: Date, endOfMonth: Date): Flow<List<Transaction>>

    /**
     * 获取本月收入
     *
     * @param startOfMonth 本月开始时间
     * @param endOfMonth 本月结束时间
     * @return 本月收入
     */
    @Query("SELECT COALESCE(SUM(amount), 0) FROM transactions WHERE type = 'INCOME' AND date BETWEEN :startOfMonth AND :endOfMonth AND is_deleted = 0")
    fun getThisMonthIncome(startOfMonth: Date, endOfMonth: Date): Flow<Double>

    /**
     * 获取本月支出
     *
     * @param startOfMonth 本月开始时间
     * @param endOfMonth 本月结束时间
     * @return 本月支出
     */
    @Query("SELECT COALESCE(SUM(amount), 0) FROM transactions WHERE type = 'EXPENSE' AND date BETWEEN :startOfMonth AND :endOfMonth AND is_deleted = 0")
    fun getThisMonthExpense(startOfMonth: Date, endOfMonth: Date): Flow<Double>

    /**
     * 获取分类统计
     *
     * @param startDate 开始日期
     * @param endDate 结束日期
     * @param type 交易类型
     * @return 分类统计数据
     */
    @Query("""
        SELECT category_id, SUM(amount) as total 
        FROM transactions 
        WHERE date BETWEEN :startDate AND :endDate 
        AND type = :type 
        AND is_deleted = 0 
        GROUP BY category_id 
        ORDER BY total DESC
    """)
    suspend fun getCategoryStats(startDate: Date, endDate: Date, type: Transaction.TransactionType): List<CategoryStat>

    /**
     * 分类统计数据类
     */
    data class CategoryStat(
        val category_id: Long?,
        val total: Double
    )
}
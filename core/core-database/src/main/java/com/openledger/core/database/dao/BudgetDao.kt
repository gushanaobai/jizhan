package com.openledger.core.database.dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update
import com.openledger.core.model.Budget
import kotlinx.coroutines.flow.Flow
import java.util.Date

/**
 * 预算 DAO
 *
 * 提供预算的数据访问方法
 */
@Dao
interface BudgetDao {

    /**
     * 插入预算
     *
     * @param budget 预算
     * @return 插入的记录ID
     */
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(budget: Budget): Long

    /**
     * 批量插入预算
     *
     * @param budgets 预算列表
     */
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAll(budgets: List<Budget>)

    /**
     * 更新预算
     *
     * @param budget 预算
     */
    @Update
    suspend fun update(budget: Budget)

    /**
     * 删除预算
     *
     * @param budget 预算
     */
    @Delete
    suspend fun delete(budget: Budget)

    /**
     * 根据ID删除预算
     *
     * @param id 记录ID
     */
    @Query("DELETE FROM budgets WHERE id = :id")
    suspend fun deleteById(id: Long)

    /**
     * 根据ID获取预算
     *
     * @param id 记录ID
     * @return 预算
     */
    @Query("SELECT * FROM budgets WHERE id = :id")
    suspend fun getById(id: Long): Budget?

    /**
     * 根据ID获取预算（Flow）
     *
     * @param id 记录ID
     * @return 预算Flow
     */
    @Query("SELECT * FROM budgets WHERE id = :id")
    fun getByIdFlow(id: Long): Flow<Budget?>

    /**
     * 获取所有预算
     *
     * @return 预算列表
     */
    @Query("SELECT * FROM budgets ORDER BY start_date DESC")
    fun getAll(): Flow<List<Budget>>

    /**
     * 根据周期获取预算
     *
     * @param period 预算周期
     * @return 预算列表
     */
    @Query("SELECT * FROM budgets WHERE period = :period ORDER BY start_date DESC")
    fun getByPeriod(period: Budget.BudgetPeriod): Flow<List<Budget>>

    /**
     * 根据分类ID获取预算
     *
     * @param categoryId 分类ID
     * @return 预算列表
     */
    @Query("SELECT * FROM budgets WHERE category_id = :categoryId ORDER BY start_date DESC")
    fun getByCategoryId(categoryId: Long): Flow<List<Budget>>

    /**
     * 根据日期范围获取预算
     *
     * @param startDate 开始日期
     * @param endDate 结束日期
     * @return 预算列表
     */
    @Query("SELECT * FROM budgets WHERE start_date <= :endDate AND end_date >= :startDate ORDER BY start_date DESC")
    fun getByDateRange(startDate: Date, endDate: Date): Flow<List<Budget>>

    /**
     * 获取当前有效的预算
     *
     * @param currentDate 当前日期
     * @return 预算列表
     */
    @Query("SELECT * FROM budgets WHERE start_date <= :currentDate AND end_date >= :currentDate ORDER BY start_date DESC")
    fun getActive(currentDate: Date): Flow<List<Budget>>

    /**
     * 获取当前有效的总预算
     *
     * @param currentDate 当前日期
     * @return 预算列表
     */
    @Query("SELECT * FROM budgets WHERE start_date <= :currentDate AND end_date >= :currentDate AND category_id IS NULL ORDER BY start_date DESC")
    fun getActiveTotalBudget(currentDate: Date): Flow<List<Budget>>

    /**
     * 获取当前有效的分类预算
     *
     * @param currentDate 当前日期
     * @return 预算列表
     */
    @Query("SELECT * FROM budgets WHERE start_date <= :currentDate AND end_date >= :currentDate AND category_id IS NOT NULL ORDER BY start_date DESC")
    fun getActiveCategoryBudget(currentDate: Date): Flow<List<Budget>>

    /**
     * 获取预算总数
     *
     * @return 预算总数
     */
    @Query("SELECT COUNT(*) FROM budgets")
    fun getCount(): Flow<Int>

    /**
     * 获取指定周期的预算总数
     *
     * @param period 预算周期
     * @return 预算总数
     */
    @Query("SELECT COUNT(*) FROM budgets WHERE period = :period")
    suspend fun getCountByPeriod(period: Budget.BudgetPeriod): Int

    /**
     * 获取指定分类的预算总额
     *
     * @param categoryId 分类ID
     * @return 预算总额
     */
    @Query("SELECT COALESCE(SUM(amount), 0) FROM budgets WHERE category_id = :categoryId")
    suspend fun getTotalByCategoryId(categoryId: Long): Double

    /**
     * 获取指定日期范围的预算总额
     *
     * @param startDate 开始日期
     * @param endDate 结束日期
     * @return 预算总额
     */
    @Query("SELECT COALESCE(SUM(amount), 0) FROM budgets WHERE start_date <= :endDate AND end_date >= :startDate")
    suspend fun getTotalByDateRange(startDate: Date, endDate: Date): Double

    /**
     * 检查是否存在指定分类的预算
     *
     * @param categoryId 分类ID
     * @param startDate 开始日期
     * @param endDate 结束日期
     * @param excludeId 排除的预算ID
     * @return 是否存在
     */
    @Query("SELECT COUNT(*) FROM budgets WHERE category_id = :categoryId AND start_date <= :endDate AND end_date >= :startDate AND id != :excludeId")
    suspend fun existsByCategoryIdAndDateRange(categoryId: Long, startDate: Date, endDate: Date, excludeId: Long = 0): Boolean

    /**
     * 检查是否存在总预算
     *
     * @param startDate 开始日期
     * @param endDate 结束日期
     * @param excludeId 排除的预算ID
     * @return 是否存在
     */
    @Query("SELECT COUNT(*) FROM budgets WHERE category_id IS NULL AND start_date <= :endDate AND end_date >= :startDate AND id != :excludeId")
    suspend fun existsTotalBudgetByDateRange(startDate: Date, endDate: Date, excludeId: Long = 0): Boolean
}
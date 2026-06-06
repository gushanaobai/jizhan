package com.openledger.core.database.dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update
import com.openledger.core.model.Account
import kotlinx.coroutines.flow.Flow

/**
 * 账户 DAO
 *
 * 提供账户的数据访问方法
 */
@Dao
interface AccountDao {

    /**
     * 插入账户
     *
     * @param account 账户
     * @return 插入的记录ID
     */
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(account: Account): Long

    /**
     * 批量插入账户
     *
     * @param accounts 账户列表
     */
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAll(accounts: List<Account>)

    /**
     * 更新账户
     *
     * @param account 账户
     */
    @Update
    suspend fun update(account: Account)

    /**
     * 删除账户
     *
     * @param account 账户
     */
    @Delete
    suspend fun delete(account: Account)

    /**
     * 根据ID删除账户
     *
     * @param id 记录ID
     */
    @Query("DELETE FROM accounts WHERE id = :id")
    suspend fun deleteById(id: Long)

    /**
     * 根据ID获取账户
     *
     * @param id 记录ID
     * @return 账户
     */
    @Query("SELECT * FROM accounts WHERE id = :id")
    suspend fun getById(id: Long): Account?

    /**
     * 根据ID获取账户（Flow）
     *
     * @param id 记录ID
     * @return 账户Flow
     */
    @Query("SELECT * FROM accounts WHERE id = :id")
    fun getByIdFlow(id: Long): Flow<Account?>

    /**
     * 获取所有账户
     *
     * @return 账户列表
     */
    @Query("SELECT * FROM accounts ORDER BY sort_order ASC")
    fun getAll(): Flow<List<Account>>

    /**
     * 获取可见账户
     *
     * @return 账户列表
     */
    @Query("SELECT * FROM accounts WHERE is_visible = 1 ORDER BY sort_order ASC")
    fun getVisible(): Flow<List<Account>>

    /**
     * 根据类型获取账户
     *
     * @param type 账户类型
     * @return 账户列表
     */
    @Query("SELECT * FROM accounts WHERE type = :type ORDER BY sort_order ASC")
    fun getByType(type: Account.AccountType): Flow<List<Account>>

    /**
     * 根据名称获取账户
     *
     * @param name 账户名称
     * @return 账户
     */
    @Query("SELECT * FROM accounts WHERE name = :name LIMIT 1")
    suspend fun getByName(name: String): Account?

    /**
     * 搜索账户
     *
     * @param query 搜索关键词
     * @return 账户列表
     */
    @Query("SELECT * FROM accounts WHERE name LIKE '%' || :query || '%' ORDER BY sort_order ASC")
    fun search(query: String): Flow<List<Account>>

    /**
     * 获取账户总数
     *
     * @return 账户总数
     */
    @Query("SELECT COUNT(*) FROM accounts")
    fun getCount(): Flow<Int>

    /**
     * 获取指定类型的账户总数
     *
     * @param type 账户类型
     * @return 账户总数
     */
    @Query("SELECT COUNT(*) FROM accounts WHERE type = :type")
    suspend fun getCountByType(type: Account.AccountType): Int

    /**
     * 获取总余额
     *
     * @return 总余额
     */
    @Query("SELECT COALESCE(SUM(balance), 0) FROM accounts")
    fun getTotalBalance(): Flow<Double>

    /**
     * 更新账户余额
     *
     * @param id 账户ID
     * @param balance 新余额
     */
    @Query("UPDATE accounts SET balance = :balance WHERE id = :id")
    suspend fun updateBalance(id: Long, balance: Double)

    /**
     * 增加账户余额
     *
     * @param id 账户ID
     * @param amount 增加金额
     */
    @Query("UPDATE accounts SET balance = balance + :amount WHERE id = :id")
    suspend fun addBalance(id: Long, amount: Double)

    /**
     * 减少账户余额
     *
     * @param id 账户ID
     * @param amount 减少金额
     */
    @Query("UPDATE accounts SET balance = balance - :amount WHERE id = :id")
    suspend fun subtractBalance(id: Long, amount: Double)

    /**
     * 更新账户排序
     *
     * @param id 账户ID
     * @param sortOrder 排序顺序
     */
    @Query("UPDATE accounts SET sort_order = :sortOrder WHERE id = :id")
    suspend fun updateSortOrder(id: Long, sortOrder: Int)

    /**
     * 更新账户可见性
     *
     * @param id 账户ID
     * @param isVisible 是否可见
     */
    @Query("UPDATE accounts SET is_visible = :isVisible WHERE id = :id")
    suspend fun updateVisibility(id: Long, isVisible: Boolean)

    /**
     * 检查账户名称是否存在
     *
     * @param name 账户名称
     * @param excludeId 排除的账户ID
     * @return 是否存在
     */
    @Query("SELECT COUNT(*) FROM accounts WHERE name = :name AND id != :excludeId")
    suspend fun isNameExists(name: String, excludeId: Long = 0): Boolean
}
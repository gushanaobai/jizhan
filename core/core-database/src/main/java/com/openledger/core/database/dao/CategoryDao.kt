package com.openledger.core.database.dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update
import com.openledger.core.model.Category
import com.openledger.core.model.Transaction
import kotlinx.coroutines.flow.Flow

/**
 * 分类 DAO
 *
 * 提供分类的数据访问方法
 */
@Dao
interface CategoryDao {

    /**
     * 插入分类
     *
     * @param category 分类
     * @return 插入的记录ID
     */
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(category: Category): Long

    /**
     * 批量插入分类
     *
     * @param categories 分类列表
     */
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAll(categories: List<Category>)

    /**
     * 更新分类
     *
     * @param category 分类
     */
    @Update
    suspend fun update(category: Category)

    /**
     * 删除分类
     *
     * @param category 分类
     */
    @Delete
    suspend fun delete(category: Category)

    /**
     * 根据ID删除分类
     *
     * @param id 记录ID
     */
    @Query("DELETE FROM categories WHERE id = :id")
    suspend fun deleteById(id: Long)

    /**
     * 根据ID获取分类
     *
     * @param id 记录ID
     * @return 分类
     */
    @Query("SELECT * FROM categories WHERE id = :id")
    suspend fun getById(id: Long): Category?

    /**
     * 根据ID获取分类（Flow）
     *
     * @param id 记录ID
     * @return 分类Flow
     */
    @Query("SELECT * FROM categories WHERE id = :id")
    fun getByIdFlow(id: Long): Flow<Category?>

    /**
     * 获取所有分类
     *
     * @return 分类列表
     */
    @Query("SELECT * FROM categories ORDER BY sort_order ASC")
    fun getAll(): Flow<List<Category>>

    /**
     * 根据类型获取分类
     *
     * @param type 交易类型
     * @return 分类列表
     */
    @Query("SELECT * FROM categories WHERE type = :type ORDER BY sort_order ASC")
    fun getByType(type: Transaction.TransactionType): Flow<List<Category>>

    /**
     * 根据类型获取可见分类
     *
     * @param type 交易类型
     * @return 分类列表
     */
    @Query("SELECT * FROM categories WHERE type = :type AND is_visible = 1 ORDER BY sort_order ASC")
    fun getVisibleByType(type: Transaction.TransactionType): Flow<List<Category>>

    /**
     * 获取父分类（无父分类的分类）
     *
     * @param type 交易类型
     * @return 分类列表
     */
    @Query("SELECT * FROM categories WHERE type = :type AND parent_id IS NULL ORDER BY sort_order ASC")
    fun getParentCategories(type: Transaction.TransactionType): Flow<List<Category>>

    /**
     * 获取子分类
     *
     * @param parentId 父分类ID
     * @return 分类列表
     */
    @Query("SELECT * FROM categories WHERE parent_id = :parentId ORDER BY sort_order ASC")
    fun getSubcategories(parentId: Long): Flow<List<Category>>

    /**
     * 根据名称获取分类
     *
     * @param name 分类名称
     * @return 分类
     */
    @Query("SELECT * FROM categories WHERE name = :name LIMIT 1")
    suspend fun getByName(name: String): Category?

    /**
     * 搜索分类
     *
     * @param query 搜索关键词
     * @return 分类列表
     */
    @Query("SELECT * FROM categories WHERE name LIKE '%' || :query || '%' ORDER BY sort_order ASC")
    fun search(query: String): Flow<List<Category>>

    /**
     * 获取分类总数
     *
     * @return 分类总数
     */
    @Query("SELECT COUNT(*) FROM categories")
    fun getCount(): Flow<Int>

    /**
     * 获取指定类型的分类总数
     *
     * @param type 交易类型
     * @return 分类总数
     */
    @Query("SELECT COUNT(*) FROM categories WHERE type = :type")
    suspend fun getCountByType(type: Transaction.TransactionType): Int

    /**
     * 获取默认分类
     *
     * @param type 交易类型
     * @return 默认分类列表
     */
    @Query("SELECT * FROM categories WHERE type = :type AND is_default = 1 ORDER BY sort_order ASC")
    fun getDefaultCategories(type: Transaction.TransactionType): Flow<List<Category>>

    /**
     * 更新分类排序
     *
     * @param id 分类ID
     * @param sortOrder 排序顺序
     */
    @Query("UPDATE categories SET sort_order = :sortOrder WHERE id = :id")
    suspend fun updateSortOrder(id: Long, sortOrder: Int)

    /**
     * 更新分类可见性
     *
     * @param id 分类ID
     * @param isVisible 是否可见
     */
    @Query("UPDATE categories SET is_visible = :isVisible WHERE id = :id")
    suspend fun updateVisibility(id: Long, isVisible: Boolean)

    /**
     * 检查分类名称是否存在
     *
     * @param name 分类名称
     * @param excludeId 排除的分类ID
     * @return 是否存在
     */
    @Query("SELECT COUNT(*) FROM categories WHERE name = :name AND id != :excludeId")
    suspend fun isNameExists(name: String, excludeId: Long = 0): Boolean
}
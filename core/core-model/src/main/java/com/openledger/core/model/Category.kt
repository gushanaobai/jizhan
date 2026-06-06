package com.openledger.core.model

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.Index
import androidx.room.PrimaryKey
import java.util.Date

/**
 * 分类实体类
 *
 * 存储收入和支出的分类信息
 */
@Entity(
    tableName = "categories",
    indices = [
        Index(value = ["type"]),
        Index(value = ["parent_id"])
    ],
    foreignKeys = [
        ForeignKey(
            entity = Category::class,
            parentColumns = ["id"],
            childColumns = ["parent_id"],
            onDelete = ForeignKey.SET_NULL
        )
    ]
)
data class Category(
    @PrimaryKey(autoGenerate = true)
    val id: Long = 0,

    @ColumnInfo(name = "name")
    val name: String,

    @ColumnInfo(name = "type")
    val type: Transaction.TransactionType,

    @ColumnInfo(name = "icon")
    val icon: String? = null,

    @ColumnInfo(name = "color")
    val color: String? = null,

    @ColumnInfo(name = "parent_id")
    val parentId: Long? = null,

    @ColumnInfo(name = "sort_order")
    val sortOrder: Int = 0,

    @ColumnInfo(name = "is_default")
    val isDefault: Boolean = false,

    @ColumnInfo(name = "is_visible")
    val isVisible: Boolean = true,

    @ColumnInfo(name = "created_at")
    val createdAt: Date = Date()
) {
    companion object {
        /**
         * 创建收入分类
         *
         * @param name 分类名称
         * @param icon 图标
         * @param color 颜色
         * @param parentId 父分类ID
         * @param sortOrder 排序顺序
         * @param isDefault 是否默认
         * @return 收入分类
         */
        fun createIncome(
            name: String,
            icon: String? = null,
            color: String? = null,
            parentId: Long? = null,
            sortOrder: Int = 0,
            isDefault: Boolean = false
        ): Category {
            return Category(
                name = name,
                type = Transaction.TransactionType.INCOME,
                icon = icon,
                color = color,
                parentId = parentId,
                sortOrder = sortOrder,
                isDefault = isDefault
            )
        }

        /**
         * 创建支出分类
         *
         * @param name 分类名称
         * @param icon 图标
         * @param color 颜色
         * @param parentId 父分类ID
         * @param sortOrder 排序顺序
         * @param isDefault 是否默认
         * @return 支出分类
         */
        fun createExpense(
            name: String,
            icon: String? = null,
            color: String? = null,
            parentId: Long? = null,
            sortOrder: Int = 0,
            isDefault: Boolean = false
        ): Category {
            return Category(
                name = name,
                type = Transaction.TransactionType.EXPENSE,
                icon = icon,
                color = color,
                parentId = parentId,
                sortOrder = sortOrder,
                isDefault = isDefault
            )
        }
    }

    /**
     * 是否是收入分类
     */
    val isIncome: Boolean
        get() = type == Transaction.TransactionType.INCOME

    /**
     * 是否是支出分类
     */
    val isExpense: Boolean
        get() = type == Transaction.TransactionType.EXPENSE

    /**
     * 是否是子分类
     */
    val isSubcategory: Boolean
        get() = parentId != null

    /**
     * 更新分类
     *
     * @param name 分类名称
     * @param icon 图标
     * @param color 颜色
     * @param parentId 父分类ID
     * @param sortOrder 排序顺序
     * @param isDefault 是否默认
     * @param isVisible 是否可见
     * @return 更新后的分类
     */
    fun update(
        name: String = this.name,
        icon: String? = this.icon,
        color: String? = this.color,
        parentId: Long? = this.parentId,
        sortOrder: Int = this.sortOrder,
        isDefault: Boolean = this.isDefault,
        isVisible: Boolean = this.isVisible
    ): Category {
        return copy(
            name = name,
            icon = icon,
            color = color,
            parentId = parentId,
            sortOrder = sortOrder,
            isDefault = isDefault,
            isVisible = isVisible
        )
    }
}
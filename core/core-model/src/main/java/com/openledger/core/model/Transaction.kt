package com.openledger.core.model

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.Index
import androidx.room.PrimaryKey
import java.util.Date

/**
 * 交易记录实体类
 *
 * 存储收入和支出交易记录
 */
@Entity(
    tableName = "transactions",
    indices = [
        Index(value = ["date"]),
        Index(value = ["category_id"]),
        Index(value = ["account_id"]),
        Index(value = ["type"])
    ],
    foreignKeys = [
        ForeignKey(
            entity = Category::class,
            parentColumns = ["id"],
            childColumns = ["category_id"],
            onDelete = ForeignKey.SET_NULL
        ),
        ForeignKey(
            entity = Account::class,
            parentColumns = ["id"],
            childColumns = ["account_id"],
            onDelete = ForeignKey.SET_NULL
        )
    ]
)
data class Transaction(
    @PrimaryKey(autoGenerate = true)
    val id: Long = 0,

    @ColumnInfo(name = "type")
    val type: TransactionType,

    @ColumnInfo(name = "amount")
    val amount: Double,

    @ColumnInfo(name = "category_id")
    val categoryId: Long?,

    @ColumnInfo(name = "account_id")
    val accountId: Long?,

    @ColumnInfo(name = "description")
    val description: String? = null,

    @ColumnInfo(name = "date")
    val date: Date,

    @ColumnInfo(name = "created_at")
    val createdAt: Date = Date(),

    @ColumnInfo(name = "updated_at")
    val updatedAt: Date = Date(),

    @ColumnInfo(name = "is_deleted")
    val isDeleted: Boolean = false
) {
    /**
     * 交易类型
     */
    enum class TransactionType {
        INCOME,
        EXPENSE;

        companion object {
            /**
             * 从字符串解析交易类型
             *
             * @param value 字符串
             * @return 交易类型
             */
            fun fromString(value: String): TransactionType {
                return when (value.uppercase()) {
                    "INCOME" -> INCOME
                    "EXPENSE" -> EXPENSE
                    else -> throw IllegalArgumentException("Unknown transaction type: $value")
                }
            }
        }
    }

    companion object {
        /**
         * 创建收入交易
         *
         * @param amount 金额
         * @param categoryId 分类ID
         * @param accountId 账户ID
         * @param description 描述
         * @param date 日期
         * @return 收入交易记录
         */
        fun createIncome(
            amount: Double,
            categoryId: Long?,
            accountId: Long?,
            description: String? = null,
            date: Date = Date()
        ): Transaction {
            return Transaction(
                type = TransactionType.INCOME,
                amount = amount,
                categoryId = categoryId,
                accountId = accountId,
                description = description,
                date = date
            )
        }

        /**
         * 创建支出交易
         *
         * @param amount 金额
         * @param categoryId 分类ID
         * @param accountId 账户ID
         * @param description 描述
         * @param date 日期
         * @return 支出交易记录
         */
        fun createExpense(
            amount: Double,
            categoryId: Long?,
            accountId: Long?,
            description: String? = null,
            date: Date = Date()
        ): Transaction {
            return Transaction(
                type = TransactionType.EXPENSE,
                amount = amount,
                categoryId = categoryId,
                accountId = accountId,
                description = description,
                date = date
            )
        }
    }

    /**
     * 是否是收入
     */
    val isIncome: Boolean
        get() = type == TransactionType.INCOME

    /**
     * 是否是支出
     */
    val isExpense: Boolean
        get() = type == TransactionType.EXPENSE

    /**
     * 获取带符号的金额
     *
     * @return 带符号的金额
     */
    fun getSignedAmount(): Double {
        return when (type) {
            TransactionType.INCOME -> amount
            TransactionType.EXPENSE -> -amount
        }
    }

    /**
     * 更新交易记录
     *
     * @param type 交易类型
     * @param amount 金额
     * @param categoryId 分类ID
     * @param accountId 账户ID
     * @param description 描述
     * @param date 日期
     * @return 更新后的交易记录
     */
    fun update(
        type: TransactionType = this.type,
        amount: Double = this.amount,
        categoryId: Long? = this.categoryId,
        accountId: Long? = this.accountId,
        description: String? = this.description,
        date: Date = this.date
    ): Transaction {
        return copy(
            type = type,
            amount = amount,
            categoryId = categoryId,
            accountId = accountId,
            description = description,
            date = date,
            updatedAt = Date()
        )
    }

    /**
     * 软删除交易记录
     *
     * @return 软删除后的交易记录
     */
    fun softDelete(): Transaction {
        return copy(
            isDeleted = true,
            updatedAt = Date()
        )
    }
}
package com.openledger.core.model

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.Index
import androidx.room.PrimaryKey
import java.util.Date

/**
 * 预算实体类
 *
 * 存储预算信息
 */
@Entity(
    tableName = "budgets",
    indices = [
        Index(value = ["category_id"]),
        Index(value = ["period"])
    ],
    foreignKeys = [
        ForeignKey(
            entity = Category::class,
            parentColumns = ["id"],
            childColumns = ["category_id"],
            onDelete = ForeignKey.CASCADE
        )
    ]
)
data class Budget(
    @PrimaryKey(autoGenerate = true)
    val id: Long = 0,

    @ColumnInfo(name = "amount")
    val amount: Double,

    @ColumnInfo(name = "category_id")
    val categoryId: Long? = null,

    @ColumnInfo(name = "period")
    val period: BudgetPeriod,

    @ColumnInfo(name = "start_date")
    val startDate: Date,

    @ColumnInfo(name = "end_date")
    val endDate: Date,

    @ColumnInfo(name = "created_at")
    val createdAt: Date = Date()
) {
    /**
     * 预算周期
     */
    enum class BudgetPeriod {
        MONTHLY,
        QUARTERLY,
        YEARLY,
        CUSTOM;

        companion object {
            /**
             * 从字符串解析预算周期
             *
             * @param value 字符串
             * @return 预算周期
             */
            fun fromString(value: String): BudgetPeriod {
                return when (value.uppercase()) {
                    "MONTHLY" -> MONTHLY
                    "QUARTERLY" -> QUARTERLY
                    "YEARLY" -> YEARLY
                    "CUSTOM" -> CUSTOM
                    else -> throw IllegalArgumentException("Unknown budget period: $value")
                }
            }
        }
    }

    companion object {
        /**
         * 创建月度预算
         *
         * @param amount 预算金额
         * @param categoryId 分类ID（null 表示总预算）
         * @param year 年份
         * @param month 月份
         * @return 月度预算
         */
        fun createMonthly(
            amount: Double,
            categoryId: Long? = null,
            year: Int,
            month: Int
        ): Budget {
            val startDate = Date(year - 1900, month - 1, 1)
            val endDate = Date(year - 1900, month, 0)
            return Budget(
                amount = amount,
                categoryId = categoryId,
                period = BudgetPeriod.MONTHLY,
                startDate = startDate,
                endDate = endDate
            )
        }

        /**
         * 创建季度预算
         *
         * @param amount 预算金额
         * @param categoryId 分类ID（null 表示总预算）
         * @param year 年份
         * @param quarter 季度（1-4）
         * @return 季度预算
         */
        fun createQuarterly(
            amount: Double,
            categoryId: Long? = null,
            year: Int,
            quarter: Int
        ): Budget {
            val startMonth = (quarter - 1) * 3
            val startDate = Date(year - 1900, startMonth, 1)
            val endDate = Date(year - 1900, startMonth + 3, 0)
            return Budget(
                amount = amount,
                categoryId = categoryId,
                period = BudgetPeriod.QUARTERLY,
                startDate = startDate,
                endDate = endDate
            )
        }

        /**
         * 创建年度预算
         *
         * @param amount 预算金额
         * @param categoryId 分类ID（null 表示总预算）
         * @param year 年份
         * @return 年度预算
         */
        fun createYearly(
            amount: Double,
            categoryId: Long? = null,
            year: Int
        ): Budget {
            val startDate = Date(year - 1900, 0, 1)
            val endDate = Date(year - 1900, 11, 31)
            return Budget(
                amount = amount,
                categoryId = categoryId,
                period = BudgetPeriod.YEARLY,
                startDate = startDate,
                endDate = endDate
            )
        }
    }

    /**
     * 是否是总预算（无分类）
     */
    val isTotalBudget: Boolean
        get() = categoryId == null

    /**
     * 是否是分类预算
     */
    val isCategoryBudget: Boolean
        get() = categoryId != null

    /**
     * 获取预算周期名称
     *
     * @return 预算周期名称
     */
    fun getPeriodName(): String {
        return when (period) {
            BudgetPeriod.MONTHLY -> "月度"
            BudgetPeriod.QUARTERLY -> "季度"
            BudgetPeriod.YEARLY -> "年度"
            BudgetPeriod.CUSTOM -> "自定义"
        }
    }

    /**
     * 更新预算
     *
     * @param amount 预算金额
     * @param categoryId 分类ID
     * @param period 预算周期
     * @param startDate 开始日期
     * @param endDate 结束日期
     * @return 更新后的预算
     */
    fun update(
        amount: Double = this.amount,
        categoryId: Long? = this.categoryId,
        period: BudgetPeriod = this.period,
        startDate: Date = this.startDate,
        endDate: Date = this.endDate
    ): Budget {
        return copy(
            amount = amount,
            categoryId = categoryId,
            period = period,
            startDate = startDate,
            endDate = endDate
        )
    }

    /**
     * 计算预算使用百分比
     *
     * @param spent 已花费金额
     * @return 预算使用百分比
     */
    fun calculateUsagePercentage(spent: Double): Double {
        if (amount == 0.0) return 0.0
        return (spent / amount) * 100
    }

    /**
     * 计算剩余预算
     *
     * @param spent 已花费金额
     * @return 剩余预算
     */
    fun calculateRemaining(spent: Double): Double {
        return amount - spent
    }

    /**
     * 判断是否超支
     *
     * @param spent 已花费金额
     * @return 是否超支
     */
    fun isOverBudget(spent: Double): Boolean {
        return spent > amount
    }

    /**
     * 判断是否接近预算（超过80%）
     *
     * @param spent 已花费金额
     * @return 是否接近预算
     */
    fun isNearBudget(spent: Double): Boolean {
        val percentage = calculateUsagePercentage(spent)
        return percentage >= 80 && percentage < 100
    }

    /**
     * 判断是否在预算范围内
     *
     * @param spent 已花费金额
     * @return 是否在预算范围内
     */
    fun isWithinBudget(spent: Double): Boolean {
        return spent <= amount
    }
}
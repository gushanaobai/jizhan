package com.openledger.core.model

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import java.util.Date

/**
 * 账户实体类
 *
 * 存储资金账户信息
 */
@Entity(tableName = "accounts")
data class Account(
    @PrimaryKey(autoGenerate = true)
    val id: Long = 0,

    @ColumnInfo(name = "name")
    val name: String,

    @ColumnInfo(name = "type")
    val type: AccountType,

    @ColumnInfo(name = "icon")
    val icon: String? = null,

    @ColumnInfo(name = "color")
    val color: String? = null,

    @ColumnInfo(name = "balance")
    val balance: Double = 0.0,

    @ColumnInfo(name = "currency")
    val currency: String = "CNY",

    @ColumnInfo(name = "credit_limit")
    val creditLimit: Double? = null,

    @ColumnInfo(name = "sort_order")
    val sortOrder: Int = 0,

    @ColumnInfo(name = "is_visible")
    val isVisible: Boolean = true,

    @ColumnInfo(name = "created_at")
    val createdAt: Date = Date()
) {
    /**
     * 账户类型
     */
    enum class AccountType {
        CASH,
        DEBIT_CARD,
        CREDIT_CARD,
        ALIPAY,
        WECHAT,
        EWALLET,
        INVESTMENT,
        OTHER;

        companion object {
            /**
             * 从字符串解析账户类型
             *
             * @param value 字符串
             * @return 账户类型
             */
            fun fromString(value: String): AccountType {
                return when (value.uppercase()) {
                    "CASH" -> CASH
                    "DEBIT_CARD" -> DEBIT_CARD
                    "CREDIT_CARD" -> CREDIT_CARD
                    "ALIPAY" -> ALIPAY
                    "WECHAT" -> WECHAT
                    "EWALLET" -> EWALLET
                    "INVESTMENT" -> INVESTMENT
                    "OTHER" -> OTHER
                    else -> throw IllegalArgumentException("Unknown account type: $value")
                }
            }
        }
    }

    companion object {
        /**
         * 创建现金账户
         *
         * @param name 账户名称
         * @param balance 余额
         * @param currency 货币
         * @return 现金账户
         */
        fun createCash(
            name: String = "现金",
            balance: Double = 0.0,
            currency: String = "CNY"
        ): Account {
            return Account(
                name = name,
                type = AccountType.CASH,
                balance = balance,
                currency = currency
            )
        }

        /**
         * 创建储蓄卡账户
         *
         * @param name 账户名称
         * @param balance 余额
         * @param currency 货币
         * @return 储蓄卡账户
         */
        fun createDebitCard(
            name: String,
            balance: Double = 0.0,
            currency: String = "CNY"
        ): Account {
            return Account(
                name = name,
                type = AccountType.DEBIT_CARD,
                balance = balance,
                currency = currency
            )
        }

        /**
         * 创建信用卡账户
         *
         * @param name 账户名称
         * @param balance 余额
         * @param creditLimit 信用额度
         * @param currency 货币
         * @return 信用卡账户
         */
        fun createCreditCard(
            name: String,
            balance: Double = 0.0,
            creditLimit: Double? = null,
            currency: String = "CNY"
        ): Account {
            return Account(
                name = name,
                type = AccountType.CREDIT_CARD,
                balance = balance,
                creditLimit = creditLimit,
                currency = currency
            )
        }

        /**
         * 创建支付宝账户
         *
         * @param name 账户名称
         * @param balance 余额
         * @param currency 货币
         * @return 支付宝账户
         */
        fun createAlipay(
            name: String = "支付宝",
            balance: Double = 0.0,
            currency: String = "CNY"
        ): Account {
            return Account(
                name = name,
                type = AccountType.ALIPAY,
                balance = balance,
                currency = currency
            )
        }

        /**
         * 创建微信钱包账户
         *
         * @param name 账户名称
         * @param balance 余额
         * @param currency 货币
         * @return 微信钱包账户
         */
        fun createWechat(
            name: String = "微信钱包",
            balance: Double = 0.0,
            currency: String = "CNY"
        ): Account {
            return Account(
                name = name,
                type = AccountType.WECHAT,
                balance = balance,
                currency = currency
            )
        }
    }

    /**
     * 是否是现金账户
     */
    val isCash: Boolean
        get() = type == AccountType.CASH

    /**
     * 是否是银行卡账户
     */
    val isBankCard: Boolean
        get() = type == AccountType.DEBIT_CARD || type == AccountType.CREDIT_CARD

    /**
     * 是否是信用卡账户
     */
    val isCreditCard: Boolean
        get() = type == AccountType.CREDIT_CARD

    /**
     * 是否是电子钱包账户
     */
    val isEWallet: Boolean
        get() = type == AccountType.ALIPAY || type == AccountType.WECHAT || type == AccountType.EWALLET

    /**
     * 是否有信用额度
     */
    val hasCreditLimit: Boolean
        get() = creditLimit != null && creditLimit > 0

    /**
     * 获取可用余额
     *
     * @return 可用余额
     */
    fun getAvailableBalance(): Double {
        return if (isCreditCard && hasCreditLimit) {
            creditLimit!! + balance
        } else {
            balance
        }
    }

    /**
     * 获取账户类型名称
     *
     * @return 账户类型名称
     */
    fun getTypeName(): String {
        return when (type) {
            AccountType.CASH -> "现金"
            AccountType.DEBIT_CARD -> "储蓄卡"
            AccountType.CREDIT_CARD -> "信用卡"
            AccountType.ALIPAY -> "支付宝"
            AccountType.WECHAT -> "微信钱包"
            AccountType.EWALLET -> "电子钱包"
            AccountType.INVESTMENT -> "投资账户"
            AccountType.OTHER -> "其他"
        }
    }

    /**
     * 更新余额
     *
     * @param amount 金额（正数增加，负数减少）
     * @return 更新后的账户
     */
    fun updateBalance(amount: Double): Account {
        return copy(
            balance = balance + amount
        )
    }

    /**
     * 更新账户信息
     *
     * @param name 账户名称
     * @param icon 图标
     * @param color 颜色
     * @param creditLimit 信用额度
     * @param sortOrder 排序顺序
     * @param isVisible 是否可见
     * @return 更新后的账户
     */
    fun update(
        name: String = this.name,
        icon: String? = this.icon,
        color: String? = this.color,
        creditLimit: Double? = this.creditLimit,
        sortOrder: Int = this.sortOrder,
        isVisible: Boolean = this.isVisible
    ): Account {
        return copy(
            name = name,
            icon = icon,
            color = color,
            creditLimit = creditLimit,
            sortOrder = sortOrder,
            isVisible = isVisible
        )
    }
}
package com.openledger.ui.components

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material.icons.outlined.*
import androidx.compose.ui.graphics.vector.ImageVector

/**
 * 分类图标映射
 *
 * 根据分类名称返回对应的 Material Icons
 */
object CategoryIcons {

    private val expenseIconMap = mapOf(
        "餐饮美食" to Icons.Filled.Restaurant,
        "交通出行" to Icons.Filled.DirectionsCar,
        "日用百货" to Icons.Filled.ShoppingCart,
        "购物消费" to Icons.Filled.ShoppingBag,
        "娱乐休闲" to Icons.Filled.SportsEsports,
        "医疗健康" to Icons.Filled.LocalHospital,
        "教育培训" to Icons.Filled.School,
        "居住生活" to Icons.Filled.Home,
        "通讯物流" to Icons.Filled.Phone,
        "人情往来" to Icons.Filled.CardGiftcard,
        "金融保险" to Icons.Filled.AccountBalance,
        "其他支出" to Icons.Filled.MoreHoriz
    )

    private val incomeIconMap = mapOf(
        "工资薪水" to Icons.Filled.Work,
        "奖金津贴" to Icons.Filled.EmojiEvents,
        "投资理财" to Icons.Filled.TrendingUp,
        "兼职副业" to Icons.Filled.BusinessCenter,
        "礼金红包" to Icons.Filled.Redeem,
        "退款返利" to Icons.Filled.Replay,
        "其他收入" to Icons.Filled.MoreHoriz
    )

    private val accountIconMap = mapOf(
        "现金" to Icons.Filled.Payments,
        "储蓄卡" to Icons.Filled.CreditCard,
        "信用卡" to Icons.Filled.CreditCard,
        "支付宝" to Icons.Filled.AccountBalanceWallet,
        "微信钱包" to Icons.Filled.AccountBalanceWallet,
        "电子钱包" to Icons.Filled.AccountBalanceWallet,
        "投资账户" to Icons.Filled.ShowChart,
        "其他" to Icons.Filled.AccountBalance
    )

    /**
     * 获取分类图标
     */
    fun getCategoryIcon(categoryName: String, isIncome: Boolean): ImageVector {
        return if (isIncome) {
            incomeIconMap[categoryName] ?: Icons.Filled.AttachMoney
        } else {
            expenseIconMap[categoryName] ?: Icons.Filled.Receipt
        }
    }

    /**
     * 获取账户图标
     */
    fun getAccountIcon(accountName: String): ImageVector {
        return accountIconMap[accountName] ?: Icons.Filled.AccountBalance
    }
}
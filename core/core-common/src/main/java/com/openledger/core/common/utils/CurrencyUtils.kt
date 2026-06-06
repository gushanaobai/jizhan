package com.openledger.core.common.utils

import java.text.DecimalFormat
import java.text.NumberFormat
import java.util.Currency
import java.util.Locale

/**
 * 货币工具类
 *
 * 提供货币格式化相关的工具方法
 */
object CurrencyUtils {

    /**
     * 货币符号常量
     */
    object Symbols {
        const val CNY = "¥"
        const val USD = "$"
        const val EUR = "€"
        const val GBP = "£"
        const val JPY = "¥"
        const val KRW = "₩"
        const val TWD = "NT$"
        const val HKD = "HK$"
        const val SGD = "S$"
        const val AUD = "A$"
        const val CAD = "C$"
        const val CHF = "CHF"
        const val SEK = "kr"
        const val NOK = "kr"
        const val DKK = "kr"
        const val NZD = "NZ$"
    }

    /**
     * 货币代码常量
     */
    object Codes {
        const val CNY = "CNY"
        const val USD = "USD"
        const val EUR = "EUR"
        const val GBP = "GBP"
        const val JPY = "JPY"
        const val KRW = "KRW"
        const val TWD = "TWD"
        const val HKD = "HKD"
        const val SGD = "SGD"
        const val AUD = "AUD"
        const val CAD = "CAD"
        const val CHF = "CHF"
        const val SEK = "SEK"
        const val NOK = "NOK"
        const val DKK = "DKK"
        const val NZD = "NZD"
    }

    /**
     * 默认货币
     */
    const val DEFAULT_CURRENCY = Codes.CNY

    /**
     * 格式化货币金额
     *
     * @param amount 金额
     * @param currencyCode 货币代码
     * @param showSymbol 是否显示货币符号
     * @param showCode 是否显示货币代码
     * @param decimalPlaces 小数位数
     * @return 格式化后的货币字符串
     */
    fun formatCurrency(
        amount: Double,
        currencyCode: String = DEFAULT_CURRENCY,
        showSymbol: Boolean = true,
        showCode: Boolean = false,
        decimalPlaces: Int = 2
    ): String {
        val formatter = NumberFormat.getCurrencyInstance(Locale.getDefault())
        formatter.currency = Currency.getInstance(currencyCode)
        formatter.maximumFractionDigits = decimalPlaces
        formatter.minimumFractionDigits = decimalPlaces

        var formatted = formatter.format(amount)

        if (!showSymbol) {
            formatted = formatted.replace(getCurrencySymbol(currencyCode), "").trim()
        }

        if (showCode) {
            formatted = "$formatted $currencyCode"
        }

        return formatted
    }

    /**
     * 格式化金额（带千分位）
     *
     * @param amount 金额
     * @param decimalPlaces 小数位数
     * @return 格式化后的金额字符串
     */
    fun formatAmount(
        amount: Double,
        decimalPlaces: Int = 2
    ): String {
        val pattern = when (decimalPlaces) {
            0 -> "#,###"
            1 -> "#,###.#"
            2 -> "#,###.##"
            3 -> "#,###.###"
            else -> "#,###.##"
        }
        val formatter = DecimalFormat(pattern)
        return formatter.format(amount)
    }

    /**
     * 格式化金额（带正负号）
     *
     * @param amount 金额
     * @param showPositiveSign 是否显示正号
     * @return 格式化后的金额字符串
     */
    fun formatAmountWithSign(
        amount: Double,
        showPositiveSign: Boolean = true
    ): String {
        val sign = when {
            amount > 0 && showPositiveSign -> "+"
            amount < 0 -> "-"
            else -> ""
        }
        return "$sign${formatAmount(kotlin.math.abs(amount))}"
    }

    /**
     * 获取货币符号
     *
     * @param currencyCode 货币代码
     * @return 货币符号
     */
    fun getCurrencySymbol(currencyCode: String): String {
        return when (currencyCode) {
            Codes.CNY -> Symbols.CNY
            Codes.USD -> Symbols.USD
            Codes.EUR -> Symbols.EUR
            Codes.GBP -> Symbols.GBP
            Codes.JPY -> Symbols.JPY
            Codes.KRW -> Symbols.KRW
            Codes.TWD -> Symbols.TWD
            Codes.HKD -> Symbols.HKD
            Codes.SGD -> Symbols.SGD
            Codes.AUD -> Symbols.AUD
            Codes.CAD -> Symbols.CAD
            Codes.CHF -> Symbols.CHF
            Codes.SEK -> Symbols.SEK
            Codes.NOK -> Symbols.NOK
            Codes.DKK -> Symbols.DKK
            Codes.NZD -> Symbols.NZD
            else -> {
                try {
                    Currency.getInstance(currencyCode).symbol
                } catch (e: Exception) {
                    currencyCode
                }
            }
        }
    }

    /**
     * 获取货币名称
     *
     * @param currencyCode 货币代码
     * @return 货币名称
     */
    fun getCurrencyName(currencyCode: String): String {
        return when (currencyCode) {
            Codes.CNY -> "人民币"
            Codes.USD -> "美元"
            Codes.EUR -> "欧元"
            Codes.GBP -> "英镑"
            Codes.JPY -> "日元"
            Codes.KRW -> "韩元"
            Codes.TWD -> "新台币"
            Codes.HKD -> "港币"
            Codes.SGD -> "新加坡元"
            Codes.AUD -> "澳大利亚元"
            Codes.CAD -> "加拿大元"
            Codes.CHF -> "瑞士法郎"
            Codes.SEK -> "瑞典克朗"
            Codes.NOK -> "挪威克朗"
            Codes.DKK -> "丹麦克朗"
            Codes.NZD -> "新西兰元"
            else -> {
                try {
                    Currency.getInstance(currencyCode).displayName
                } catch (e: Exception) {
                    currencyCode
                }
            }
        }
    }

    /**
     * 获取支持的货币列表
     *
     * @return 支持的货币代码列表
     */
    fun getSupportedCurrencies(): List<String> {
        return listOf(
            Codes.CNY,
            Codes.USD,
            Codes.EUR,
            Codes.GBP,
            Codes.JPY,
            Codes.KRW,
            Codes.TWD,
            Codes.HKD,
            Codes.SGD,
            Codes.AUD,
            Codes.CAD,
            Codes.CHF,
            Codes.SEK,
            Codes.NOK,
            Codes.DKK,
            Codes.NZD
        )
    }

    /**
     * 获取货币信息
     *
     * @param currencyCode 货币代码
     * @return 货币信息
     */
    fun getCurrencyInfo(currencyCode: String): CurrencyInfo {
        return CurrencyInfo(
            code = currencyCode,
            name = getCurrencyName(currencyCode),
            symbol = getCurrencySymbol(currencyCode)
        )
    }

    /**
     * 解析金额字符串
     *
     * @param amountString 金额字符串
     * @return 金额，解析失败返回 null
     */
    fun parseAmount(amountString: String): Double? {
        return try {
            val cleaned = amountString
                .replace(",", "")
                .replace(" ", "")
                .replace(Regex("[^\\d.-]"), "")
            cleaned.toDoubleOrNull()
        } catch (e: Exception) {
            null
        }
    }

    /**
     * 验证金额是否有效
     *
     * @param amount 金额
     * @return 是否有效
     */
    fun isValidAmount(amount: Double?): Boolean {
        return amount != null && amount.isFinite() && amount >= 0
    }

    /**
     * 验证金额字符串是否有效
     *
     * @param amountString 金额字符串
     * @return 是否有效
     */
    fun isValidAmountString(amountString: String): Boolean {
        val amount = parseAmount(amountString)
        return isValidAmount(amount)
    }

    /**
     * 计算百分比
     *
     * @param part 部分
     * @param total 总数
     * @param decimalPlaces 小数位数
     * @return 百分比
     */
    fun calculatePercentage(
        part: Double,
        total: Double,
        decimalPlaces: Int = 2
    ): Double {
        if (total == 0.0) return 0.0
        return (part / total) * 100
    }

    /**
     * 格式化百分比
     *
     * @param percentage 百分比
     * @param decimalPlaces 小数位数
     * @return 格式化后的百分比字符串
     */
    fun formatPercentage(
        percentage: Double,
        decimalPlaces: Int = 2
    ): String {
        val pattern = when (decimalPlaces) {
            0 -> "#%"
            1 -> "#.#%"
            2 -> "#.##%"
            3 -> "#.###%"
            else -> "#.##%"
        }
        val formatter = DecimalFormat(pattern)
        return formatter.format(percentage / 100)
    }

    /**
     * 格式化百分比（带符号）
     *
     * @param percentage 百分比
     * @param decimalPlaces 小数位数
     * @return 格式化后的百分比字符串
     */
    fun formatPercentageWithSign(
        percentage: Double,
        decimalPlaces: Int = 2
    ): String {
        val sign = if (percentage > 0) "+" else ""
        return "$sign${formatPercentage(percentage, decimalPlaces)}"
    }

    /**
     * 货币信息数据类
     */
    data class CurrencyInfo(
        val code: String,
        val name: String,
        val symbol: String
    )
}
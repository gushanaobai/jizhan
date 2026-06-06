package com.openledger.core.common.utils

import java.text.SimpleDateFormat
import java.util.Calendar
import java.util.Date
import java.util.Locale

/**
 * 日期工具类
 *
 * 提供日期相关的工具方法
 */
object DateUtils {

    /**
     * 日期格式常量
     */
    object Formats {
        const val DATE = "yyyy-MM-dd"
        const val TIME = "HH:mm"
        const val DATE_TIME = "yyyy-MM-dd HH:mm"
        const val DATE_TIME_SECONDS = "yyyy-MM-dd HH:mm:ss"
        const val YEAR_MONTH = "yyyy-MM"
        const val YEAR = "yyyy"
        const val MONTH = "MM"
        const val DAY = "dd"
        const val DAY_OF_WEEK = "EEEE"
        const val SHORT_DATE = "MM/dd"
        const val SHORT_DATE_WITH_YEAR = "yy/MM/dd"
        const val CHINESE_DATE = "yyyy年MM月dd日"
        const val CHINESE_YEAR_MONTH = "yyyy年MM月"
        const val CHINESE_MONTH_DAY = "MM月dd日"
    }

    /**
     * 格式化日期
     *
     * @param date 日期
     * @param format 格式
     * @return 格式化后的日期字符串
     */
    fun formatDate(date: Date, format: String = Formats.DATE): String {
        val sdf = SimpleDateFormat(format, Locale.getDefault())
        return sdf.format(date)
    }

    /**
     * 格式化日期
     *
     * @param timestamp 时间戳
     * @param format 格式
     * @return 格式化后的日期字符串
     */
    fun formatDate(timestamp: Long, format: String = Formats.DATE): String {
        return formatDate(Date(timestamp), format)
    }

    /**
     * 解析日期字符串
     *
     * @param dateString 日期字符串
     * @param format 格式
     * @return 日期对象，解析失败返回 null
     */
    fun parseDate(dateString: String, format: String = Formats.DATE): Date? {
        return try {
            val sdf = SimpleDateFormat(format, Locale.getDefault())
            sdf.parse(dateString)
        } catch (e: Exception) {
            null
        }
    }

    /**
     * 获取今天的开始时间
     *
     * @return 今天的开始时间
     */
    fun getStartOfDay(): Date {
        val calendar = Calendar.getInstance()
        calendar.set(Calendar.HOUR_OF_DAY, 0)
        calendar.set(Calendar.MINUTE, 0)
        calendar.set(Calendar.SECOND, 0)
        calendar.set(Calendar.MILLISECOND, 0)
        return calendar.time
    }

    /**
     * 获取今天的结束时间
     *
     * @return 今天的结束时间
     */
    fun getEndOfDay(): Date {
        val calendar = Calendar.getInstance()
        calendar.set(Calendar.HOUR_OF_DAY, 23)
        calendar.set(Calendar.MINUTE, 59)
        calendar.set(Calendar.SECOND, 59)
        calendar.set(Calendar.MILLISECOND, 999)
        return calendar.time
    }

    /**
     * 获取本周的开始时间
     *
     * @return 本周的开始时间
     */
    fun getStartOfWeek(): Date {
        val calendar = Calendar.getInstance()
        calendar.firstDayOfWeek = Calendar.MONDAY
        calendar.set(Calendar.DAY_OF_WEEK, Calendar.MONDAY)
        calendar.set(Calendar.HOUR_OF_DAY, 0)
        calendar.set(Calendar.MINUTE, 0)
        calendar.set(Calendar.SECOND, 0)
        calendar.set(Calendar.MILLISECOND, 0)
        return calendar.time
    }

    /**
     * 获取本周的结束时间
     *
     * @return 本周的结束时间
     */
    fun getEndOfWeek(): Date {
        val calendar = Calendar.getInstance()
        calendar.firstDayOfWeek = Calendar.MONDAY
        calendar.set(Calendar.DAY_OF_WEEK, Calendar.SUNDAY)
        calendar.set(Calendar.HOUR_OF_DAY, 23)
        calendar.set(Calendar.MINUTE, 59)
        calendar.set(Calendar.SECOND, 59)
        calendar.set(Calendar.MILLISECOND, 999)
        return calendar.time
    }

    /**
     * 获取本月的开始时间
     *
     * @return 本月的开始时间
     */
    fun getStartOfMonth(): Date {
        val calendar = Calendar.getInstance()
        calendar.set(Calendar.DAY_OF_MONTH, 1)
        calendar.set(Calendar.HOUR_OF_DAY, 0)
        calendar.set(Calendar.MINUTE, 0)
        calendar.set(Calendar.SECOND, 0)
        calendar.set(Calendar.MILLISECOND, 0)
        return calendar.time
    }

    /**
     * 获取本月的结束时间
     *
     * @return 本月的结束时间
     */
    fun getEndOfMonth(): Date {
        val calendar = Calendar.getInstance()
        calendar.set(Calendar.DAY_OF_MONTH, calendar.getActualMaximum(Calendar.DAY_OF_MONTH))
        calendar.set(Calendar.HOUR_OF_DAY, 23)
        calendar.set(Calendar.MINUTE, 59)
        calendar.set(Calendar.SECOND, 59)
        calendar.set(Calendar.MILLISECOND, 999)
        return calendar.time
    }

    /**
     * 获取本年的开始时间
     *
     * @return 本年的开始时间
     */
    fun getStartOfYear(): Date {
        val calendar = Calendar.getInstance()
        calendar.set(Calendar.MONTH, Calendar.JANUARY)
        calendar.set(Calendar.DAY_OF_MONTH, 1)
        calendar.set(Calendar.HOUR_OF_DAY, 0)
        calendar.set(Calendar.MINUTE, 0)
        calendar.set(Calendar.SECOND, 0)
        calendar.set(Calendar.MILLISECOND, 0)
        return calendar.time
    }

    /**
     * 获取本年的结束时间
     *
     * @return 本年的结束时间
     */
    fun getEndOfYear(): Date {
        val calendar = Calendar.getInstance()
        calendar.set(Calendar.MONTH, Calendar.DECEMBER)
        calendar.set(Calendar.DAY_OF_MONTH, 31)
        calendar.set(Calendar.HOUR_OF_DAY, 23)
        calendar.set(Calendar.MINUTE, 59)
        calendar.set(Calendar.SECOND, 59)
        calendar.set(Calendar.MILLISECOND, 999)
        return calendar.time
    }

    /**
     * 判断是否是今天
     *
     * @param date 日期
     * @return 是否是今天
     */
    fun isToday(date: Date): Boolean {
        val today = Calendar.getInstance()
        val target = Calendar.getInstance()
        target.time = date
        return today.get(Calendar.YEAR) == target.get(Calendar.YEAR) &&
                today.get(Calendar.DAY_OF_YEAR) == target.get(Calendar.DAY_OF_YEAR)
    }

    /**
     * 判断是否是昨天
     *
     * @param date 日期
     * @return 是否是昨天
     */
    fun isYesterday(date: Date): Boolean {
        val yesterday = Calendar.getInstance()
        yesterday.add(Calendar.DAY_OF_YEAR, -1)
        val target = Calendar.getInstance()
        target.time = date
        return yesterday.get(Calendar.YEAR) == target.get(Calendar.YEAR) &&
                yesterday.get(Calendar.DAY_OF_YEAR) == target.get(Calendar.DAY_OF_YEAR)
    }

    /**
     * 判断是否是本周
     *
     * @param date 日期
     * @return 是否是本周
     */
    fun isThisWeek(date: Date): Boolean {
        val now = Calendar.getInstance()
        val target = Calendar.getInstance()
        target.time = date
        return now.get(Calendar.YEAR) == target.get(Calendar.YEAR) &&
                now.get(Calendar.WEEK_OF_YEAR) == target.get(Calendar.WEEK_OF_YEAR)
    }

    /**
     * 判断是否是本月
     *
     * @param date 日期
     * @return 是否是本月
     */
    fun isThisMonth(date: Date): Boolean {
        val now = Calendar.getInstance()
        val target = Calendar.getInstance()
        target.time = date
        return now.get(Calendar.YEAR) == target.get(Calendar.YEAR) &&
                now.get(Calendar.MONTH) == target.get(Calendar.MONTH)
    }

    /**
     * 判断是否是本年
     *
     * @param date 日期
     * @return 是否是本年
     */
    fun isThisYear(date: Date): Boolean {
        val now = Calendar.getInstance()
        val target = Calendar.getInstance()
        target.time = date
        return now.get(Calendar.YEAR) == target.get(Calendar.YEAR)
    }

    /**
     * 获取相对日期描述
     *
     * @param date 日期
     * @return 相对日期描述
     */
    fun getRelativeDate(date: Date): String {
        return when {
            isToday(date) -> "今天"
            isYesterday(date) -> "昨天"
            isThisWeek(date) -> "本周"
            isThisMonth(date) -> "本月"
            isThisYear(date) -> formatDate(date, Formats.CHINESE_MONTH_DAY)
            else -> formatDate(date, Formats.CHINESE_DATE)
        }
    }

    /**
     * 计算两个日期之间的天数差
     *
     * @param startDate 开始日期
     * @param endDate 结束日期
     * @return 天数差
     */
    fun daysBetween(startDate: Date, endDate: Date): Long {
        val diff = endDate.time - startDate.time
        return diff / (24 * 60 * 60 * 1000)
    }

    /**
     * 计算两个日期之间的月数差
     *
     * @param startDate 开始日期
     * @param endDate 结束日期
     * @return 月数差
     */
    fun monthsBetween(startDate: Date, endDate: Date): Int {
        val start = Calendar.getInstance()
        start.time = startDate
        val end = Calendar.getInstance()
        end.time = endDate
        val yearDiff = end.get(Calendar.YEAR) - start.get(Calendar.YEAR)
        val monthDiff = end.get(Calendar.MONTH) - start.get(Calendar.MONTH)
        return yearDiff * 12 + monthDiff
    }

    /**
     * 计算两个日期之间的年数差
     *
     * @param startDate 开始日期
     * @param endDate 结束日期
     * @return 年数差
     */
    fun yearsBetween(startDate: Date, endDate: Date): Int {
        val start = Calendar.getInstance()
        start.time = startDate
        val end = Calendar.getInstance()
        end.time = endDate
        return end.get(Calendar.YEAR) - start.get(Calendar.YEAR)
    }

    /**
     * 添加天数
     *
     * @param date 日期
     * @param days 天数
     * @return 新日期
     */
    fun addDays(date: Date, days: Int): Date {
        val calendar = Calendar.getInstance()
        calendar.time = date
        calendar.add(Calendar.DAY_OF_YEAR, days)
        return calendar.time
    }

    /**
     * 添加月数
     *
     * @param date 日期
     * @param months 月数
     * @return 新日期
     */
    fun addMonths(date: Date, months: Int): Date {
        val calendar = Calendar.getInstance()
        calendar.time = date
        calendar.add(Calendar.MONTH, months)
        return calendar.time
    }

    /**
     * 添加年数
     *
     * @param date 日期
     * @param years 年数
     * @return 新日期
     */
    fun addYears(date: Date, years: Int): Date {
        val calendar = Calendar.getInstance()
        calendar.time = date
        calendar.add(Calendar.YEAR, years)
        return calendar.time
    }

    /**
     * 获取当前时间戳
     *
     * @return 当前时间戳
     */
    fun currentTimeMillis(): Long {
        return System.currentTimeMillis()
    }

    /**
     * 获取当前日期
     *
     * @return 当前日期
     */
    fun currentDate(): Date {
        return Date()
    }
}
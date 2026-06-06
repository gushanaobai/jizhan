package com.openledger.core.database.converter

import androidx.room.TypeConverter
import java.util.Date

/**
 * 日期类型转换器
 *
 * 用于 Room 数据库中 Date 类型的转换
 */
class DateConverter {

    /**
     * 将 Date 转换为 Long（时间戳）
     *
     * @param date 日期
     * @return 时间戳
     */
    @TypeConverter
    fun fromTimestamp(date: Date?): Long? {
        return date?.time
    }

    /**
     * 将 Long（时间戳）转换为 Date
     *
     * @param value 时间戳
     * @return 日期
     */
    @TypeConverter
    fun toTimestamp(value: Long?): Date? {
        return value?.let { Date(it) }
    }
}
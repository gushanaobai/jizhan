package com.openledger.core.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import androidx.sqlite.db.SupportSQLiteDatabase
import com.openledger.core.database.converter.DateConverter
import com.openledger.core.database.dao.AccountDao
import com.openledger.core.database.dao.BudgetDao
import com.openledger.core.database.dao.CategoryDao
import com.openledger.core.database.dao.TransactionDao
import com.openledger.core.model.Account
import com.openledger.core.model.Budget
import com.openledger.core.model.Category
import com.openledger.core.model.Transaction
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

/**
 * OpenLedger 数据库
 *
 * Room 数据库定义
 */
@Database(
    entities = [
        Transaction::class,
        Category::class,
        Account::class,
        Budget::class
    ],
    version = 1,
    exportSchema = true
)
@TypeConverters(DateConverter::class)
abstract class OpenLedgerDatabase : RoomDatabase() {

    /**
     * 获取交易记录 DAO
     */
    abstract fun transactionDao(): TransactionDao

    /**
     * 获取分类 DAO
     */
    abstract fun categoryDao(): CategoryDao

    /**
     * 获取账户 DAO
     */
    abstract fun accountDao(): AccountDao

    /**
     * 获取预算 DAO
     */
    abstract fun budgetDao(): BudgetDao

    companion object {
        /**
         * 数据库名称
         */
        private const val DATABASE_NAME = "openledger.db"

        /**
         * 数据库实例
         */
        @Volatile
        private var INSTANCE: OpenLedgerDatabase? = null

        /**
         * 获取数据库实例
         *
         * @param context 上下文
         * @return 数据库实例
         */
        fun getInstance(context: Context): OpenLedgerDatabase {
            return INSTANCE ?: synchronized(this) {
                INSTANCE ?: buildDatabase(context).also { INSTANCE = it }
            }
        }

        /**
         * 构建数据库
         *
         * @param context 上下文
         * @return 数据库实例
         */
        private fun buildDatabase(context: Context): OpenLedgerDatabase {
            return Room.databaseBuilder(
                context.applicationContext,
                OpenLedgerDatabase::class.java,
                DATABASE_NAME
            )
                .addCallback(object : Callback() {
                    override fun onCreate(db: SupportSQLiteDatabase) {
                        super.onCreate(db)
                        // 数据库创建时初始化默认数据
                        CoroutineScope(Dispatchers.IO).launch {
                            getInstance(context).let { database ->
                                initializeDefaultData(database)
                            }
                        }
                    }
                })
                .fallbackToDestructiveMigration()
                .build()
        }

        /**
         * 初始化默认数据
         *
         * @param database 数据库实例
         */
        private suspend fun initializeDefaultData(database: OpenLedgerDatabase) {
            // 初始化默认分类
            val categoryDao = database.categoryDao()
            val defaultCategories = getDefaultCategories()
            categoryDao.insertAll(defaultCategories)

            // 初始化默认账户
            val accountDao = database.accountDao()
            val defaultAccounts = getDefaultAccounts()
            accountDao.insertAll(defaultAccounts)
        }

        /**
         * 获取默认分类列表
         *
         * @return 默认分类列表
         */
        private fun getDefaultCategories(): List<Category> {
            return listOf(
                // 支出分类
                Category.createExpense("餐饮美食", icon = "restaurant", color = "#FF5722", sortOrder = 1),
                Category.createExpense("交通出行", icon = "directions_car", color = "#2196F3", sortOrder = 2),
                Category.createExpense("日用百货", icon = "shopping_cart", color = "#4CAF50", sortOrder = 3),
                Category.createExpense("购物消费", icon = "shopping_bag", color = "#E91E63", sortOrder = 4),
                Category.createExpense("娱乐休闲", icon = "sports_esports", color = "#9C27B0", sortOrder = 5),
                Category.createExpense("医疗健康", icon = "local_hospital", color = "#00BCD4", sortOrder = 6),
                Category.createExpense("教育培训", icon = "school", color = "#FF9800", sortOrder = 7),
                Category.createExpense("居住生活", icon = "home", color = "#795548", sortOrder = 8),
                Category.createExpense("通讯物流", icon = "phone", color = "#607D8B", sortOrder = 9),
                Category.createExpense("人情往来", icon = "card_giftcard", color = "#F44336", sortOrder = 10),
                Category.createExpense("金融保险", icon = "account_balance", color = "#3F51B5", sortOrder = 11),
                Category.createExpense("其他支出", icon = "more_horiz", color = "#9E9E9E", sortOrder = 12),

                // 收入分类
                Category.createIncome("工资薪水", icon = "work", color = "#4CAF50", sortOrder = 1),
                Category.createIncome("奖金津贴", icon = "emoji_events", color = "#8BC34A", sortOrder = 2),
                Category.createIncome("投资理财", icon = "trending_up", color = "#009688", sortOrder = 3),
                Category.createIncome("兼职副业", icon = "business_center", color = "#FF5722", sortOrder = 4),
                Category.createIncome("礼金红包", icon = "redeem", color = "#E91E63", sortOrder = 5),
                Category.createIncome("退款返利", icon = "replay", color = "#FF9800", sortOrder = 6),
                Category.createIncome("其他收入", icon = "more_horiz", color = "#9E9E9E", sortOrder = 7)
            )
        }

        /**
         * 获取默认账户列表
         *
         * @return 默认账户列表
         */
        private fun getDefaultAccounts(): List<Account> {
            return listOf(
                Account.createCash(),
                Account.createAlipay(),
                Account.createWechat()
            )
        }
    }
}
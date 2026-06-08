package com.openledger.ui.home

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.BarChart
import androidx.compose.material.icons.filled.DateRange
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.filled.TrendingUp
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import com.openledger.core.common.utils.CurrencyUtils
import com.openledger.core.common.utils.DateUtils
import com.openledger.core.model.Transaction
import com.openledger.ui.components.CategoryIcons
import com.openledger.ui.theme.*

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HomeScreen(
    viewModel: HomeViewModel = hiltViewModel(),
    onNavigateToAdd: () -> Unit = {}
) {
    val uiState by viewModel.uiState.collectAsState()
    var selectedTab by remember { mutableIntStateOf(0) }

    Scaffold(
        bottomBar = {
            BottomNavBar(
                selectedTab = selectedTab,
                onTabSelected = { selectedTab = it },
                onAddClick = onNavigateToAdd
            )
        }
    ) { paddingValues ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues)
        ) {
            CenterAlignedTopAppBar(
                title = {
                    Text(
                        "OpenLedger",
                        fontWeight = FontWeight.Bold,
                        letterSpacing = (-0.5).sp
                    )
                },
                colors = TopAppBarDefaults.centerAlignedTopAppBarColors(
                    containerColor = Color.Transparent,
                    titleContentColor = MaterialTheme.colorScheme.onBackground
                )
            )

            if (uiState.isLoading) {
                Box(
                    modifier = Modifier.fillMaxSize(),
                    contentAlignment = Alignment.Center
                ) {
                    CircularProgressIndicator(color = MaterialTheme.colorScheme.primary)
                }
            } else {
                LazyColumn(
                    modifier = Modifier.fillMaxSize(),
                    contentPadding = PaddingValues(horizontal = 16.dp, vertical = 8.dp),
                    verticalArrangement = Arrangement.spacedBy(16.dp)
                ) {
                    item {
                        MonthlyOverviewCard(
                            income = uiState.monthlyIncome,
                            expense = uiState.monthlyExpense,
                            balance = uiState.monthlyBalance
                        )
                    }

                    item {
                        TotalAssetCard(totalBalance = uiState.totalBalance)
                    }

                    item {
                        QuickActionRow()
                    }

                    item {
                        Row(
                            modifier = Modifier.fillMaxWidth(),
                            horizontalArrangement = Arrangement.SpaceBetween,
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            Text(
                                "最近交易",
                                style = MaterialTheme.typography.titleMedium,
                                fontWeight = FontWeight.SemiBold
                            )
                            Text(
                                "查看全部",
                                style = MaterialTheme.typography.bodySmall,
                                color = MaterialTheme.colorScheme.primary
                            )
                        }
                    }

                    if (uiState.recentTransactions.isEmpty()) {
                        item { EmptyState() }
                    } else {
                        val categoryMap = uiState.categories.associateBy { it.id }
                        items(
                            items = uiState.recentTransactions,
                            key = { it.id }
                        ) { transaction ->
                            val category = categoryMap[transaction.categoryId]
                            TransactionItem(
                                transaction = transaction,
                                categoryName = category?.name,
                                onDelete = { viewModel.deleteTransaction(transaction) }
                            )
                        }
                    }

                    item { Spacer(modifier = Modifier.height(16.dp)) }
                }
            }
        }
    }

    uiState.errorMessage?.let { message ->
        LaunchedEffect(message) {
            viewModel.clearError()
        }
    }
}

@Composable
fun BottomNavBar(
    selectedTab: Int,
    onTabSelected: (Int) -> Unit,
    onAddClick: () -> Unit
) {
    Surface(
        tonalElevation = 0.dp,
        color = MaterialTheme.colorScheme.surface
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 8.dp, vertical = 8.dp),
            horizontalArrangement = Arrangement.SpaceAround,
            verticalAlignment = Alignment.CenterVertically
        ) {
            BottomNavItem(
                icon = Icons.Filled.DateRange,
                label = "明细",
                selected = selectedTab == 0,
                onClick = { onTabSelected(0) }
            )
            BottomNavItem(
                icon = Icons.Filled.BarChart,
                label = "图表",
                selected = selectedTab == 1,
                onClick = { onTabSelected(1) }
            )
            Box(
                modifier = Modifier
                    .size(46.dp)
                    .clip(RoundedCornerShape(12.dp))
                    .background(MaterialTheme.colorScheme.primary)
                    .clickable(onClick = onAddClick),
                contentAlignment = Alignment.Center
            ) {
                Icon(
                    Icons.Default.Add,
                    contentDescription = "记账",
                    tint = Color.White,
                    modifier = Modifier.size(24.dp)
                )
            }
            BottomNavItem(
                icon = Icons.Filled.TrendingUp,
                label = "发现",
                selected = selectedTab == 2,
                onClick = { onTabSelected(2) }
            )
            BottomNavItem(
                icon = Icons.Filled.Person,
                label = "我的",
                selected = selectedTab == 3,
                onClick = { onTabSelected(3) }
            )
        }
    }
}

@Composable
fun BottomNavItem(
    icon: ImageVector,
    label: String,
    selected: Boolean,
    onClick: () -> Unit
) {
    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = Modifier
            .clickable(onClick = onClick)
            .padding(horizontal = 12.dp, vertical = 4.dp)
    ) {
        Icon(
            imageVector = icon,
            contentDescription = label,
            modifier = Modifier.size(22.dp),
            tint = if (selected) MaterialTheme.colorScheme.primary else TextHint
        )
        Spacer(modifier = Modifier.height(2.dp))
        Text(
            text = label,
            style = MaterialTheme.typography.labelSmall,
            color = if (selected) MaterialTheme.colorScheme.primary else TextHint
        )
    }
}

@Composable
fun MonthlyOverviewCard(
    income: Double,
    expense: Double,
    balance: Double
) {
    Card(
        modifier = Modifier.fillMaxWidth(),
        shape = RoundedCornerShape(20.dp),
        colors = CardDefaults.cardColors(containerColor = Color.Transparent),
        elevation = CardDefaults.cardElevation(0.dp)
    ) {
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .background(
                    brush = Brush.linearGradient(
                        colors = listOf(Primary, PrimaryLight)
                    ),
                    shape = RoundedCornerShape(20.dp)
                )
                .padding(24.dp)
        ) {
            Column {
                Text(
                    "本月概览",
                    style = MaterialTheme.typography.bodySmall,
                    color = Color.White.copy(alpha = 0.7f)
                )
                Spacer(modifier = Modifier.height(20.dp))
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween
                ) {
                    Column {
                        Text("收入", style = MaterialTheme.typography.labelSmall, color = Color.White.copy(alpha = 0.6f))
                        Spacer(modifier = Modifier.height(4.dp))
                        Text(CurrencyUtils.formatCurrency(income), style = MaterialTheme.typography.titleLarge, fontWeight = FontWeight.Bold, color = Color.White)
                    }
                    Column {
                        Text("支出", style = MaterialTheme.typography.labelSmall, color = Color.White.copy(alpha = 0.6f))
                        Spacer(modifier = Modifier.height(4.dp))
                        Text(CurrencyUtils.formatCurrency(expense), style = MaterialTheme.typography.titleLarge, fontWeight = FontWeight.Bold, color = Color.White)
                    }
                    Column {
                        Text("结余", style = MaterialTheme.typography.labelSmall, color = Color.White.copy(alpha = 0.6f))
                        Spacer(modifier = Modifier.height(4.dp))
                        Text(CurrencyUtils.formatCurrency(balance), style = MaterialTheme.typography.titleLarge, fontWeight = FontWeight.Bold, color = Color.White)
                    }
                }
            }
        }
    }
}

@Composable
fun TotalAssetCard(totalBalance: Double) {
    Card(
        modifier = Modifier.fillMaxWidth(),
        shape = RoundedCornerShape(16.dp),
        colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surface),
        elevation = CardDefaults.cardElevation(0.dp),
        border = androidx.compose.foundation.BorderStroke(0.5.dp, BorderLight)
    ) {
        Row(
            modifier = Modifier.fillMaxWidth().padding(20.dp),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Column {
                Text("总资产", style = MaterialTheme.typography.bodySmall, color = TextHint)
                Spacer(modifier = Modifier.height(6.dp))
                Text(CurrencyUtils.formatCurrency(totalBalance), style = MaterialTheme.typography.headlineMedium, fontWeight = FontWeight.Bold, color = if (totalBalance >= 0) IncomeGreen else ExpenseRed)
            }
            Box(
                modifier = Modifier.size(44.dp).clip(CircleShape).background(IncomeBg),
                contentAlignment = Alignment.Center
            ) {
                Icon(Icons.Default.TrendingUp, contentDescription = null, tint = IncomeGreen, modifier = Modifier.size(22.dp))
            }
        }
    }
}

@Composable
fun QuickActionRow() {
    Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.spacedBy(12.dp)) {
        QuickActionItem("收入", IncomeGreen, IncomeBg, Modifier.weight(1f))
        QuickActionItem("支出", ExpenseRed, ExpenseBg, Modifier.weight(1f))
        QuickActionItem("转账", Secondary, SecondaryContainer, Modifier.weight(1f))
        QuickActionItem("基金", Color(0xFF534AB7), Color(0xFFEEEDFE), Modifier.weight(1f))
    }
}

@Composable
fun QuickActionItem(label: String, color: Color, bgColor: Color, modifier: Modifier = Modifier) {
    Card(
        modifier = modifier,
        shape = RoundedCornerShape(12.dp),
        colors = CardDefaults.cardColors(containerColor = bgColor),
        elevation = CardDefaults.cardElevation(0.dp)
    ) {
        Column(
            modifier = Modifier.fillMaxWidth().padding(vertical = 14.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Box(
                modifier = Modifier.size(36.dp).clip(CircleShape).background(color.copy(alpha = 0.15f)),
                contentAlignment = Alignment.Center
            ) {
                Text(label.first().toString(), style = MaterialTheme.typography.titleSmall, fontWeight = FontWeight.Bold, color = color)
            }
            Spacer(modifier = Modifier.height(6.dp))
            Text(label, style = MaterialTheme.typography.labelSmall, color = color)
        }
    }
}

@Composable
fun TransactionItem(transaction: Transaction, categoryName: String? = null, onDelete: () -> Unit) {
    val displayName = categoryName ?: transaction.description ?: if (transaction.isIncome) "收入" else "支出"
    val icon = CategoryIcons.getCategoryIcon(displayName, transaction.isIncome)

    Card(
        modifier = Modifier.fillMaxWidth(),
        shape = RoundedCornerShape(14.dp),
        colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surface),
        elevation = CardDefaults.cardElevation(0.dp),
        border = androidx.compose.foundation.BorderStroke(0.5.dp, BorderLight)
    ) {
        Row(
            modifier = Modifier.fillMaxWidth().padding(14.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Box(
                modifier = Modifier.size(42.dp).clip(RoundedCornerShape(12.dp)).background(if (transaction.isIncome) IncomeBg else ExpenseBg),
                contentAlignment = Alignment.Center
            ) {
                Icon(imageVector = icon, contentDescription = null, tint = if (transaction.isIncome) IncomeGreen else ExpenseRed, modifier = Modifier.size(20.dp))
            }
            Spacer(modifier = Modifier.width(12.dp))
            Column(modifier = Modifier.weight(1f)) {
                Text(text = displayName, style = MaterialTheme.typography.bodyLarge, fontWeight = FontWeight.Medium)
                Spacer(modifier = Modifier.height(2.dp))
                Text(text = DateUtils.formatDate(transaction.date, DateUtils.Formats.CHINESE_DATE), style = MaterialTheme.typography.bodySmall, color = TextHint)
            }
            Text(
                text = "${if (transaction.isIncome) "+" else "-"}${CurrencyUtils.formatCurrency(transaction.amount)}",
                style = MaterialTheme.typography.titleMedium,
                fontWeight = FontWeight.SemiBold,
                color = if (transaction.isIncome) IncomeGreen else ExpenseRed
            )
        }
    }
}

@Composable
fun EmptyState() {
    Card(
        modifier = Modifier.fillMaxWidth(),
        shape = RoundedCornerShape(14.dp),
        colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surfaceVariant),
        elevation = CardDefaults.cardElevation(0.dp)
    ) {
        Box(modifier = Modifier.fillMaxWidth().padding(40.dp), contentAlignment = Alignment.Center) {
            Column(horizontalAlignment = Alignment.CenterHorizontally) {
                Text("暂无交易记录", style = MaterialTheme.typography.bodyLarge, color = TextHint)
                Spacer(modifier = Modifier.height(6.dp))
                Text("点击下方按钮开始记账", style = MaterialTheme.typography.bodySmall, color = TextHint.copy(alpha = 0.6f))
            }
        }
    }
}
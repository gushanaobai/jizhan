# OpenLedger - 开源记账本

<div align="center">

**一款简单、实用、安全、开源的 Android 记账应用**

[![License: MIT](https://img.shields.io/badge/License-MIT-yellow.svg)](https://opensource.org/licenses/MIT)
[![Android](https://img.shields.io/badge/Platform-Android-green.svg)](https://developer.android.com)
[![Kotlin](https://img.shields.io/badge/Language-Kotlin-purple.svg)](https://kotlinlang.org)
[![API](https://img.shields.io/badge/API-26%2B-brightgreen.svg)](https://android-arsenal.com/api?level=26)

</div>

## ✨ 功能特点

- 📝 **收支记录**: 快速记录日常收入和支出
- 📊 **数据统计**: 直观的图表展示消费趋势
- 💰 **预算管理**: 设置月度预算并实时监控
- 🏦 **多账户支持**: 支持现金、银行卡、支付宝、微信等多种账户
- 🔒 **数据安全**: 本地加密存储，支持数据备份与恢复
- 📤 **数据导出**: 支持导出为 CSV、Excel 等格式
- 🎨 **主题定制**: 支持亮色/暗色主题切换

## 🚀 快速开始

### 环境要求

- Android Studio Hedgehog (2023.1.1) 或更高版本
- JDK 17 或更高版本
- Android SDK 34
- Kotlin 1.9.0 或更高版本

### 安装步骤

1. **克隆项目**
   ```bash
   git clone https://github.com/gushanaobai/jizhan.git
   cd jizhan
   ```

2. **打开项目**
   - 使用 Android Studio 打开项目
   - 等待 Gradle 同步完成

3. **运行应用**
   - 连接 Android 设备或启动模拟器
   - 点击运行按钮或使用命令行：
   ```bash
   ./gradlew installDebug
   ```

### 构建发布版本

```bash
# 构建 Debug 版本
./gradlew assembleDebug

# 构建 Release 版本
./gradlew assembleRelease

# 运行测试
./gradlew test
```

## 📖 使用指南

### 基本操作

1. **记账**: 点击底部"+"按钮，输入金额和分类
2. **查看统计**: 进入统计页面，查看消费趋势
3. **管理账户**: 在设置中添加和管理资金账户
4. **设置预算**: 在预算页面设置月度预算
5. **数据备份**: 在设置中备份和恢复数据

### 快捷操作

- **左滑删除**: 在交易列表中左滑可删除记录
- **右滑编辑**: 在交易列表中右滑可编辑记录
- **下拉刷新**: 在列表页面下拉可刷新数据

## 🏗️ 项目结构

```
jizhan/
├── app/                                    # 主应用模块
│   ├── src/main/
│   │   ├── java/com/openledger/
│   │   │   ├── MainActivity.kt            # 主 Activity + 导航
│   │   │   ├── OpenLedgerApplication.kt   # Application 类
│   │   │   ├── repository/               # 数据仓库层
│   │   │   │   └── TransactionRepository.kt
│   │   │   └── ui/                       # UI 层
│   │   │       ├── theme/                # Material3 主题
│   │   │       │   └── Theme.kt
│   │   │       ├── home/                 # 首页模块
│   │   │       │   ├── HomeScreen.kt
│   │   │       │   └── HomeViewModel.kt
│   │   │       └── add/                  # 记账模块
│   │   │           ├── AddTransactionScreen.kt
│   │   │           └── AddTransactionViewModel.kt
│   │   └── res/                          # 资源文件
│   └── build.gradle.kts                  # 模块构建配置
├── core/                                  # 核心功能模块
│   ├── core-common/                      # 公共工具类
│   │   └── utils/
│   │       ├── DateUtils.kt              # 日期工具
│   │       └── CurrencyUtils.kt          # 货币工具
│   ├── core-model/                       # 数据模型
│   │   ├── Transaction.kt                # 交易记录
│   │   ├── Category.kt                   # 分类
│   │   ├── Account.kt                    # 账户
│   │   └── Budget.kt                     # 预算
│   └── core-database/                    # 数据库操作
│       ├── OpenLedgerDatabase.kt         # Room 数据库
│       ├── converter/DateConverter.kt    # 类型转换器
│       └── dao/                          # 数据访问对象
│           ├── TransactionDao.kt
│           ├── CategoryDao.kt
│           ├── AccountDao.kt
│           └── BudgetDao.kt
├── docs/                                  # 文档
├── .github/                               # GitHub 配置
├── build.gradle.kts                       # 项目构建配置
├── settings.gradle.kts                    # 项目设置
└── README.md                              # 项目说明
```

## 🛠️ 技术栈

### 核心框架

- **语言**: Kotlin
- **UI 框架**: Jetpack Compose
- **架构模式**: MVVM + Clean Architecture
- **依赖注入**: Hilt
- **异步处理**: Kotlin Coroutines + Flow

### Android Jetpack

- **Lifecycle**: 生命周期管理
- **ViewModel**: 视图状态管理
- **Room**: 本地数据库
- **Navigation**: 页面导航
- **WorkManager**: 后台任务
- **DataStore**: 数据存储

### 第三方库

- **Retrofit**: 网络请求
- **OkHttp**: HTTP 客户端
- **Gson/Moshi**: JSON 解析
- **Coil**: 图片加载
- **MPAndroidChart**: 图表库
- **Lottie**: 动画库

### 测试框架

- **JUnit**: 单元测试
- **Espresso**: UI 测试
- **Mockk**: Mock 框架
- **Turbine**: Flow 测试

## 📊 功能清单

| 功能 | 状态 | 说明 |
|------|------|------|
| 收支记录 | ✅ 完成 | 支持收入/支出记录，带分类和备注 |
| 分类管理 | ✅ 完成 | 预设 19 种收支分类，支持自定义 |
| 多账户支持 | ✅ 完成 | 现金、储蓄卡、支付宝、微信等 |
| 数据统计 | ✅ 完成 | 本月收支概览、总余额展示 |
| 本地存储 | ✅ 完成 | Room 数据库，数据持久化 |
| 深色模式 | ✅ 完成 | Material3 动态主题，支持暗色模式 |
| 预算管理 | 🚧 计划中 | 月度预算设置与监控 |
| 数据备份 | 🚧 计划中 | 本地加密备份与恢复 |
| 数据导出 | 🚧 计划中 | CSV/Excel 格式导出 |
| 基金涨跌幅跟踪 | 📋 规划中 | 基金持仓管理、实时涨跌幅监控、收益分析 |

### 路线图

- **v1.1** - 预算管理 + 数据备份恢复
- **v1.2** - 数据导出 + 图表统计增强
- **v2.0** - 基金涨跌幅跟踪（持仓管理、实时行情、收益曲线、分红记录）

## 🤝 贡献指南

我们欢迎所有形式的贡献！请阅读 [CONTRIBUTING.md](CONTRIBUTING.md) 了解详细信息。

### 贡献方式

1. **报告 Bug**: 通过 [GitHub Issues](https://github.com/gushanaobai/jizhan/issues) 报告问题
2. **提出建议**: 通过 [GitHub Discussions](https://github.com/gushanaobai/jizhan/discussions) 提出建议
3. **提交代码**: 通过 Pull Request 提交代码
4. **完善文档**: 帮助完善项目文档
5. **翻译**: 帮助翻译应用界面

### 开发流程

```bash
# 1. Fork 项目
# 2. 克隆你的 Fork
git clone https://github.com/your-username/jizhan.git

# 3. 创建功能分支
git checkout -b feature/amazing-feature

# 4. 提交更改
git commit -m 'feat: add amazing feature'

# 5. 推送到分支
git push origin feature/amazing-feature

# 6. 创建 Pull Request
```

## 📄 许可证

本项目采用 MIT 许可证 - 查看 [LICENSE](LICENSE) 文件了解详情。

## 🙏 致谢

感谢以下开源项目和贡献者：

- [Android Jetpack](https://developer.android.com/jetpack)
- [Kotlin](https://kotlinlang.org)
- [Material Design](https://material.io)
- [MPAndroidChart](https://github.com/PhilJay/MPAndroidChart)
- [Hilt](https://dagger.dev/hilt/)
- [Room](https://developer.android.com/training/data-storage/room)

## 📞 联系我们

- **GitHub**: [gushanaobai/jizhan](https://github.com/gushanaobai/jizhan)
- **Issues**: [GitHub Issues](https://github.com/gushanaobai/jizhan/issues)
- **Discussions**: [GitHub Discussions](https://github.com/gushanaobai/jizhan/discussions)

---

<div align="center">

**如果这个项目对你有帮助，请给它一个 ⭐️**

</div>

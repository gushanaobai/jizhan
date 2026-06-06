# OpenLedger - 开源记账本

<div align="center">

![OpenLedger Logo](docs/images/logo.png)

**一款简单、实用、安全、开源的个人记账应用**

[![License: MIT](https://img.shields.io/badge/License-MIT-yellow.svg)](https://opensource.org/licenses/MIT)
[![Android](https://img.shields.io/badge/Platform-Android-green.svg)](https://developer.android.com)
[![Kotlin](https://img.shields.io/badge/Language-Kotlin-purple.svg)](https://kotlinlang.org)
[![API](https://img.shields.io/badge/API-26%2B-brightgreen.svg)](https://android-arsenal.com/api?level=26)
[![Release](https://img.shields.io/github/v/release/gushanaobai/OpenLedger.svg)](https://github.com/gushanaobai/OpenLedger/releases)

[English](README_EN.md) | [中文](README.md)

</div>

## ✨ 功能特点

- 📝 **收支记录**: 快速记录日常收入和支出
- 📊 **数据统计**: 直观的图表展示消费趋势
- 💰 **预算管理**: 设置月度预算并实时监控
- 🏦 **多账户支持**: 支持现金、银行卡、支付宝、微信等多种账户
- 🔒 **数据安全**: 本地加密存储，支持数据备份与恢复
- 📤 **数据导出**: 支持导出为 CSV、Excel 等格式
- 🌍 **多语言支持**: 支持中文、英文等多种语言
- 🎨 **主题定制**: 支持亮色/暗色主题切换

## 📱 应用截图

<div align="center">
  <img src="docs/images/screenshot_home.png" width="200" alt="首页">
  <img src="docs/images/screenshot_add.png" width="200" alt="记账">
  <img src="docs/images/screenshot_stats.png" width="200" alt="统计">
  <img src="docs/images/screenshot_settings.png" width="200" alt="设置">
</div>

## 🚀 快速开始

### 环境要求

- Android Studio Hedgehog (2023.1.1) 或更高版本
- JDK 17 或更高版本
- Android SDK 34
- Kotlin 1.9.0 或更高版本

### 安装步骤

1. **克隆项目**
   ```bash
   git clone https://github.com/gushanaobai/OpenLedger.git
   cd OpenLedger
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
- **双击快速记账**: 在首页双击可快速打开记账页面

## 🏗️ 项目结构

```
OpenLedger/
├── app/                          # 主应用模块
├── buildSrc/                     # 构建配置
├── core/                         # 核心功能模块
│   ├── core-common/             # 公共工具类
│   ├── core-model/              # 数据模型
│   ├── core-database/           # 数据库操作
│   ├── core-data/               # 数据仓库
│   └── core-domain/             # 业务逻辑
├── feature/                      # 功能模块
│   ├── feature-home/            # 首页模块
│   ├── feature-transaction/     # 交易模块
│   ├── feature-statistics/      # 统计模块
│   ├── feature-settings/        # 设置模块
│   └── feature-backup/          # 备份模块
├── docs/                         # 文档
├── scripts/                      # 脚本
├── .github/                      # GitHub 配置
├── build.gradle.kts
├── settings.gradle.kts
└── README.md
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

## 📊 性能指标

| 指标 | 目标值 | 实际值 | 状态 |
|------|--------|--------|------|
| 应用启动时间 | < 2 秒 | - | ⏳ 待测试 |
| 内存占用 | < 100 MB | - | ⏳ 待测试 |
| 崩溃率 | < 0.1% | - | ⏳ 待测试 |
| 代码覆盖率 | > 80% | - | ⏳ 待测试 |

## 🤝 贡献指南

我们欢迎所有形式的贡献！请阅读 [CONTRIBUTING.md](CONTRIBUTING.md) 了解详细信息。

### 贡献方式

1. **报告 Bug**: 通过 [GitHub Issues](https://github.com/gushanaobai/OpenLedger/issues) 报告问题
2. **提出建议**: 通过 [GitHub Discussions](https://github.com/gushanaobai/OpenLedger/discussions) 提出建议
3. **提交代码**: 通过 Pull Request 提交代码
4. **完善文档**: 帮助完善项目文档
5. **翻译**: 帮助翻译应用界面

### 开发流程

```bash
# 1. Fork 项目
# 2. 克隆你的 Fork
git clone https://github.com/your-username/OpenLedger.git

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

```
MIT License

Copyright (c) 2026 OpenLedger

Permission is hereby granted, free of charge, to any person obtaining a copy
of this software and associated documentation files (the "Software"), to deal
in the Software without restriction, including without limitation the rights
to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
copies of the Software, and to permit persons to whom the Software is
furnished to do so, subject to the following conditions:

The above copyright notice and this permission notice shall be included in all
copies or substantial portions of the Software.

THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE
SOFTWARE.
```

## 🙏 致谢

感谢以下开源项目和贡献者：

- [Android Jetpack](https://developer.android.com/jetpack)
- [Kotlin](https://kotlinlang.org)
- [Material Design](https://material.io)
- [MPAndroidChart](https://github.com/PhilJay/MPAndroidChart)
- [Hilt](https://dagger.dev/hilt/)
- [Room](https://developer.android.com/training/data-storage/room)
- 所有 [贡献者](https://github.com/gushanaobai/OpenLedger/graphs/contributors)

## 📞 联系我们

- **GitHub**: [gushanaobai/OpenLedger](https://github.com/gushanaobai/OpenLedger)
- **Email**: gushanaobai@gmail.com
- **Issues**: [GitHub Issues](https://github.com/gushanaobai/OpenLedger/issues)
- **Discussions**: [GitHub Discussions](https://github.com/gushanaobai/OpenLedger/discussions)

## ⭐ Star History

[![Star History Chart](https://api.star-history.com/svg?repos=gushanaobai/OpenLedger&type=Date)](https://star-history.com/#gushanaobai/OpenLedger&Date)

---

<div align="center">

**如果这个项目对你有帮助，请给它一个 ⭐️**

</div>
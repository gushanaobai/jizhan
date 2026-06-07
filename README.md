# 简易记账本

<div align="center">

**一款简单、实用的个人记账应用**

[![License: MIT](https://img.shields.io/badge/License-MIT-yellow.svg)](https://opensource.org/licenses/MIT)
[![HTML5](https://img.shields.io/badge/HTML5-E34F26?logo=html5&logoColor=white)](https://developer.mozilla.org/zh-CN/docs/Web/HTML)
[![CSS3](https://img.shields.io/badge/CSS3-1572B6?logo=css3&logoColor=white)](https://developer.mozilla.org/zh-CN/docs/Web/CSS)
[![JavaScript](https://img.shields.io/badge/JavaScript-F7DF1E?logo=javascript&logoColor=black)](https://developer.mozilla.org/zh-CN/docs/Web/JavaScript)

</div>

## ✨ 功能特点

- 📝 **收支记录**: 快速记录日常收入和支出
- 📊 **数据统计**: 直观的图表展示消费趋势
- 💰 **预算管理**: 设置月度预算并实时监控
- 🏦 **多账户支持**: 支持现金、银行卡、支付宝、微信等多种账户
- 📤 **数据导出**: 支持导出为 CSV 格式
- 🎨 **主题定制**: 支持亮色/暗色主题切换

## 🚀 快速开始

### 在线体验

直接在浏览器中打开 `index.html` 文件即可使用。

### 本地运行

```bash
# 克隆项目
git clone https://github.com/gushanaobai/jizhan.git
cd jizhan

# 使用浏览器打开
open index.html  # macOS
start index.html # Windows
xdg-open index.html # Linux
```

## 📖 使用指南

### 基本操作

1. **记账**: 点击"添加记录"按钮，输入金额和分类
2. **查看统计**: 在统计区域查看本月收支情况
3. **筛选记录**: 使用筛选功能查看特定类型的记录
4. **数据备份**: 数据自动保存在浏览器本地存储中

### 快捷操作

- **快速记账**: 填写表单后点击"添加"按钮
- **筛选记录**: 使用下拉菜单筛选收入/支出
- **本地存储**: 数据自动保存在浏览器中

## 🏗️ 项目结构

```
jizhan/
├── index.html              # 主页面
├── style.css               # 样式文件
├── script.js               # 主要功能代码
├── app/                    # Android 应用模块
├── core/                   # 核心功能模块
├── docs/                   # 文档
├── .github/                # GitHub 配置
├── build.gradle.kts        # Gradle 构建配置
├── settings.gradle.kts     # Gradle 设置
└── README.md               # 项目说明
```

## 🛠️ 技术栈

### Web 版本

- **HTML5**: 页面结构
- **CSS3**: 样式设计
- **JavaScript**: 业务逻辑
- **LocalStorage**: 本地数据存储

### Android 版本（开发中）

- **语言**: Kotlin
- **UI 框架**: Jetpack Compose
- **架构模式**: MVVM + Clean Architecture
- **依赖注入**: Hilt
- **本地数据库**: Room

## 📊 功能清单

| 功能 | 状态 | 说明 |
|------|------|------|
| 收支记录 | ✅ 完成 | 支持收入/支出记录 |
| 分类管理 | ✅ 完成 | 预设多种分类 |
| 数据统计 | ✅ 完成 | 本月收支统计 |
| 数据筛选 | ✅ 完成 | 按类型筛选记录 |
| 本地存储 | ✅ 完成 | 浏览器本地存储 |
| 多账户支持 | 🚧 开发中 | Android 版本 |
| 数据导出 | 🚧 开发中 | CSV 导出功能 |
| 预算管理 | 🚧 开发中 | 月度预算设置 |

## 🤝 贡献指南

欢迎贡献代码、报告问题或提出建议！

### 贡献方式

1. **报告 Bug**: 通过 [GitHub Issues](https://github.com/gushanaobai/jizhan/issues) 报告问题
2. **提出建议**: 通过 Issues 提出功能建议
3. **提交代码**: 通过 Pull Request 提交代码

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

感谢以下开源项目：

- [MDN Web Docs](https://developer.mozilla.org/) - Web 技术文档
- [GitHub](https://github.com/) - 代码托管平台

## 📞 联系我们

- **GitHub**: [gushanaobai/jizhan](https://github.com/gushanaobai/jizhan)
- **Issues**: [GitHub Issues](https://github.com/gushanaobai/jizhan/issues)

---

<div align="center">

**如果这个项目对你有帮助，请给它一个 ⭐️**

</div>

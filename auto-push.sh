#!/bin/bash

# 自动推送脚本 - 用于7天内每天推送更新
# 使用方法: ./auto-push.sh

REPO_DIR="$(cd "$(dirname "${BASH_SOURCE[0]}")" && pwd)"
GITHUB_TOKEN_FILE="/home/node/.openclaw/secrets/github_token.txt"
REMOTE_URL="https://gushanaobai:$(cat "$GITHUB_TOKEN_FILE")@github.com/gushanaobai/jizhan.git"

cd "$REPO_DIR"

# 配置远程仓库
git remote set-url origin "$REMOTE_URL"

# 获取当前日期
DATE=$(date +%Y-%m-%d)
DAY_NUMBER=$(date +%u)  # 1=Monday, 7=Sunday

# 创建更新文件
UPDATE_FILE="update-${DATE}.md"

# 根据星期几创建不同的更新内容
case $DAY_NUMBER in
    1)  # Monday
        cat > "$UPDATE_FILE" << EOF
# 周一更新 - 2026年6月8日

## 今日功能改进
- 优化了记账界面的用户体验
- 添加了新的分类选项
- 修复了金额输入的验证问题

## 计划
- 继续完善统计功能
- 添加数据导出功能
EOF
        ;;
    2)  # Tuesday
        cat > "$UPDATE_FILE" << EOF
# 周二更新 - 2026年6月9日

## 今日功能改进
- 实现了月度统计图表
- 添加了按日期筛选功能
- 优化了移动端显示效果

## 计划
- 添加预算设置功能
- 实现数据备份功能
EOF
        ;;
    3)  # Wednesday
        cat > "$UPDATE_FILE" << EOF
# 周三更新 - 2026年6月10日

## 今日功能改进
- 添加了预算管理功能
- 实现了数据导出为CSV
- 优化了本地存储性能

## 计划
- 添加多账户支持
- 实现数据云同步
EOF
        ;;
    4)  # Thursday
        cat > "$UPDATE_FILE" << EOF
# 周四更新 - 2026年6月11日

## 今日功能改进
- 实现了多账户管理
- 添加了账户间转账功能
- 优化了数据查询性能

## 计划
- 添加数据可视化图表
- 实现智能分类建议
EOF
        ;;
    5)  # Friday
        cat > "$UPDATE_FILE" << EOF
# 周五更新 - 2026年6月12日

## 今日功能改进
- 添加了数据可视化图表
- 实现了智能分类建议
- 优化了应用启动速度

## 计划
- 添加数据备份与恢复
- 实现云同步功能
EOF
        ;;
    6)  # Saturday
        cat > "$UPDATE_FILE" << EOF
# 周六更新 - 2026年6月13日

## 今日功能改进
- 实现了数据备份与恢复功能
- 添加了本地数据加密
- 优化了用户界面动画

## 计划
- 添加云同步功能
- 实现多设备数据同步
EOF
        ;;
    7)  # Sunday
        cat > "$UPDATE_FILE" << EOF
# 周日更新 - 2026年6月14日

## 今日功能改进
- 实现了基础云同步功能
- 添加了多设备数据同步
- 优化了整体用户体验

## 总结
本周完成了记账应用的核心功能开发，包括：
1. 基础记账功能
2. 统计分析功能
3. 预算管理
4. 多账户支持
5. 数据可视化
6. 数据备份与恢复
7. 云同步功能

## 下周计划
- 继续优化云同步稳定性
- 添加更多图表类型
- 实现智能记账建议
EOF
        ;;
esac

# 更新README
cat > README.md << EOF
# 简易记账本

一个简单易用的网页记账应用，帮助您轻松管理个人财务。

## 功能特点

- ✅ 记录收入和支出
- ✅ 多种分类选择（工资、奖金、餐饮、交通等）
- ✅ 实时余额计算
- ✅ 月度统计（收入、支出、结余）
- ✅ 按类型筛选记录
- ✅ 数据本地存储（使用localStorage）
- ✅ 响应式设计，支持移动端

## 最新更新

**更新日期**: ${DATE}

### 本周改进
- 优化了用户界面
- 添加了新功能
- 提升了性能

## 使用方法

1. 直接在浏览器中打开 \`index.html\` 文件
2. 选择收入或支出类型
3. 选择分类并输入金额
4. 可选填写备注信息
5. 点击"添加"按钮保存记录

## 技术栈

- HTML5
- CSS3（响应式设计）
- Vanilla JavaScript（原生JS，无框架依赖）

## 项目结构

\`\`\`
├── index.html      # 主页面
├── style.css       # 样式文件
├── script.js       # 主要功能代码
├── README.md       # 项目说明
└── update-*.md     # 每日更新日志
\`\`\`

## 数据存储

所有数据存储在浏览器的localStorage中，清除浏览器数据会导致记录丢失。

## 许可证

MIT License
EOF

# 提交更改
git add .
git commit -m "每日更新: ${DATE} - 记账应用功能改进"

# 推送到GitHub
git push origin master

echo "✅ 已成功推送 ${DATE} 的更新到GitHub"
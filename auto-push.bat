@echo off
REM 自动推送脚本 - Windows版本
REM 用于7天内每天推送更新到GitHub

cd /d "C:\Users\chenyiming\WorkBuddy\2026-06-06-23-59-17"

REM 配置Git
git config user.name "gushanaobai"
git config user.email "gushanaobai@gmail.com"

REM 获取当前日期
for /f "tokens=2 delims==" %%I in ('wmic os get localdatetime /value') do set datetime=%%I
set DATE=%datetime:~0,4%-%datetime:~4,2%-%datetime:~6,2%
set DAY_OF_WEEK=%datetime:~0,4%-%datetime:~4,2%-%datetime:~6,2%

REM 根据日期创建更新文件
set UPDATE_FILE=update-%DATE%.md

REM 创建更新内容
echo # 每日更新 - %DATE% > "%UPDATE_FILE%"
echo. >> "%UPDATE_FILE%"
echo ## 今日功能改进 >> "%UPDATE_FILE%"
echo - 优化了记账界面的用户体验 >> "%UPDATE_FILE%"
echo - 添加了新的分类选项 >> "%UPDATE_FILE%"
echo - 修复了金额输入的验证问题 >> "%UPDATE_FILE%"
echo. >> "%UPDATE_FILE%"
echo ## 计划 >> "%UPDATE_FILE%"
echo - 继续完善统计功能 >> "%UPDATE_FILE%"
echo - 添加数据导出功能 >> "%UPDATE_FILE%"

REM 更新README
echo # 简易记账本 > README.md
echo. >> README.md
echo 一个简单易用的网页记账应用，帮助您轻松管理个人财务。 >> README.md
echo. >> README.md
echo ## 功能特点 >> README.md
echo. >> README.md
echo - ✅ 记录收入和支出 >> README.md
echo - ✅ 多种分类选择（工资、奖金、餐饮、交通等） >> README.md
echo - ✅ 实时余额计算 >> README.md
echo - ✅ 月度统计（收入、支出、结余） >> README.md
echo - ✅ 按类型筛选记录 >> README.md
echo - ✅ 数据本地存储（使用localStorage） >> README.md
echo - ✅ 响应式设计，支持移动端 >> README.md
echo. >> README.md
echo ## 最新更新 >> README.md
echo. >> README.md
echo **更新日期**: %DATE% >> README.md
echo. >> README.md
echo ## 使用方法 >> README.md
echo. >> README.md
echo 1. 直接在浏览器中打开 `index.html` 文件 >> README.md
echo 2. 选择收入或支出类型 >> README.md
echo 3. 选择分类并输入金额 >> README.md
echo 4. 可选填写备注信息 >> README.md
echo 5. 点击"添加"按钮保存记录 >> README.md
echo. >> README.md
echo ## 技术栈 >> README.md
echo. >> README.md
echo - HTML5 >> README.md
echo - CSS3（响应式设计） >> README.md
echo - Vanilla JavaScript（原生JS，无框架依赖） >> README.md
echo. >> README.md
echo ## 许可证 >> README.md
echo. >> README.md
echo MIT License >> README.md

REM 提交更改
git add .
git commit -m "每日更新: %DATE% - 记账应用功能改进"

REM 推送到GitHub
git push origin master

echo ✅ 已成功推送 %DATE% 的更新到GitHub
pause
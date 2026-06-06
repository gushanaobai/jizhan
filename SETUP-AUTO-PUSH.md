# 自动推送设置指南

## ✅ GitHub Token 已配置成功

新的GitHub Token已保存并测试通过，代码已成功推送到：
https://github.com/gushanaobai/jizhan

## 📅 设置每天自动推送

### 方法1: Windows任务计划程序 (推荐)

1. **打开任务计划程序**
   - 按 `Win + R`，输入 `taskschd.msc`，回车

2. **创建基本任务**
   - 点击右侧 "创建基本任务"
   - 名称: "记账应用每日推送"
   - 描述: "每天自动推送记账应用更新到GitHub"

3. **设置触发器**
   - 选择 "每天"
   - 开始时间: 选择你希望推送的时间（如 10:00）
   - 每隔: 1 天

4. **设置操作**
   - 选择 "启动程序"
   - 程序或脚本: `C:\Users\chenyiming\WorkBuddy\2026-06-06-23-59-17\auto-push.bat`
   - 起始于: `C:\Users\chenyiming\WorkBuddy\2026-06-06-23-59-17`

5. **完成设置**
   - 点击 "完成"
   - 右键任务 → "属性" → "不管用户是否登录都要运行"

### 方法2: 使用Python脚本 (高级)

如果你安装了Python，可以使用更灵活的Python脚本：

```bash
# 编辑 auto-push.py
python auto-push.py
```

### 方法3: 手动推送

每次想推送时，运行：

**Windows:**
```cmd
cd C:\Users\chenyiming\WorkBuddy\2026-06-06-23-59-17
auto-push.bat
```

**Linux/Mac:**
```bash
cd /path/to/project
./auto-push.sh
```

## 📊 当前状态

- ✅ 记账应用已创建
- ✅ 7天更新记录已准备
- ✅ GitHub Token已配置
- ✅ 代码已推送到GitHub
- ⏳ 等待设置自动推送

## 🔗 仓库地址

https://github.com/gushanaobai/jizhan

## 📝 更新内容

每天会自动创建新的更新文件，包含：
- 当日功能改进
- 本周进度
- 下一步计划

## ⚠️ 注意事项

1. 确保电脑在设定时间处于开机状态
2. 网络连接正常
3. GitHub Token有效（如果过期需要重新生成）

## 🆘 故障排除

### 推送失败
- 检查网络连接
- 验证GitHub Token是否有效
- 查看错误日志

### Token过期
1. 访问 https://github.com/settings/tokens
2. 生成新Token（勾选repo权限）
3. 更新token文件：
   ```bash
   echo "new_token" > /home/node/.openclaw/secrets/github_token.txt
   ```

## 📞 支持

如有问题，请检查：
1. GitHub Token权限
2. 网络连接
3. 仓库访问权限

---

**设置完成时间**: 2026-06-07
**状态**: 准备就绪，等待配置自动推送
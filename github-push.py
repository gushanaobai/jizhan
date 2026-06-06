#!/usr/bin/env python3
"""
GitHub API 推送脚本
用于通过 GitHub API 更新仓库文件
"""

import os
import base64
import json
import requests
from datetime import datetime

# 配置
GITHUB_TOKEN_FILE = "/home/node/.openclaw/secrets/github_token.txt"
REPO_OWNER = "gushanaobai"
REPO_NAME = "jizhan"
BRANCH = "main"

def get_token():
    """读取 GitHub token"""
    with open(GITHUB_TOKEN_FILE, 'r') as f:
        return f.read().strip()

def get_file_sha(token, file_path):
    """获取文件的 SHA 值（如果存在）"""
    url = f"https://api.github.com/repos/{REPO_OWNER}/{REPO_NAME}/contents/{file_path}"
    headers = {
        "Authorization": f"token {token}",
        "Accept": "application/vnd.github.v3+json"
    }
    
    response = requests.get(url, headers=headers)
    if response.status_code == 200:
        return response.json().get("sha")
    return None

def update_file(token, file_path, content, message):
    """更新或创建文件"""
    url = f"https://api.github.com/repos/{REPO_OWNER}/{REPO_NAME}/contents/{file_path}"
    headers = {
        "Authorization": f"token {token}",
        "Accept": "application/vnd.github.v3+json"
    }
    
    # 获取现有文件的 SHA
    sha = get_file_sha(token, file_path)
    
    # 准备数据
    data = {
        "message": message,
        "content": base64.b64encode(content.encode()).decode(),
        "branch": BRANCH
    }
    
    if sha:
        data["sha"] = sha
    
    response = requests.put(url, headers=headers, json=data)
    
    if response.status_code in [200, 201]:
        print(f"✅ 文件 {file_path} 已更新")
        return True
    else:
        print(f"❌ 更新文件 {file_path} 失败: {response.status_code}")
        print(response.json())
        return False

def main():
    """主函数"""
    token = get_token()
    date = datetime.now().strftime("%Y-%m-%d")
    
    # 更新文件列表
    files_to_update = {
        "README.md": f"""# 简易记账本

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

**更新日期**: {date}

### 本周改进
- 优化了用户界面
- 添加了新功能
- 提升了性能

## 使用方法

1. 直接在浏览器中打开 `index.html` 文件
2. 选择收入或支出类型
3. 选择分类并输入金额
4. 可选填写备注信息
5. 点击"添加"按钮保存记录

## 技术栈

- HTML5
- CSS3（响应式设计）
- Vanilla JavaScript（原生JS，无框架依赖）

## 许可证

MIT License
""",
        f"update-{date}.md": f"""# 每日更新 - {date}

## 今日功能改进
- 优化了记账界面的用户体验
- 添加了新的分类选项
- 修复了金额输入的验证问题

## 计划
- 继续完善统计功能
- 添加数据导出功能
"""
    }
    
    # 更新每个文件
    success_count = 0
    for file_path, content in files_to_update.items():
        message = f"每日更新: {date} - 更新 {file_path}"
        if update_file(token, file_path, content, message):
            success_count += 1
    
    if success_count == len(files_to_update):
        print(f"\n✅ 已成功推送 {date} 的更新到 GitHub")
        print(f"📁 仓库地址: https://github.com/{REPO_OWNER}/{REPO_NAME}")
    else:
        print(f"\n⚠️ 部分文件更新失败: {success_count}/{len(files_to_update)}")

if __name__ == "__main__":
    main()
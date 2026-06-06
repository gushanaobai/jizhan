// 记账应用主要功能
class AccountingApp {
    constructor() {
        this.transactions = JSON.parse(localStorage.getItem('transactions')) || [];
        this.form = document.getElementById('transaction-form');
        this.transactionsList = document.getElementById('transactions');
        this.balanceElement = document.getElementById('balance');
        this.monthlyIncomeElement = document.getElementById('monthly-income');
        this.monthlyExpenseElement = document.getElementById('monthly-expense');
        this.monthlyBalanceElement = document.getElementById('monthly-balance');
        this.filterType = document.getElementById('filter-type');

        this.init();
    }

    init() {
        this.form.addEventListener('submit', (e) => this.addTransaction(e));
        this.filterType.addEventListener('change', () => this.renderTransactions());
        this.render();
    }

    addTransaction(e) {
        e.preventDefault();

        const type = document.getElementById('type').value;
        const category = document.getElementById('category').value;
        const amount = parseFloat(document.getElementById('amount').value);
        const description = document.getElementById('description').value;

        const transaction = {
            id: Date.now(),
            type,
            category,
            amount,
            description,
            date: new Date().toISOString()
        };

        this.transactions.push(transaction);
        this.save();
        this.render();

        // 重置表单
        this.form.reset();
        document.getElementById('type').value = 'income';
    }

    deleteTransaction(id) {
        this.transactions = this.transactions.filter(t => t.id !== id);
        this.save();
        this.render();
    }

    save() {
        localStorage.setItem('transactions', JSON.stringify(this.transactions));
    }

    render() {
        this.renderTransactions();
        this.renderBalance();
        this.renderMonthlyStats();
    }

    renderTransactions() {
        const filter = this.filterType.value;
        let filtered = this.transactions;

        if (filter !== 'all') {
            filtered = this.transactions.filter(t => t.type === filter);
        }

        // 按日期倒序排列
        filtered.sort((a, b) => new Date(b.date) - new Date(a.date));

        this.transactionsList.innerHTML = filtered.map(t => `
            <li class="${t.type}">
                <div class="transaction-info">
                    <div class="transaction-amount">
                        ${t.type === 'income' ? '+' : '-'}¥${t.amount.toFixed(2)}
                    </div>
                    <div class="transaction-category">${this.getCategoryName(t.category)}</div>
                    ${t.description ? `<div class="transaction-description">${t.description}</div>` : ''}
                    <div class="transaction-date">${this.formatDate(t.date)}</div>
                </div>
                <button class="delete-btn" onclick="app.deleteTransaction(${t.id})">删除</button>
            </li>
        `).join('');
    }

    renderBalance() {
        const balance = this.transactions.reduce((acc, t) => {
            return t.type === 'income' ? acc + t.amount : acc - t.amount;
        }, 0);

        this.balanceElement.textContent = `¥${balance.toFixed(2)}`;
        this.balanceElement.style.color = balance >= 0 ? '#27ae60' : '#e74c3c';
    }

    renderMonthlyStats() {
        const now = new Date();
        const currentMonth = now.getMonth();
        const currentYear = now.getFullYear();

        const monthlyTransactions = this.transactions.filter(t => {
            const date = new Date(t.date);
            return date.getMonth() === currentMonth && date.getFullYear() === currentYear;
        });

        const monthlyIncome = monthlyTransactions
            .filter(t => t.type === 'income')
            .reduce((acc, t) => acc + t.amount, 0);

        const monthlyExpense = monthlyTransactions
            .filter(t => t.type === 'expense')
            .reduce((acc, t) => acc + t.amount, 0);

        const monthlyBalance = monthlyIncome - monthlyExpense;

        this.monthlyIncomeElement.textContent = `¥${monthlyIncome.toFixed(2)}`;
        this.monthlyExpenseElement.textContent = `¥${monthlyExpense.toFixed(2)}`;
        this.monthlyBalanceElement.textContent = `¥${monthlyBalance.toFixed(2)}`;
        this.monthlyBalanceElement.style.color = monthlyBalance >= 0 ? '#27ae60' : '#e74c3c';
    }

    getCategoryName(category) {
        const categories = {
            salary: '工资',
            bonus: '奖金',
            food: '餐饮',
            transport: '交通',
            shopping: '购物',
            entertainment: '娱乐',
            other: '其他'
        };
        return categories[category] || category;
    }

    formatDate(dateString) {
        const date = new Date(dateString);
        return date.toLocaleDateString('zh-CN', {
            year: 'numeric',
            month: '2-digit',
            day: '2-digit',
            hour: '2-digit',
            minute: '2-digit'
        });
    }
}

// 初始化应用
const app = new AccountingApp();
// API基础URL
const API_BASE_URL = 'http://localhost:3068/api/performance';

// 检查后端服务连接状态
async function checkConnection() {
    const connectionStatus = document.getElementById('connectionStatus');
    const buttons = document.querySelectorAll('button[type="button"]');
    
    try {
        const response = await fetch(`${API_BASE_URL}/health`);
        if (response.ok) {
            connectionStatus.className = 'alert alert-success';
            connectionStatus.textContent = '后端服务连接正常';
            buttons.forEach(btn => btn.disabled = false);
            return true;
        } else {
            throw new Error('后端服务响应异常');
        }
    } catch (error) {
        connectionStatus.className = 'alert alert-danger';
        connectionStatus.textContent = '后端服务连接失败，请确保服务已启动';
        buttons.forEach(btn => btn.disabled = true);
        return false;
    }
}

// 获取表单数据
function getFormData() {
    return {
        dataSize: document.getElementById('dataSize').value,
        concurrency: document.getElementById('concurrency').value,
        totalOperations: document.getElementById('totalOperations').value
    };
}

// 显示加载状态
function showLoading(elementId) {
    document.getElementById(elementId).innerHTML = '<div class="loading"></div> 测试进行中...';
}

// 显示错误信息
function showError(elementId, message) {
    document.getElementById(elementId).innerHTML = `<div class="alert alert-danger">${message}</div>`;
}

// 构建查询参数
function buildQueryParams(data) {
    return Object.entries(data)
        .map(([key, value]) => `${encodeURIComponent(key)}=${encodeURIComponent(value)}`)
        .join('&');
}

// 格式化测试结果
function formatResult(result) {
    if (!result) {
        return '<div class="alert alert-warning">未获取到测试结果</div>';
    }
    
    return `
        <div class="result-item">
            <div><span class="result-label">测试类型:</span> <span class="result-value">${result.testType || 'N/A'}</span></div>
            <div><span class="result-label">数据大小:</span> <span class="result-value">${result.dataSize || 0} 字节</span></div>
            <div><span class="result-label">并发数:</span> <span class="result-value">${result.concurrency || 0}</span></div>
            <div><span class="result-label">总操作数:</span> <span class="result-value">${result.totalOperations || 0}</span></div>
            <div><span class="result-label">总耗时:</span> <span class="result-value">${result.totalTime || 0} 毫秒</span></div>
            <div><span class="result-label">平均响应时间:</span> <span class="result-value">${(result.averageResponseTime || 0).toFixed(2)} 毫秒</span></div>
            <div><span class="result-label">吞吐量:</span> <span class="result-value">${(result.throughput || 0).toFixed(2)} 操作/秒</span></div>
            <div><span class="result-label">错误数:</span> <span class="result-value">${result.errorCount || 0}</span></div>
        </div>
    `;
}

// 运行完整测试
async function runFullTest() {
    if (!await checkConnection()) return;
    
    const data = getFormData();
    showLoading('redisResults');
    showLoading('couchbaseResults');

    try {
        const response = await fetch(`${API_BASE_URL}/full-test?${buildQueryParams(data)}`, {
            method: 'POST'
        });
        
        if (!response.ok) {
            throw new Error(`测试请求失败: ${response.status} ${response.statusText}`);
        }

        const results = await response.json();
        if (!results || !results.redis || !results.couchbase) {
            throw new Error('返回数据格式不正确');
        }
        
        document.getElementById('redisResults').innerHTML = formatResult(results.redis);
        document.getElementById('couchbaseResults').innerHTML = formatResult(results.couchbase);
    } catch (error) {
        showError('redisResults', error.message);
        showError('couchbaseResults', error.message);
    }
}

// 仅测试Redis
async function testRedis() {
    if (!await checkConnection()) return;
    
    const data = getFormData();
    showLoading('redisResults');

    try {
        const response = await fetch(`${API_BASE_URL}/redis/write?${buildQueryParams(data)}`, {
            method: 'POST'
        });
        
        if (!response.ok) {
            throw new Error(`Redis测试请求失败: ${response.status} ${response.statusText}`);
        }

        const result = await response.json();
        document.getElementById('redisResults').innerHTML = formatResult(result);
    } catch (error) {
        showError('redisResults', error.message);
    }
}

// 仅测试Couchbase
async function testCouchbase() {
    if (!await checkConnection()) return;
    
    const data = getFormData();
    showLoading('couchbaseResults');

    try {
        const response = await fetch(`${API_BASE_URL}/couchbase/write?${buildQueryParams(data)}`, {
            method: 'POST'
        });
        
        if (!response.ok) {
            throw new Error(`Couchbase测试请求失败: ${response.status} ${response.statusText}`);
        }

        const result = await response.json();
        document.getElementById('couchbaseResults').innerHTML = formatResult(result);
    } catch (error) {
        showError('couchbaseResults', error.message);
    }
}

// 页面加载完成后检查连接状态
document.addEventListener('DOMContentLoaded', checkConnection); 
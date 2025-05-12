package com.performance.model;

import lombok.Data;
import lombok.Builder;

/**
 * 测试结果模型
 * 用于记录性能测试的结果
 */
@Data
@Builder
public class TestResult {
    // 测试类型（READ/WRITE）
    private String testType;
    
    // 数据库类型（REDIS/COUCHBASE）
    private String dbType;
    
    // 数据大小
    private int dataSize;
    
    // 并发数
    private int concurrency;
    
    // 总操作数
    private long totalOperations;
    
    // 总耗时（毫秒）
    private long totalTime;
    
    // 平均响应时间（毫秒）
    private double averageResponseTime;
    
    // 吞吐量（操作/秒）
    private double throughput;
    
    // 错误数
    private int errorCount;
    
    // 测试时间戳
    private long timestamp;
} 
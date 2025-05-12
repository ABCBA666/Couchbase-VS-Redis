package com.performance.model;

import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.couchbase.core.mapping.Document;

/**
 * 测试数据模型
 * 用于存储性能测试的数据
 */
@Data
@Document
public class TestData {
    @Id
    private String id;
    
    // 数据大小（字节）
    private int size;
    
    // 数据内容
    private String content;
    
    // 创建时间
    private long timestamp;
    
    // 数据类型（用于区分不同大小的测试数据）
    private String dataType; // SMALL, MEDIUM, LARGE
} 
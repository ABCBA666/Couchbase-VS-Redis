package com.performance.service;

import com.performance.model.TestData;
import org.springframework.stereotype.Service;

import java.util.UUID;

/**
 * 测试数据生成服务
 * 用于生成不同大小的测试数据
 */
@Service
public class DataGeneratorService {
    
    /**
     * 生成指定大小的测试数据
     * @param size 数据大小（字节）
     * @return 测试数据对象
     */
    public TestData generateTestData(int size) {
        TestData data = new TestData();
        data.setId(UUID.randomUUID().toString());
        data.setSize(size);
        data.setContent(generateContent(size));
        data.setTimestamp(System.currentTimeMillis());
        data.setDataType(determineDataType(size));
        return data;
    }
    
    /**
     * 根据数据大小确定数据类型
     */
    private String determineDataType(int size) {
        if (size <= 1024) {
            return "SMALL";
        } else if (size <= 10240) {
            return "MEDIUM";
        } else {
            return "LARGE";
        }
    }
    
    /**
     * 生成指定大小的内容
     */
    private String generateContent(int size) {
        StringBuilder content = new StringBuilder();
        while (content.length() < size) {
            content.append(UUID.randomUUID().toString());
        }
        return content.substring(0, size);
    }
} 
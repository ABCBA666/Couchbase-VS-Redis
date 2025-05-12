package com.performance.controller;

import com.performance.model.TestResult;
import com.performance.service.PerformanceTestService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;

/**
 * 性能测试控制器
 * 提供性能测试的REST API接口
 */
@RestController
@RequestMapping("/api/performance")
@RequiredArgsConstructor
public class PerformanceTestController {
    
    private final PerformanceTestService performanceTestService;
    
    /**
     * 健康检查接口
     */
    @GetMapping("/health")
    public Map<String, String> healthCheck() {
        Map<String, String> response = new HashMap<>();
        response.put("status", "UP");
        response.put("message", "服务正常运行");
        return response;
    }
    
    /**
     * 执行Redis写入测试
     */
    @PostMapping("/redis/write")
    public TestResult testRedisWrite(
            @RequestParam(defaultValue = "1024") int dataSize,
            @RequestParam(defaultValue = "10") int concurrency,
            @RequestParam(defaultValue = "1000") int totalOperations) {
        return performanceTestService.testRedisWrite(dataSize, concurrency, totalOperations);
    }
    
    /**
     * 执行Couchbase写入测试
     */
    @PostMapping("/couchbase/write")
    public TestResult testCouchbaseWrite(
            @RequestParam(defaultValue = "1024") int dataSize,
            @RequestParam(defaultValue = "10") int concurrency,
            @RequestParam(defaultValue = "1000") int totalOperations) {
        return performanceTestService.testCouchbaseWrite(dataSize, concurrency, totalOperations);
    }
    
    /**
     * 执行完整的性能测试
     */
    @PostMapping("/full-test")
    public Map<String, TestResult> runFullTest(
            @RequestParam(defaultValue = "1024") int dataSize,
            @RequestParam(defaultValue = "10") int concurrency,
            @RequestParam(defaultValue = "1000") int totalOperations) {
        
        Map<String, TestResult> results = new HashMap<>();
        
        // Redis测试
        results.put("redis", performanceTestService.testRedisWrite(dataSize, concurrency, totalOperations));
        
        // Couchbase测试
        results.put("couchbase", performanceTestService.testCouchbaseWrite(dataSize, concurrency, totalOperations));
        
        return results;
    }
} 
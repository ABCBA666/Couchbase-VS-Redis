package com.performance.service;

import com.performance.model.TestData;
import com.performance.model.TestResult;
import com.performance.repository.CouchbaseTestRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * 性能测试服务
 * 实现Redis和Couchbase的性能测试逻辑
 */
@Service
@RequiredArgsConstructor
public class PerformanceTestService {
    
    private final RedisTemplate<String, Object> redisTemplate;
    private final CouchbaseTestRepository couchbaseRepository;
    private final DataGeneratorService dataGeneratorService;
    
    /**
     * 执行Redis写入测试
     */
    public TestResult testRedisWrite(int dataSize, int concurrency, int totalOperations) {
        long startTime = System.currentTimeMillis();
        AtomicInteger errorCount = new AtomicInteger(0);
        
        ExecutorService executor = Executors.newFixedThreadPool(concurrency);
        List<CompletableFuture<Void>> futures = new ArrayList<>();
        
        for (int i = 0; i < totalOperations; i++) {
            CompletableFuture<Void> future = CompletableFuture.runAsync(() -> {
                try {
                    TestData data = dataGeneratorService.generateTestData(dataSize);
                    redisTemplate.opsForValue().set(data.getId(), data);
                } catch (Exception e) {
                    errorCount.incrementAndGet();
                }
            }, executor);
            futures.add(future);
        }
        
        CompletableFuture.allOf(futures.toArray(new CompletableFuture[0])).join();
        executor.shutdown();
        
        long endTime = System.currentTimeMillis();
        long totalTime = endTime - startTime;
        
        return TestResult.builder()
                .testType("WRITE")
                .dbType("REDIS")
                .dataSize(dataSize)
                .concurrency(concurrency)
                .totalOperations(totalOperations)
                .totalTime(totalTime)
                .averageResponseTime((double) totalTime / totalOperations)
                .throughput((double) totalOperations / (totalTime / 1000.0))
                .errorCount(errorCount.get())
                .timestamp(System.currentTimeMillis())
                .build();
    }
    
    /**
     * 执行Couchbase写入测试
     */
    public TestResult testCouchbaseWrite(int dataSize, int concurrency, int totalOperations) {
        long startTime = System.currentTimeMillis();
        AtomicInteger errorCount = new AtomicInteger(0);
        
        ExecutorService executor = Executors.newFixedThreadPool(concurrency);
        List<CompletableFuture<Void>> futures = new ArrayList<>();
        
        for (int i = 0; i < totalOperations; i++) {
            CompletableFuture<Void> future = CompletableFuture.runAsync(() -> {
                try {
                    TestData data = dataGeneratorService.generateTestData(dataSize);
                    couchbaseRepository.save(data);
                } catch (Exception e) {
                    errorCount.incrementAndGet();
                }
            }, executor);
            futures.add(future);
        }
        
        CompletableFuture.allOf(futures.toArray(new CompletableFuture[0])).join();
        executor.shutdown();
        
        long endTime = System.currentTimeMillis();
        long totalTime = endTime - startTime;
        
        return TestResult.builder()
                .testType("WRITE")
                .dbType("COUCHBASE")
                .dataSize(dataSize)
                .concurrency(concurrency)
                .totalOperations(totalOperations)
                .totalTime(totalTime)
                .averageResponseTime((double) totalTime / totalOperations)
                .throughput((double) totalOperations / (totalTime / 1000.0))
                .errorCount(errorCount.get())
                .timestamp(System.currentTimeMillis())
                .build();
    }
    
    // 读取测试方法类似，这里省略...
} 
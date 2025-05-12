package com.performance.repository;

import com.performance.model.TestData;
import org.springframework.data.couchbase.repository.CouchbaseRepository;
import org.springframework.stereotype.Repository;

/**
 * Couchbase数据访问接口
 */
@Repository
public interface CouchbaseTestRepository extends CouchbaseRepository<TestData, String> {
    // 可以添加自定义查询方法
} 
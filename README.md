# 项目名称

Couchbase VS Redis 数据库性能测试

## 项目描述

此项目用于测试Redis与Couchbase的性能表现。帮助开发者选择合适的数据库解决方案。项目的目标是提供一个易于使用的测试框架，能够在不同的负载条件下评估数据库的性能。

## 使用说明

- 运行
- 健康检查接口：http://localhost:3068/api/performance/health
- 前端页面：http://localhost:3068/index.html

## 许可证

Apache License 2.0

## 主要功能和特性

- 支持Redis与Couchbase的读写性能测试。
- 可配置的测试参数，包括数据大小、并发数和总操作数。
- 详细的测试结果输出，包括平均响应时间、吞吐量和错误计数。

## 技术架构和设计决策

项目基于Spring Boot框架，使用Java语言开发。选择Spring Boot是因为其快速开发和易于集成的特性。Redis和Couchbase的客户端库分别使用Spring Data Redis和Spring Data Couchbase。

## 使用的版本信息

- Spring Boot: 3.2.1
- Couchbase Client: 3.5.3
- Embedded Redis: 0.7.3
- Couchbase：7.6.0

## 性能测试运行步骤
1. Couchbase创建一个名为test的bucket，网址http://localhost:8091
2. 确保Redis和Couchbase服务已启动并可访问。
3. 配置`application.yml`文件中的数据库连接信息。
4. 使用API或前端界面提交测试请求，查看测试结果。

<img width="1268" alt="1747034304416" src="https://github.com/user-attachments/assets/58b55330-7b81-42ca-849a-c809d77c6d52" />
<img width="1279" alt="1747034368101" src="https://github.com/user-attachments/assets/eac925b8-52c0-465e-835f-7f684c67e2bc" />
<img width="1277" alt="1747034601325" src="https://github.com/user-attachments/assets/dd11e260-ae33-466f-b02d-4c6703d5c0be" />
<img width="1267" alt="1747034442305" src="https://github.com/user-attachments/assets/a8c2e948-44d8-49b5-add3-390e64d9e30d" />

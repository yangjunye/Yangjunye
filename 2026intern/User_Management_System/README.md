# 用户管理系统 API

基于 Spring Boot + MySQL + JdbcTemplate 实现的 RESTful 用户管理系统。

---

## 📋 项目信息

| 项目 | 说明 |
|------|------|
| 框架 | Spring Boot 4.0.2 |
| JDK | 21 |
| 数据库 | MySQL 8.0+ |
| 数据访问 | JdbcTemplate |
| 构建工具 | Maven |

---

## ✅ 已完成功能

### 核心接口（5个）

| 方法 | URL | 功能 | 状态 |
|------|-----|------|------|
| GET | `/users` | 查询所有用户 | ✅ |
| GET | `/users/{id}` | 根据ID查询用户 | ✅ |
| POST | `/users` | 新增用户 | ✅ |
| PUT | `/users/{id}` | 修改用户信息 | ✅ |
| DELETE | `/users/{id}` | 删除用户 | ✅ |

### 扩展功能

| 方法 | URL | 功能 | 状态 |
|------|-----|------|------|
| GET | `/users/page` | 分页查询 | ✅ |
| GET | `/users/search` | 模糊搜索（用户名） | ✅ |



---


### 1. 环境准备

- JDK 21+
- MySQL 8.0+
- Maven 3.6+
- IntelliJ IDEA（推荐）

### 2. 导入项目

```bash
git clone [项目地址]
# 或直接解压项目压缩包
-- 创建用户表
CREATE TABLE IF NOT EXISTS user (
                                    id INT PRIMARY KEY AUTO_INCREMENT COMMENT '主键',
                                    username VARCHAR(50) NOT NULL COMMENT '用户名',
                                    age INT COMMENT '年龄',
                                    email VARCHAR(100) COMMENT '邮箱',
                                    create_time DATETIME DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
                                    UNIQUE KEY uk_username (username)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='用户表';

-- 插入测试数据 - 换成英文名
INSERT INTO user (username, age, email) VALUES
                                            ('Jack', 25, 'jack@example.com'),
                                            ('Amy', 30, 'amy@example.com'),
                                            ('Alice', 28, 'alice@example.com');
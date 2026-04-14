package com.example.practice_1.repository;

import com.example.practice_1.model.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

@Repository
public class UserRepository {

    @Autowired
    private JdbcTemplate jdbcTemplate;

    // 自定义 RowMapper
    private static final class UserRowMapper implements RowMapper<User> {
        @Override
        public User mapRow(ResultSet rs, int rowNum) throws SQLException {
            User user = new User();
            user.setId(rs.getInt("id"));
            user.setUsername(rs.getString("username"));
            user.setAge(rs.getInt("age"));
            user.setEmail(rs.getString("email"));
            user.setCreateTime(rs.getTimestamp("create_time"));
            return user;
        }
    }

    // 1. 查询所有用户
    public List<User> findAll() {
        String sql = "SELECT * FROM user ORDER BY id";
        return jdbcTemplate.query(sql, new UserRowMapper());
    }

    // 2. 根据ID查询用户
    public User findById(Integer id) {
        String sql = "SELECT * FROM user WHERE id = ?";
        try {
            return jdbcTemplate.queryForObject(sql, new UserRowMapper(), id);
        } catch (Exception e) {
            return null; // 没找到返回null
        }
    }

    // 3. 新增用户
    public int save(User user) {
        String sql = "INSERT INTO user (username, age, email, create_time) VALUES (?, ?, ?, ?)";
        return jdbcTemplate.update(sql,
                user.getUsername(),
                user.getAge(),
                user.getEmail(),
                user.getCreateTime() != null ? user.getCreateTime() : new java.util.Date()
        );
    }

    // 4. 更新用户
    public int update(User user) {
        String sql = "UPDATE user SET username = ?, age = ?, email = ? WHERE id = ?";
        return jdbcTemplate.update(sql,
                user.getUsername(),
                user.getAge(),
                user.getEmail(),
                user.getId()
        );
    }

    // 5. 删除用户
    public int deleteById(Integer id) {
        String sql = "DELETE FROM user WHERE id = ?";
        return jdbcTemplate.update(sql, id);
    }

    // 扩展：按用户名模糊查询（可选）
    public List<User> findByUsernameContaining(String keyword) {
        String sql = "SELECT * FROM user WHERE username LIKE ? ORDER BY id";
        return jdbcTemplate.query(sql, new UserRowMapper(), "%" + keyword + "%");
    }

    // 扩展：分页查询（可选）
    public List<User> findWithPagination(int page, int size) {
        String sql = "SELECT * FROM user ORDER BY id LIMIT ? OFFSET ?";
        int offset = (page - 1) * size;
        return jdbcTemplate.query(sql, new UserRowMapper(), size, offset);
    }
}
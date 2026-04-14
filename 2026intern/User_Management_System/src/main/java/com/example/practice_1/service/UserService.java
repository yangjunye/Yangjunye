package com.example.practice_1.service;

import com.example.practice_1.model.User;
import com.example.practice_1.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;

@Service
public class UserService {

    @Autowired
    private UserRepository userRepository;

    // 1. 获取所有用户
    public List<User> getAllUsers() {
        return userRepository.findAll();
    }

    // 2. 根据ID获取用户
    public User getUserById(Integer id) {
        return userRepository.findById(id);
    }

    // 3. 创建用户
    public User createUser(User user) {
        // 设置创建时间
        if (user.getCreateTime() == null) {
            user.setCreateTime(new Date());
        }
        userRepository.save(user);
        return user;
    }

    // 4. 更新用户
    public User updateUser(Integer id, User user) {
        // 先检查用户是否存在
        User existingUser = userRepository.findById(id);
        if (existingUser == null) {
            return null;
        }

        // 设置ID
        user.setId(id);
        // 保留原有的创建时间
        user.setCreateTime(existingUser.getCreateTime());

        userRepository.update(user);
        return user;
    }

    // 5. 删除用户 - 这就是你报错缺少的方法！
    public boolean deleteUser(Integer id) {
        int result = userRepository.deleteById(id);
        return result > 0;
    }

    // 扩展方法（可选）
    public List<User> searchByUsername(String keyword) {
        return userRepository.findByUsernameContaining(keyword);
    }

    public List<User> getUsersWithPagination(int page, int size) {
        return userRepository.findWithPagination(page, size);
    }
}
package com.example.practice_1.controller;

import com.example.practice_1.model.User;
import com.example.practice_1.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/users")  // 基路径改为 /users
public class UserController {

    @Autowired
    private UserService userService;

    // 1. 查询所有用户 - GET /users （任务要求）
    @GetMapping("")
    public ResponseEntity<Map<String, Object>> getAllUsers() {
        Map<String, Object> response = new HashMap<>();
        try {
            List<User> users = userService.getAllUsers();
            response.put("success", true);
            response.put("message", "查询成功");
            response.put("data", users);
            return ResponseEntity.ok(response);
        } catch (Exception e) {
            response.put("success", false);
            response.put("message", "查询失败: " + e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(response);
        }
    }

    // 2. 根据ID查询用户 - GET /users/{id} （任务要求）
    @GetMapping("/{id}")
    public ResponseEntity<Map<String, Object>> getUserById(@PathVariable Integer id) {
        Map<String, Object> response = new HashMap<>();
        try {
            User user = userService.getUserById(id);
            if (user != null) {
                response.put("success", true);
                response.put("message", "查询成功");
                response.put("data", user);
                return ResponseEntity.ok(response);
            } else {
                response.put("success", false);
                response.put("message", "用户不存在");
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body(response);
            }
        } catch (Exception e) {
            response.put("success", false);
            response.put("message", "查询失败: " + e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(response);
        }
    }

    // 3. 新增用户 - POST /users （任务要求）
    @PostMapping("")
    public ResponseEntity<Map<String, Object>> createUser(@RequestBody User user) {
        Map<String, Object> response = new HashMap<>();
        try {
            // 简单的参数校验
            if (user.getUsername() == null || user.getUsername().trim().isEmpty()) {
                response.put("success", false);
                response.put("message", "用户名不能为空");
                return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
            }

            User createdUser = userService.createUser(user);
            response.put("success", true);
            response.put("message", "创建成功");
            response.put("data", createdUser);
            return ResponseEntity.status(HttpStatus.CREATED).body(response);
        } catch (Exception e) {
            response.put("success", false);
            response.put("message", "创建失败: " + e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(response);
        }
    }

    // 4. 修改用户信息 - PUT /users/{id} （任务要求）
    @PutMapping("/{id}")
    public ResponseEntity<Map<String, Object>> updateUser(@PathVariable Integer id, @RequestBody User user) {
        Map<String, Object> response = new HashMap<>();
        try {
            User updatedUser = userService.updateUser(id, user);
            if (updatedUser != null) {
                response.put("success", true);
                response.put("message", "更新成功");
                response.put("data", updatedUser);
                return ResponseEntity.ok(response);
            } else {
                response.put("success", false);
                response.put("message", "用户不存在，更新失败");
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body(response);
            }
        } catch (Exception e) {
            response.put("success", false);
            response.put("message", "更新失败: " + e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(response);
        }
    }

    // 5. 删除用户 - DELETE /users/{id} （任务要求）
    @DeleteMapping("/{id}")
    public ResponseEntity<Map<String, Object>> deleteUser(@PathVariable Integer id) {
        Map<String, Object> response = new HashMap<>();
        try {
            boolean deleted = userService.deleteUser(id);
            if (deleted) {
                response.put("success", true);
                response.put("message", "删除成功");
                return ResponseEntity.ok(response);
            } else {
                response.put("success", false);
                response.put("message", "用户不存在，删除失败");
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body(response);
            }
        } catch (Exception e) {
            response.put("success", false);
            response.put("message", "删除失败: " + e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(response);
        }
    }

    // 扩展：分页查询（可选）
    @GetMapping("/page")
    public ResponseEntity<Map<String, Object>> getUsersByPage(
            @RequestParam(defaultValue = "1") int page,
            @RequestParam(defaultValue = "10") int size) {
        Map<String, Object> response = new HashMap<>();
        try {
            List<User> users = userService.getUsersWithPagination(page, size);
            response.put("success", true);
            response.put("message", "分页查询成功");
            response.put("data", users);
            response.put("page", page);
            response.put("size", size);
            return ResponseEntity.ok(response);
        } catch (Exception e) {
            response.put("success", false);
            response.put("message", "分页查询失败: " + e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(response);
        }
    }

    // 扩展：模糊查询（可选）
    @GetMapping("/search")
    public ResponseEntity<Map<String, Object>> searchByUsername(@RequestParam String keyword) {
        Map<String, Object> response = new HashMap<>();
        try {
            List<User> users = userService.searchByUsername(keyword);
            response.put("success", true);
            response.put("message", "搜索成功");
            response.put("data", users);
            response.put("keyword", keyword);
            return ResponseEntity.ok(response);
        } catch (Exception e) {
            response.put("success", false);
            response.put("message", "搜索失败: " + e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(response);
        }
    }
}
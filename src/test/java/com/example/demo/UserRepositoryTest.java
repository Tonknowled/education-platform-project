package com.example.demo;

import com.example.demo.entity.Role;
import com.example.demo.entity.User;
import com.example.demo.repository.UserRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@Transactional
class UserRepositoryTest {

    @Autowired
    private UserRepository userRepository;

    @Test
    void testCreateAndReadUser() {
        // 1. Создаем пользователя
        User user = new User();
        user.setName("Тестовый Студент");
        user.setEmail("test@example.com");
        user.setRole(Role.STUDENT);

        // 2. Сохраняем (CREATE)
        User savedUser = userRepository.save(user);
        assertNotNull(savedUser.getId(), "ID должен быть присвоен");

        // 3. Читаем (READ)
        User foundUser = userRepository.findById(savedUser.getId()).orElse(null);
        assertNotNull(foundUser, "Пользователь должен быть найден");
        assertEquals("Тестовый Студент", foundUser.getName());
        assertEquals("test@example.com", foundUser.getEmail());
    }
}
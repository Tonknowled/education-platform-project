package com.example.demo;

import com.example.demo.entity.*;
import com.example.demo.repository.*;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;

@Component
public class DataInitializer implements CommandLineRunner {

    private final UserRepository userRepository;
    private final CategoryRepository categoryRepository;
    private final CourseRepository courseRepository;

    public DataInitializer(UserRepository userRepository, 
                           CategoryRepository categoryRepository, 
                           CourseRepository courseRepository) {
        this.userRepository = userRepository;
        this.categoryRepository = categoryRepository;
        this.courseRepository = courseRepository;
    }

    @Override
    public void run(String... args) throws Exception {
        // Проверяем, есть ли уже данные, чтобы не дублировать при каждом запуске
        if (userRepository.count() > 0) {
            return;
        }

        // 1. Создаём преподавателя
        User teacher = new User();
        teacher.setName("Иван Преподаватель");
        teacher.setEmail("teacher@example.com");
        teacher.setRole(Role.TEACHER);
        userRepository.save(teacher);

        // 2. Создаём студента
        User student = new User();
        student.setName("Анна Студент");
        student.setEmail("student@example.com");
        student.setRole(Role.STUDENT);
        userRepository.save(student);

        // 3. Создаём категорию
        Category category = new Category();
        category.setName("Программирование");
        categoryRepository.save(category);

        // 4. Создаём курс
        Course course = new Course();
        course.setTitle("Основы Hibernate");
        course.setDescription("Учебный курс по ORM и Hibernate для начинающих.");
        course.setTeacher(teacher);
        course.setCategory(category);
        courseRepository.save(course);

        System.out.println("=========================================");
        System.out.println("✅ База данных успешно заполнена тестовыми данными!");
        System.out.println("Преподаватель: " + teacher.getEmail());
        System.out.println("Студент: " + student.getEmail());
        System.out.println("Курс: " + course.getTitle());
        System.out.println("=========================================");
    }
}
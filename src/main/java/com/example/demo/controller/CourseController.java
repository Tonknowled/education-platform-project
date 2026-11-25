package com.example.demo.controller;

import com.example.demo.entity.Course;
import com.example.demo.entity.Category;
import com.example.demo.entity.User;
import com.example.demo.repository.CourseRepository;
import com.example.demo.repository.CategoryRepository;
import com.example.demo.repository.UserRepository;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/courses")
public class CourseController {

    private final CourseRepository courseRepository;
    private final UserRepository userRepository;
    private final CategoryRepository categoryRepository;

    public CourseController(CourseRepository courseRepository, 
                            UserRepository userRepository, 
                            CategoryRepository categoryRepository) {
        this.courseRepository = courseRepository;
        this.userRepository = userRepository;
        this.categoryRepository = categoryRepository;
    }

    // READ: Получить все курсы (список названий)
    @GetMapping
    public List<String> getAllCourses() {
        return courseRepository.findAll().stream()
                .map(Course::getTitle)
                .collect(Collectors.toList());
    }

    // READ: Получить курс по ID
    @GetMapping("/{id}")
    public String getCourseById(@PathVariable Long id) {
        Course course = courseRepository.findById(id).orElseThrow();
        return "Курс: " + course.getTitle() + ". Описание: " + course.getDescription();
    }

    // CREATE: Создать новый курс
    @PostMapping
    public String createCourse(@RequestBody Map<String, Object> payload) {
        // Достаём данные из JSON, который пришлёт клиент
        String title = (String) payload.get("title");
        String description = (String) payload.get("description");
        Long teacherId = Long.valueOf(payload.get("teacherId").toString());
        Long categoryId = Long.valueOf(payload.get("categoryId").toString());

        // Находим преподавателя и категорию в базе
        User teacher = userRepository.findById(teacherId).orElseThrow();
        Category category = categoryRepository.findById(categoryId).orElseThrow();

        // Создаём и сохраняем курс
        Course newCourse = new Course();
        newCourse.setTitle(title);
        newCourse.setDescription(description);
        newCourse.setTeacher(teacher);
        newCourse.setCategory(category);
        
        courseRepository.save(newCourse);

        return "✅ Курс '" + title + "' успешно создан с ID: " + newCourse.getId();
    }
}
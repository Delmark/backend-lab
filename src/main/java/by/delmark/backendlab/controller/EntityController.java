package by.delmark.backendlab.controller;

import by.delmark.backendlab.pojo.model.Course;
import by.delmark.backendlab.pojo.request.CourseRequest;
import by.delmark.backendlab.service.CourseService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

@RestController
@RequestMapping("/entity")
@RequiredArgsConstructor
@SecurityRequirement(name = "Bearer Authentication")
public class EntityController {

    private final CourseService courseService;

    @Operation(description = "Получение всех курсов")
    @GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<List<Course>> getAllEntities() {
        return ResponseEntity.ok(courseService.getAllCourses());
    }

    @Operation(description = "Получение курса по идентификатору")
    @GetMapping(path = "/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Course> getEntity(@PathVariable Long id) {
        Course course = courseService.getCourseById(id).orElseThrow(()-> new ResponseStatusException(HttpStatus.NOT_FOUND, "Курс не найден"));
        return ResponseEntity.ok(course);
    }

    @Operation(description = "Создание курса")
    @PostMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseStatus(HttpStatus.CREATED)
    public ResponseEntity<Void> createEntity(@Valid @RequestBody CourseRequest course) {
        courseService.saveCourse(course);
        return ResponseEntity.status(HttpStatus.CREATED).build();
    }

    @Operation(description = "Обновление курса (целиком)")
    @PutMapping(path="/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Void> updateEntity(@PathVariable Long id, @Valid @RequestBody CourseRequest course) {
        courseService.updateCourse(id, course);
        return ResponseEntity.ok().build();
    }

    @Operation(description = "Обновление курса (частично)")
    @PatchMapping(path="/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Void> patchEntity(@PathVariable Long id, @RequestBody CourseRequest course) {
        courseService.patchCourse(id, course);
        return ResponseEntity.ok().build();
    }

    @Operation(description = "Удаление курса")
    @DeleteMapping(path="/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Void> deleteEntity(@PathVariable Long id) {
        courseService.deleteCourse(id);
        return ResponseEntity.ok().build();
    }
}

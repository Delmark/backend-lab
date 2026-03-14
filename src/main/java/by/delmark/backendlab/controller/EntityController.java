package by.delmark.backendlab.controller;

import by.delmark.backendlab.pojo.model.Course;
import by.delmark.backendlab.pojo.request.CourseRequest;
import by.delmark.backendlab.service.CourseService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
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
public class EntityController {

    private final CourseService courseService;

    @GetMapping
    public ResponseEntity<List<Course>> getAllEntities() {
        return ResponseEntity.ok(courseService.getAllCourses());
    }

    @GetMapping("/{id}")
    public ResponseEntity<Course> getEntity(@PathVariable Long id) {
        Course course = courseService.getCourseById(id).orElseThrow(()-> new ResponseStatusException(HttpStatus.NOT_FOUND, "Курс не найден"));
        return ResponseEntity.ok(course);
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public ResponseEntity<Void> createEntity(@Valid @RequestBody CourseRequest course) {
        courseService.saveCourse(course);
        return ResponseEntity.status(HttpStatus.CREATED).build();
    }

    @PutMapping("/{id}")
    public ResponseEntity<Void> updateEntity(@PathVariable Long id, @Valid @RequestBody CourseRequest course) {
        courseService.updateCourse(id, course);
        return ResponseEntity.ok().build();
    }

    @PatchMapping("/{id}")
    public ResponseEntity<Void> patchEntity(@PathVariable Long id, @RequestBody CourseRequest course) {
        courseService.patchCourse(id, course);
        return ResponseEntity.ok().build();
    }

    @DeleteMapping("/id")
    public ResponseEntity<Void> deleteEntity(@PathVariable Long id) {
        courseService.deleteCourse(id);
        return ResponseEntity.ok().build();
    }
}

package by.delmark.backendlab.service;

import by.delmark.backendlab.pojo.model.Course;
import by.delmark.backendlab.pojo.request.CourseRequest;

import java.util.List;
import java.util.Optional;

public interface CourseService {

    List<Course> getAllCourses();

    Optional<Course> getCourseById(Long id);

    void saveCourse(CourseRequest course);

    void updateCourse(Long id, CourseRequest course);

    void patchCourse(Long id, CourseRequest course);

    void deleteCourse(Long id);
}

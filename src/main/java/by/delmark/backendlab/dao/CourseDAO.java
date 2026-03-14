package by.delmark.backendlab.dao;

import by.delmark.backendlab.pojo.model.Course;
import by.delmark.backendlab.pojo.request.CourseRequest;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;
import java.util.Optional;

@Mapper
public interface CourseDAO {

    List<Course> getCourses();

    Optional<Course> getCourseById(Long id);

    void insertCourse(CourseRequest course);

    void updateCourse(CourseRequest course, Long id);

    void patchCourse(CourseRequest course, Long id);

    void deleteCourse(Long id);
}

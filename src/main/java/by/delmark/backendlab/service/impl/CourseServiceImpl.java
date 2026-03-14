package by.delmark.backendlab.service.impl;

import by.delmark.backendlab.dao.CourseDAO;
import by.delmark.backendlab.pojo.model.Course;
import by.delmark.backendlab.pojo.request.CourseRequest;
import by.delmark.backendlab.service.CourseService;
import by.delmark.backendlab.utils.CaseUtils;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.validation.ConstraintViolation;
import jakarta.validation.ConstraintViolationException;
import jakarta.validation.Validator;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.Set;

@Service
@RequiredArgsConstructor
public class CourseServiceImpl implements CourseService {

    private final CourseDAO courseDAO;
    private final Validator validator;
    private ObjectMapper objectMapper = new ObjectMapper()
            .setDefaultPropertyInclusion(JsonInclude.Include.NON_NULL);

    @Override
    public List<Course> getAllCourses() {
        return courseDAO.getCourses();
    }

    @Override
    public Optional<Course> getCourseById(Long id) {
        return courseDAO.getCourseById(id);
    }

    @Override
    public void saveCourse(CourseRequest course) {
        courseDAO.insertCourse(course);
    }

    @Override
    public void updateCourse(Long id, CourseRequest course) {
        courseDAO.getCourseById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Обновляемый курс не найден"));
        courseDAO.updateCourse(course, id);
    }

    @Override
    public void patchCourse(Long id, CourseRequest course) {
        validatePatchChanges(course);
        courseDAO.getCourseById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Обновляемый курс не найден"));

        courseDAO.patchCourse(course, id);
    }

    @Override
    public void deleteCourse(Long id) {
        courseDAO.getCourseById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Удаляемый курс не найден"));
        courseDAO.deleteCourse(id);
    }

    private void validatePatchChanges(CourseRequest request) {
        JsonNode requestJson = objectMapper.valueToTree(request);

        // быстрый хак на проверку того что есть хотя бы одно поле
        if (requestJson.isEmpty()) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Передано некорректное тело запроса");
        }

        // Валидация полей которые присутствуют в запросе
        List<String> existingFields = requestJson.propertyStream()
                .map(Map.Entry::getKey)
                .map(CaseUtils::toCamelCase)
                .toList();

        Set<ConstraintViolation<CourseRequest>> violations = new HashSet<>();
        existingFields.forEach(field -> violations.addAll(validator.validateProperty(request, field)));
        if (!violations.isEmpty()) {
            throw new ConstraintViolationException(violations);
        }
    }
}

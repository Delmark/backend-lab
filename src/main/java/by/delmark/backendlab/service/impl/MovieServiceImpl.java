package by.delmark.backendlab.service.impl;

import by.delmark.backendlab.dao.MovieDAO;
import by.delmark.backendlab.pojo.model.Movie;
import by.delmark.backendlab.pojo.request.MovieRequest;
import by.delmark.backendlab.service.MovieService;
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
public class MovieServiceImpl implements MovieService {

    private final MovieDAO movieDAO;
    private final Validator validator;
    private final ObjectMapper objectMapper = new ObjectMapper()
            .setDefaultPropertyInclusion(JsonInclude.Include.NON_NULL);

    @Override
    public List<Movie> getAllMovies() {
        return movieDAO.getMovies();
    }

    @Override
    public Optional<Movie> getMovieById(Long id) {
        return movieDAO.getMovieById(id);
    }

    @Override
    public void saveMovie(MovieRequest movieRequest) {
        movieDAO.insertMovie(movieRequest);
    }

    @Override
    public void updateMovie(Long id, MovieRequest movieRequest) {
        movieDAO.getMovieById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Обновляемый фильм не найден"));
        movieDAO.updateMovie(movieRequest, id);
    }

    @Override
    public void patchMovie(Long id, MovieRequest movieRequest) {
        validatePatchChanges(movieRequest);
        movieDAO.getMovieById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Обновляемый фильм не найден"));

        movieDAO.patchMovie(movieRequest, id);
    }

    @Override
    public void deleteMovie(Long id) {
        movieDAO.getMovieById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Удаляемый фильм не найден"));
        movieDAO.deleteMovie(id);
    }

    private void validatePatchChanges(MovieRequest request) {
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

        Set<ConstraintViolation<MovieRequest>> violations = new HashSet<>();
        existingFields.forEach(field -> violations.addAll(validator.validateProperty(request, field)));
        if (!violations.isEmpty()) {
            throw new ConstraintViolationException(violations);
        }
    }
}

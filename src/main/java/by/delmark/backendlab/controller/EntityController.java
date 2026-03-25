package by.delmark.backendlab.controller;

import by.delmark.backendlab.pojo.model.Movie;
import by.delmark.backendlab.pojo.request.MovieRequest;
import by.delmark.backendlab.service.MovieService;
import io.swagger.v3.oas.annotations.Operation;
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
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

@RestController
@RequestMapping("/entity")
@RequiredArgsConstructor
@SecurityRequirement(name = "Bearer Authentication")
public class EntityController {

    private final MovieService movieService;

    @Operation(summary = "Получение всех фильмов")
    @GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<List<Movie>> getAllEntities() {
        return ResponseEntity.ok(movieService.getAllMovies());
    }

    @Operation(summary = "Получение фильма по идентификатору")
    @GetMapping(path = "/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Movie> getEntity(@PathVariable Long id) {
        Movie movie = movieService.getMovieById(id).orElseThrow(()-> new ResponseStatusException(HttpStatus.NOT_FOUND, "Фильм не найден"));
        return ResponseEntity.ok(movie);
    }

    @Operation(summary = "Создание фильма")
    @PostMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Void> createEntity(@Valid @RequestBody MovieRequest movieRequest) {
        movieService.saveMovie(movieRequest);
        return ResponseEntity.status(HttpStatus.CREATED).build();
    }

    @Operation(summary = "Обновление фильма (целиком)")
    @PutMapping(path="/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Void> updateEntity(@PathVariable Long id, @Valid @RequestBody MovieRequest movieRequest) {
        movieService.updateMovie(id, movieRequest);
        return ResponseEntity.ok().build();
    }

    @Operation(summary = "Обновление фильма (частично)")
    @PatchMapping(path="/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Void> patchEntity(@PathVariable Long id, @RequestBody MovieRequest movieRequest) {
        movieService.patchMovie(id, movieRequest);
        return ResponseEntity.ok().build();
    }

    @Operation(summary = "Удаление фильма")
    @DeleteMapping(path="/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Void> deleteEntity(@PathVariable Long id) {
        movieService.deleteMovie(id);
        return ResponseEntity.ok().build();
    }
}

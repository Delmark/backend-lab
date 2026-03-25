package by.delmark.backendlab.service;

import by.delmark.backendlab.pojo.model.Movie;
import by.delmark.backendlab.pojo.request.MovieRequest;

import java.util.List;
import java.util.Optional;

public interface MovieService {

    List<Movie> getAllMovies();

    Optional<Movie> getMovieById(Long id);

    void saveMovie(MovieRequest movie);

    void updateMovie(Long id, MovieRequest movie);

    void patchMovie(Long id, MovieRequest movie);

    void deleteMovie(Long id);
}

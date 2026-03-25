package by.delmark.backendlab.dao;

import by.delmark.backendlab.pojo.model.Movie;
import by.delmark.backendlab.pojo.request.MovieRequest;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;
import java.util.Optional;

@Mapper
public interface MovieDAO {

    List<Movie> getMovies();

    Optional<Movie> getMovieById(Long id);

    void insertMovie(MovieRequest movie);

    void updateMovie(MovieRequest movie, Long id);

    void patchMovie(MovieRequest movie, Long id);

    void deleteMovie(Long id);
}

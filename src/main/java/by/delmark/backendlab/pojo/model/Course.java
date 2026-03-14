package by.delmark.backendlab.pojo.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AccessLevel;
import lombok.Data;
import lombok.experimental.FieldDefaults;

@Data
@FieldDefaults(level = AccessLevel.PRIVATE)
public class Course {
    @Schema(description = "Идентификатор курса")
    Long id;

    @Schema(description = "Название курса")
    String title;

    @Schema(description = "Автор курса")
    String director;

    @Schema(description = "Год выпуска")
    @JsonProperty("release_year")
    Integer releaseYear;

    @Schema(description = "Рейтинг")
    Double rating;

    @Schema(description = "Доступность")
    Boolean available;
}

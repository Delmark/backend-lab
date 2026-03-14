package by.delmark.backendlab.pojo.request;

import by.delmark.backendlab.validation.YearIsPastOrPresent;
import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AccessLevel;
import lombok.Data;
import lombok.experimental.FieldDefaults;

@Data
@FieldDefaults(level = AccessLevel.PRIVATE)
public class CourseRequest {
    @Schema(description = "Название курса")
    @NotNull
    @NotBlank
    String title;

    @Schema(description = "Автор курса")
    @NotNull
    @NotBlank
    String director;

    @Schema(description = "Год выпуска")
    @JsonProperty("release_year")
    @YearIsPastOrPresent
    Integer releaseYear;

    @Schema(description = "Рейтинг")
    @Min(0)
    @Max(10)
    @NotNull
    Double rating;

    @Schema(description = "Доступность")
    @NotNull
    Boolean available;
}

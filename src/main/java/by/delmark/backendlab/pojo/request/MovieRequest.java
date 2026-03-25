package by.delmark.backendlab.pojo.request;

import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AccessLevel;
import lombok.Data;
import lombok.experimental.FieldDefaults;
import org.hibernate.validator.constraints.Length;

@Data
@FieldDefaults(level = AccessLevel.PRIVATE)
public class MovieRequest {
    @Schema(description = "Название курса")
    @NotNull
    @NotBlank
    @Length(max=120)
    String title;

    @Schema(description = "Автор курса")
    @NotNull
    @NotBlank
    @Length(max=120)
    String director;

    @Schema(description = "Год выпуска")
    @JsonProperty("release_year")
    @NotNull
    @Min(1900)
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

package by.delmark.backendlab.config.security;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import org.springframework.http.converter.json.ProblemDetailJacksonMixin;

import java.net.URI;

import static com.fasterxml.jackson.annotation.JsonInclude.Include.NON_EMPTY;

@JsonInclude(NON_EMPTY)
public interface ProblemDetailsRenameMixin extends ProblemDetailJacksonMixin {

    @JsonProperty("path")
    URI getInstance();
}

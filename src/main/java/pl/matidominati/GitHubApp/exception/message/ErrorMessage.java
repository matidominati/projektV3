package pl.matidominati.GitHubApp.exception.message;

import lombok.Builder;
import lombok.Getter;
import org.springframework.http.HttpStatus;

import java.time.ZonedDateTime;

@Getter
@Builder
public class ErrorMessage {
    private final String message;
    private final HttpStatus httpStatus;
    private final ZonedDateTime timestamp;
}

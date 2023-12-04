package pl.matidominati.GitHubApp.exception.handler;

import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import pl.matidominati.GitHubApp.exception.DataAlreadyExistsException;
import pl.matidominati.GitHubApp.exception.DataNotFoundException;
import pl.matidominati.GitHubApp.exception.message.ErrorMessage;

import java.time.ZonedDateTime;

@ControllerAdvice
@Slf4j
public class GitHubAppExceptionHandler {
    @ExceptionHandler(DataNotFoundException.class)
    public ResponseEntity<Object> handleGitHubException(DataNotFoundException ex) {
        log.error("Data not found exception: {}", ex.getMessage(), ex);
        return ResponseEntity.status(ex.getHttpStatus()).body(ErrorMessage.builder()
                .message(ex.getMessage())
                .httpStatus(ex.getHttpStatus())
                .timestamp(ZonedDateTime.now())
                .build());
    }

    @ExceptionHandler(value
            = DataAlreadyExistsException.class)
    public ResponseEntity<Object> handleGitHubException(DataAlreadyExistsException ex) {
        log.error("Data already exists exception: {}", ex.getMessage(), ex);
        return ResponseEntity.status(ex.getHttpStatus()).body(ErrorMessage.builder()
                .message(ex.getMessage())
                .httpStatus(ex.getHttpStatus())
                .timestamp(ZonedDateTime.now())
                .build());
    }
}
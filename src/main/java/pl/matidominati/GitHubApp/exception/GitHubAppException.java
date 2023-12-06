package pl.matidominati.GitHubApp.exception;

import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
public abstract class GitHubAppException extends RuntimeException {
    private final HttpStatus httpStatus;

    public GitHubAppException(String message, HttpStatus httpStatus) {
        super(message);
        this.httpStatus = httpStatus;
    }
}

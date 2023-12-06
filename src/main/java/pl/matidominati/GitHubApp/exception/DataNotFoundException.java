package pl.matidominati.GitHubApp.exception;

import org.springframework.http.HttpStatus;

public class DataNotFoundException extends GitHubAppException {
    public DataNotFoundException(String message) {
        super(message, HttpStatus.NOT_FOUND);
    }
}

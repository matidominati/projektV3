package pl.matidominati.GitHubApp.exception;

import org.springframework.http.HttpStatus;

public class DataAlreadyExistsException extends GitHubAppException{
    public DataAlreadyExistsException(String message) {
        super(message, HttpStatus.CONFLICT);
    }
}

package net.backend.library_management.exception;

import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
@AllArgsConstructor

public class BookAPIException extends RuntimeException {

    private HttpStatus status;
    private String message;

}

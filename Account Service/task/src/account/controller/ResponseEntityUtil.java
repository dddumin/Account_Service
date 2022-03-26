package account.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.time.LocalDateTime;
import java.util.Map;

public class ResponseEntityUtil {
    public static ResponseEntity<?> getResponseEntity(HttpStatus status, String message, String path) {
        return new ResponseEntity<>(
                Map.of("timestamp", LocalDateTime.now(),
                        "status", status.value(),
                        "error", status.getReasonPhrase(),
                        "message", message,
                        "path", path),
                status
        );
    }

    public static ResponseEntity<?> getResponseEntity(Errors errors, String path) {
        return new ResponseEntity<>(
                Map.of("timestamp", LocalDateTime.now(),
                        "status", errors.getStatus().value(),
                        "error", errors.getStatus().getReasonPhrase(),
                        "message", errors.getMessage(),
                        "path", path),
                errors.getStatus()
        );
    }
}

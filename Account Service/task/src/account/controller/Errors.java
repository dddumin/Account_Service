package account.controller;

import org.springframework.http.HttpStatus;

import static org.springframework.http.HttpStatus.BAD_REQUEST;

public enum Errors {
    USER_EXIST(BAD_REQUEST, "User exist!"),
    PASSWORD_SHORT(BAD_REQUEST, "Password length must be 12 chars minimum!"),
    PASSWORD_BRANCHED(BAD_REQUEST, "The password is in the hacker's database!"),
    PASSWORD_MATCH(BAD_REQUEST, "The passwords must be different!");


    private final HttpStatus status;
    private final String message;


    Errors(HttpStatus status, String message) {
        this.status = status;
        this.message = message;
    }

    public HttpStatus getStatus() {
        return status;
    }

    public String getMessage() {
        return message;
    }
}

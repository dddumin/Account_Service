package account.model.dto;

import org.springframework.http.HttpStatus;

import java.time.LocalDateTime;
import java.util.LinkedHashMap;
import java.util.Map;

import static org.springframework.http.HttpStatus.OK;

public class Result<T> {
    private final HttpStatus httpStatus;
    private T obj;

    public Result(HttpStatus httpStatus) {
        this.httpStatus = httpStatus;
    }

    public Result(HttpStatus httpStatus, T obj) {
        this.httpStatus = httpStatus;
        this.obj = obj;
    }

    public static Result<Map<String, String>> error(HttpDescription httpDescription, String path) {
        Result<Map<String, String>> result = new Result<>(HttpStatus.BAD_REQUEST);
        LinkedHashMap<String, String> infos = new LinkedHashMap<>();
        infos.put("timestamp", LocalDateTime.now().toString());
        infos.put("status", result.httpStatus.getReasonPhrase());
        infos.put("error", String.valueOf(result.httpStatus.value()));
        infos.put("message", httpDescription.getMessage());
        infos.put("path", path);
        result.setObj(infos);
        return result;
    }

    public static <T> Result<T> error(HttpStatus httpStatus) {
        return new Result<>(httpStatus);
    }

    public static <T> Result<T> ok() {
        return new Result<>(OK);
    }

    public static <T> Result<T> ok(T obj) {
        return new Result<>(OK, obj);
    }

    public HttpStatus getHttpStatus() {
        return httpStatus;
    }

    public T getObj() {
        return obj;
    }

    public void setObj(T obj) {
        this.obj = obj;
    }
}

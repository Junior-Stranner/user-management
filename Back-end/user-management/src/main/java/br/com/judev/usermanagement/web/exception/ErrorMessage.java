package br.com.judev.usermanagement.web.exception;

import com.fasterxml.jackson.annotation.JsonInclude;
import jakarta.servlet.http.HttpServletRequest;
import lombok.Getter;
import lombok.ToString;
import org.springframework.http.HttpStatus;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;

import java.util.HashMap;
import java.util.Map;

@Getter
@ToString
public class ErrorMessage {

    private String path;
    private String method;
    private int status;
    private String statusText;
    private String message;
    @JsonInclude(JsonInclude.Include.NON_NULL)
    private Map<String, String> errors;

    public ErrorMessage(HttpServletRequest request, HttpStatus notFound, String message) {
        this.errors = new HashMap<>();
    }

    public ErrorMessage(String path, String method, int status, String statusText, Map<String, String> errors,BindingResult result) {
        this.path = path;
        this.method = method;
        this.status = status;
        this.statusText = statusText;
        this.errors = errors;
        addErrors(result);
    }

    private void addErrors (BindingResult result){
        this.errors = new HashMap<>();
        for (FieldError fiedError : result.getFieldErrors()){
            this.errors.put(fiedError.getField(), fiedError.getDefaultMessage());
        }
    }
}

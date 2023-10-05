package practicespringboot.notesapplicationonline.system.exception;

import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import practicespringboot.notesapplicationonline.system.Result;
import practicespringboot.notesapplicationonline.system.StatusCode;

@RestControllerAdvice
public class ExceptionHandlerAdvice {
    @ExceptionHandler(ObjectNotFoundException.class)
    Result handleObjectNotFoundException(ObjectNotFoundException ex) {
        return new Result(false, StatusCode.NOT_FOUND, ex.getMessage());
    }

}

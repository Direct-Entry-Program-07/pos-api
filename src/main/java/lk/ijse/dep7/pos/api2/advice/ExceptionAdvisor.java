package lk.ijse.dep7.pos.api2.advice;

import lk.ijse.dep7.pos.exception.DuplicateEntityException;
import lk.ijse.dep7.pos.exception.EntityNotFoundException;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.server.ResponseStatusException;

@RestControllerAdvice
public class ExceptionAdvisor {

    @ExceptionHandler(RuntimeException.class)
    public void handleExceptions(RuntimeException ex){
        if (ex instanceof EntityNotFoundException){
            ex.initCause(new ResponseStatusException(HttpStatus.NOT_FOUND, ex.getMessage()));
        }else if (ex instanceof DuplicateEntityException){
            ex.initCause(new ResponseStatusException(HttpStatus.CONFLICT, ex.getMessage()));
        }
        throw ex;
    }
}

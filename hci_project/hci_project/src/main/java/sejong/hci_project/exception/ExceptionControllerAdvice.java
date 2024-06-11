package sejong.hci_project.exception;

import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class ExceptionControllerAdvice {

    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @Order(Ordered.HIGHEST_PRECEDENCE)
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ErrorResult> validExHandle(MethodArgumentNotValidException e){
        return new ResponseEntity<ErrorResult>(new ErrorResult("400: BAD_REQUSET",
                e.getBindingResult().getFieldError().getDefaultMessage())
                ,HttpStatus.BAD_REQUEST);
    }
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler({DuplicateEmailException.class,NoSuchEmaillException.class,WrongPasswordException.class})
    public ResponseEntity<ErrorResult> duplicateEmailExHandle(RuntimeException e){
        return new ResponseEntity<ErrorResult>(new ErrorResult("400: BAD_REQUSET",
                e.getMessage())
                ,HttpStatus.BAD_REQUEST);
    }
}

package org.fansuregrin.handler;

import jakarta.validation.ConstraintViolation;
import jakarta.validation.ConstraintViolationException;
import lombok.extern.slf4j.Slf4j;
import org.fansuregrin.entity.ApiResponse;
import org.fansuregrin.exception.DuplicateResourceException;
import org.fansuregrin.exception.LoginException;
import org.fansuregrin.exception.PermissionException;
import org.fansuregrin.exception.RequestDataException;
import org.springframework.dao.DataAccessException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindException;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.stream.Collectors;

@Slf4j
@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(Exception.class)
    public ResponseEntity<?> handleException(Exception e) {
        log.debug(e.getClass().getName(), e);
        return ResponseEntity.badRequest().body(
            ApiResponse.error(e.getClass().getName() + " " + e.getMessage()));
    }

    @ExceptionHandler(DataAccessException.class)
    public ResponseEntity<?> handleDataAccessException(Exception e) {
        log.debug(e.getClass().getName(), e);
        return ResponseEntity.badRequest().body(ApiResponse.error("数据操作出错"));
    }

    @ExceptionHandler(PermissionException.class)
    public ResponseEntity<?> handlePermissionException(Exception e) {
        log.debug(e.getClass().getName(), e);
        return ResponseEntity.status(HttpStatus.FORBIDDEN).body(
            ApiResponse.error(e.getMessage()));
    }

    @ExceptionHandler(RequestDataException.class)
    public ResponseEntity<?> handleRequestDataException(Exception e) {
        log.debug(e.getClass().getName(), e);
        return ResponseEntity.badRequest().body(ApiResponse.error(e.getMessage()));
    }

    @ExceptionHandler(DuplicateResourceException.class)
    public ResponseEntity<?> handleDuplicateResourceException(Exception e) {
        log.debug(e.getClass().getName(), e);
        return ResponseEntity.status(HttpStatus.CONFLICT).body(
            ApiResponse.error(e.getMessage()));
    }

    @ExceptionHandler(LoginException.class)
    public ResponseEntity<?> handleLoginException(Exception e) {
        log.debug(e.getClass().getName(), e);
        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(
            ApiResponse.error(e.getMessage()));
    }

    @ExceptionHandler({BindException.class, ConstraintViolationException.class,
        MethodArgumentNotValidException.class})
    public ResponseEntity<?> handleValidateException(Exception e) {
        log.debug(e.getClass().getName(), e);
        ApiResponse respBody = null;
        if (e instanceof MethodArgumentNotValidException ex) {
            respBody = ApiResponse.error(ex.getBindingResult().getAllErrors()
                .stream().map(ObjectError::getDefaultMessage)
                .collect(Collectors.joining("; ")));
        } else if (e instanceof ConstraintViolationException ex) {
            respBody = ApiResponse.error(ex.getConstraintViolations()
                .stream().map(ConstraintViolation::getMessage)
                .collect(Collectors.joining("; ")));
        } else if (e instanceof BindException ex) {
            respBody = ApiResponse.error(ex.getAllErrors()
                .stream().map(ObjectError::getDefaultMessage)
                .collect(Collectors.joining("; ")));
        }
        return ResponseEntity.badRequest().body(respBody);
    }
}

package com.apex.wallet.exception;

import jakarta.persistence.OptimisticLockException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ProblemDetail;
import org.springframework.http.ResponseEntity;
import org.springframework.orm.ObjectOptimisticLockingFailureException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import javax.naming.AuthenticationException;
import java.time.Instant;
import java.util.HashMap;
import java.util.Map;

@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler({
            UserNotFoundException.class,
            WalletNotFoundException.class})
    public ResponseEntity<ProblemDetail> handleNotFound(RuntimeException ex){
        ProblemDetail problem = buildProblem(HttpStatus.NOT_FOUND,
                "Resource Not Found",
                ex.getMessage(),
                Instant.now());
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(problem);
    }

    @ExceptionHandler({InvalidTransferException.class, WalletInactiveException.class, InsufficientFundsException.class})
    public ResponseEntity<ProblemDetail> handleBadRequest(RuntimeException ex){
        ProblemDetail problem = buildProblem(HttpStatus.BAD_REQUEST,
                "Business Rule Violation",
                ex.getMessage(),
                Instant.now());
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(problem);

    }

    @ExceptionHandler({MethodArgumentNotValidException.class})
    public ResponseEntity<ProblemDetail> handleParamErrors(MethodArgumentNotValidException ex){
        Map<String,String> errors = new HashMap<>();
        ex.getBindingResult().getFieldErrors().forEach(error ->
                errors.put(error.getField(),error.getDefaultMessage()));
        ProblemDetail problem = buildProblem(HttpStatus.BAD_REQUEST,
                "Validation Error",
                "Validation failed for request payload",
                Instant.now());
        problem.setProperty("invalidParams",errors);
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(problem);
    }

    @ExceptionHandler({ObjectOptimisticLockingFailureException.class, OptimisticLockException.class})
    public ResponseEntity<ProblemDetail> handleConflictForLocks(RuntimeException ex){
        ProblemDetail problem = buildProblem(HttpStatus.CONFLICT,
                "Concurrent Modification Detected",
                "Another transaction modified the wallet balance concurrently. Please retry.",
                Instant.now());
        return ResponseEntity.status(HttpStatus.CONFLICT).body(problem);
    }
    @ExceptionHandler({UserAlreadyExists.class})
    public ResponseEntity<ProblemDetail> handleAlreadyExists(RuntimeException ex){
        ProblemDetail problem = buildProblem(HttpStatus.CONFLICT,
                "Resource Already Exists",
                ex.getMessage(),
                Instant.now());
        return ResponseEntity.status(HttpStatus.CONFLICT).body(problem);
    }
    @ExceptionHandler(AuthenticationException.class)
    public ResponseEntity<ProblemDetail> handleAuthFailure(AuthenticationException ex){
        ProblemDetail problem = buildProblem(HttpStatus.UNAUTHORIZED,
                "Authentication Failed",
                "Invalid email or password",
                Instant.now());
        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(problem);
    }

    private static ProblemDetail buildProblem(HttpStatus httpStatus,String title, String message,Instant timeStamp){
        ProblemDetail problem = ProblemDetail.forStatusAndDetail(httpStatus,message);
        problem.setTitle(title);
        problem.setProperty("timeStamp",timeStamp);
        return problem;
    }
}

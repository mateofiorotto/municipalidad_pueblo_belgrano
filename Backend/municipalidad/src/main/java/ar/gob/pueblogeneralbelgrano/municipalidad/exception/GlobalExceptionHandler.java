package ar.gob.pueblogeneralbelgrano.municipalidad.exception;

import com.auth0.jwt.exceptions.JWTVerificationException;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@RestControllerAdvice
public class GlobalExceptionHandler {

    //BadRequestException
    @ExceptionHandler(value = {BadRequestException.class})
    public ResponseEntity<Object> handleBadRequestException(BadRequestException e) {

        ApiException apiException = new ApiException(
                e.getMessage(),
                e,
                HttpStatus.BAD_REQUEST,
                ZonedDateTime.now(ZoneId.of("Z"))
        );

        return new ResponseEntity<>(apiException, HttpStatus.BAD_REQUEST);
    }

    //NotFoundException
    @ExceptionHandler(value = {NotFoundException.class})
    public ResponseEntity<Object> handleNotFoundException(NotFoundException e) {

        ApiException apiException = new ApiException(
                e.getMessage(),
                e,
                HttpStatus.NOT_FOUND,
                ZonedDateTime.now(ZoneId.of("Z"))
        );

        return new ResponseEntity<>(apiException, HttpStatus.NOT_FOUND);
    }

    //500 - INTERNAL SERVER ERROR
    @ExceptionHandler(value = {Exception.class})
    public ResponseEntity<Object> handleGenericException(Exception e) {
        //payload con error generico 500
        ApiException apiException = new ApiException(
                e.getMessage(),
                e,
                HttpStatus.INTERNAL_SERVER_ERROR,
                ZonedDateTime.now(ZoneId.of("Z"))
        );

        return new ResponseEntity<>(apiException, HttpStatus.INTERNAL_SERVER_ERROR);
    }

    //403 forbidden - Access denied
    @ExceptionHandler(value = {AccessDeniedException.class})
    public ResponseEntity<Object> handleAccessDeniedException(Exception e) {
        ApiException apiException = new ApiException(
                e.getMessage(),
                e,
                HttpStatus.FORBIDDEN,
                ZonedDateTime.now(ZoneId.of("Z"))
        );

        return new ResponseEntity<>(apiException, HttpStatus.FORBIDDEN);
    }

    //401 unauthorized - BadCredentials
    @ExceptionHandler(value = {BadCredentialsException.class})
    public ResponseEntity<Object> handleBadCredentialsException(Exception e){
        ApiException apiException = new ApiException(
                e.getMessage(),
                e,
                HttpStatus.UNAUTHORIZED,
                ZonedDateTime.now(ZoneId.of("Z"))
        );

        return new ResponseEntity<>(apiException, HttpStatus.UNAUTHORIZED);
    }

    //401 unauthorized - JWTVerification
    @ExceptionHandler(value = {JWTVerificationException.class})
    public ResponseEntity<Object> handleJWTVerificationException(Exception e){
        ApiException apiException = new ApiException(
                e.getMessage(),
                e,
                HttpStatus.UNAUTHORIZED,
                ZonedDateTime.now(ZoneId.of("Z"))
        );

        return new ResponseEntity<>(apiException, HttpStatus.UNAUTHORIZED);
    }

    //409 conflict
    @ExceptionHandler(value = {ConflictException.class})
    public ResponseEntity<Object> handleConflictException(ConflictException e) {

        ApiException apiException = new ApiException(
                e.getMessage(),
                e,
                HttpStatus.CONFLICT,
                ZonedDateTime.now(ZoneId.of("Z"))
        );

        return new ResponseEntity<>(apiException, HttpStatus.CONFLICT);
    }

}

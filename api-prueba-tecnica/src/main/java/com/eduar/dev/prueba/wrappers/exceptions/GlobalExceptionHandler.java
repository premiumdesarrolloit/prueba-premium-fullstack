package com.eduar.dev.prueba.wrappers.exceptions;

import com.eduar.dev.prueba.wrappers.exceptions.model.ErrorResponse;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.JwtException;
import io.jsonwebtoken.MalformedJwtException;
import io.jsonwebtoken.UnsupportedJwtException;
import jakarta.servlet.http.HttpServletRequest;
import lombok.extern.slf4j.Slf4j;
import org.hibernate.LazyInitializationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;



@ControllerAdvice
@Slf4j
public class GlobalExceptionHandler extends ResponseEntityExceptionHandler {

    @ExceptionHandler(GlobalException.class)
    public ResponseEntity<ErrorResponse> handleExceptionGlobal(GlobalException exception, HttpServletRequest request) {
        ErrorResponse errorGlobal = new ErrorResponse(
                HttpStatus.BAD_REQUEST.value(),
                "BAD_REQUEST",
                exception.getMessage() != null ? exception.getMessage() : "",
                exception.getDetails(),
                request.getServletPath()
        );

        return new ResponseEntity<>(errorGlobal, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(BadCredentialsException.class)
    public ResponseEntity<ErrorResponse> handleExceptionBadCredentials(BadCredentialsException exception, HttpServletRequest request) {
        ErrorResponse errorGlobal = new ErrorResponse(
                HttpStatus.UNAUTHORIZED.value(),
                "UNAUTHORIZED",
                exception.getMessage() != null ? exception.getMessage() : "",
                "Email o password incorrecta XD",
                request.getServletPath()
        );

        return new ResponseEntity<>(errorGlobal, HttpStatus.UNAUTHORIZED);
    }

    @ExceptionHandler(AccessDeniedException.class)
    public ResponseEntity<ErrorResponse> handleAccessDeniedException(AccessDeniedException exception, HttpServletRequest request) {
        ErrorResponse errorAccessDenied = new ErrorResponse(
                HttpStatus.FORBIDDEN.value(),
                "FORBIDDEN",
                exception.getMessage(),
                "No tienes permisos para acceder a este recurso.",
                request.getServletPath()
        );
        return new ResponseEntity<>(errorAccessDenied, HttpStatus.FORBIDDEN);
    }

    @ExceptionHandler(ExpiredJwtException.class)
    public ResponseEntity<ErrorResponse> handleExpiredJwtException(
            ExpiredJwtException expiredJwtException,
            HttpServletRequest request
    ) {
        ErrorResponse errorExpiredJwt = new ErrorResponse(
                HttpStatus.UNAUTHORIZED.value(),
                "UNAUTHORIZED",
                expiredJwtException.getMessage(),
                "El token JWT ha Expirado",
                request.getServletPath()
        );
        return new ResponseEntity<>(errorExpiredJwt, HttpStatus.UNAUTHORIZED);
    }

    @ExceptionHandler(MalformedJwtException.class)
    public ResponseEntity<ErrorResponse> handleMalformedJwtException(MalformedJwtException ex, HttpServletRequest request) {
        ErrorResponse errorJwt = new ErrorResponse(
                HttpStatus.BAD_REQUEST.value(),
                "BAD_REQUEST",
                ex.getMessage(),
                "El token JWT no es valido",
                request.getServletPath()
        );
        return new ResponseEntity<>(errorJwt, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(UnsupportedJwtException.class)
    public ResponseEntity<String> handleUnsupportedJwtException(UnsupportedJwtException ex) {
        return ResponseEntity.status(HttpStatus.UNAUTHORIZED)
                .body("Error: JWT no soportado. Detalles: " + ex.getMessage());
    }

    @ExceptionHandler(JwtException.class)
    public ResponseEntity<String> handleJwtException(JwtException ex) {
        return ResponseEntity.status(HttpStatus.UNAUTHORIZED)
                .body("Error: JWT inválido. Detalles: " + ex.getMessage());
    }

    @ExceptionHandler(IllegalArgumentException.class)
    public ResponseEntity<ErrorResponse> handleIllegalArgumentException(IllegalArgumentException ex, HttpServletRequest request) {
        ErrorResponse errorIllegalArgument = new ErrorResponse(
                HttpStatus.BAD_REQUEST.value(),
                "BAD_REQUEST",
                ex.getMessage(),
                "Hubo un error inesperado, intentelo nuevamente",
                request.getServletPath()
        );
        return new ResponseEntity<>(errorIllegalArgument, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(LazyInitializationException.class)
    public ResponseEntity<ErrorResponse> handleLazyInitializationException(LazyInitializationException ex, HttpServletRequest request) {
        ErrorResponse errorLazyInitialization = new ErrorResponse(
                HttpStatus.BAD_REQUEST.value(),
                "BAD_REQUEST",
                ex.getMessage(),
                "Hubo un error inesperado, intentelo nuevamente",
                request.getServletPath()
        );
        return new ResponseEntity<>(errorLazyInitialization, HttpStatus.BAD_REQUEST);
    }

}

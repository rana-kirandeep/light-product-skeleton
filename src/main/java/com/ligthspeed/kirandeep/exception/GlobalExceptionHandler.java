package com.ligthspeed.kirandeep.exception;


import com.ligthspeed.kirandeep.response.ErrorResponse;
import com.ligthspeed.kirandeep.util.ProductUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.core.env.Environment;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import java.time.LocalDateTime;
import java.util.Locale;

@Slf4j
@ControllerAdvice
public class GlobalExceptionHandler {

    public static final String ERROR_FAIL_TO_SAVE_PRODUCT = "1001";
    public static final String ERROR_GENERIC = "1000";

    public static final String ERROR_VALIDATION_FAIL = "1002";
    @Autowired
    Environment environment;
    @Autowired
    private MessageSource messageSource;


    @ExceptionHandler(ApiException.class)
    public ResponseEntity handleApiException(ApiException ex) {
        return ResponseEntity.status(HttpStatus.OK).body(new
                ErrorResponse(LocalDateTime.now(),ERROR_FAIL_TO_SAVE_PRODUCT,
                messageSource.getMessage(ERROR_FAIL_TO_SAVE_PRODUCT, null, Locale.ENGLISH),
                ex.getErrorDetails(), ex.getTraceId()));
    }


    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity handleMethodArgumentNotValidException(MethodArgumentNotValidException ex) {
        var traceId = ProductUtil.getUUID();
        return ResponseEntity.status(HttpStatus.OK).body(new
                ErrorResponse(LocalDateTime.now(),ERROR_VALIDATION_FAIL,
                messageSource.getMessage(ERROR_VALIDATION_FAIL, null, Locale.ENGLISH),
                ex.getBindingResult().getFieldError().getDefaultMessage(), traceId));
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity handleGenericException(Exception ex) {
        var traceId = ProductUtil.getUUID();
        log.error("traceId [{}]: Something went wrong", traceId, ex);
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(new
                ErrorResponse(LocalDateTime.now(),ERROR_GENERIC,
                messageSource.getMessage(ERROR_GENERIC, null, Locale.ENGLISH),
                messageSource.getMessage(ERROR_GENERIC, null, Locale.ENGLISH), traceId));
    }

}

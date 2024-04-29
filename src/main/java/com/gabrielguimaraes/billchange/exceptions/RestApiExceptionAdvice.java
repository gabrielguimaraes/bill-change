package com.gabrielguimaraes.billchange.exceptions;

import java.security.InvalidParameterException;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import com.gabrielguimaraes.billchange.model.MessageError;

@ControllerAdvice
public class RestApiExceptionAdvice {

    @ExceptionHandler({ NotEnoughCoinsException.class, InvalidParameterException.class })
    public ResponseEntity<MessageError> handleNotEnoughCoinsException(Exception ex) {
        MessageError messageError = new MessageError(ex.getMessage(), HttpStatus.BAD_REQUEST.value(),
                HttpStatus.BAD_REQUEST.getReasonPhrase());
        return ResponseEntity.badRequest().body(messageError);
    }

}

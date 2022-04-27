package com.banksource.onlinebank.advice;

import java.util.Date;

import com.banksource.onlinebank.exception.TokenRefreshException;
import com.banksource.onlinebank.payload.response.data.ErrorMessageData;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.context.request.WebRequest;

@RestControllerAdvice
public class TokenControllerAdvice {

    @ExceptionHandler(value = TokenRefreshException.class)
    @ResponseStatus(HttpStatus.FORBIDDEN)
    public ErrorMessageData handleTokenRefreshException(TokenRefreshException ex, WebRequest request) {
        ErrorMessageData errorMessageData = new ErrorMessageData();
        errorMessageData.setMessage(ex.getMessage());
        errorMessageData.setTimestamp(new Date());
        errorMessageData.setDescription(request.getDescription(false));
        errorMessageData.setStatusCode(HttpStatus.FORBIDDEN.value());

        return errorMessageData;
    }
}

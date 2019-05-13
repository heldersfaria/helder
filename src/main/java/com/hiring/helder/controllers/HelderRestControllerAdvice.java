package com.hiring.helder.controllers;

import com.hiring.helder.exceptions.*;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.convert.ConversionFailedException;
import org.springframework.validation.BindException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import javax.servlet.http.HttpServletRequest;

import static org.springframework.http.HttpStatus.*;

@Slf4j
@RestControllerAdvice(basePackages = {"com.hiring.helder"})
public class HelderRestControllerAdvice {

    @ResponseStatus(INTERNAL_SERVER_ERROR)
    @ExceptionHandler(Throwable.class)
    public void helderApplicationThrowable(final HttpServletRequest req, final Throwable e) {
        logError(req, e);
    }

    @ResponseStatus(BAD_REQUEST)
    @ExceptionHandler({BindException.class, ConversionFailedException.class, IllegalArgumentException.class, VendaSemDiscoException.class, CashBackException.class})
    public void handleValidationExceptions(final HttpServletRequest req, final Exception e) {
        logError(req, e);
    }

    @ResponseStatus(NOT_FOUND)
    @ExceptionHandler({DiscoVinilNaoEncontradoException.class, VendaNaoEncontradaException.class})
    public void handleValidationExceptionsForNotFound(final HttpServletRequest req, final Exception e) {
        logError(req, e);
    }

    @ResponseStatus(INTERNAL_SERVER_ERROR)
    @ExceptionHandler(Exception.class)
    protected void handleGenericException(Exception ex) {
        log.error("Unexpected error.", ex);
    }

    @ResponseStatus(INTERNAL_SERVER_ERROR)
    @ExceptionHandler(DiscoException.class)
    public void catalogoExceptionHadler(final HttpServletRequest req, final Throwable e) {
        logError(req, e);
    }

    private void logError(final HttpServletRequest req, final Throwable e) {
        log.error("Request " + String.valueOf(req.getRequestURL()) + "raised an exception : " + e.getClass().getName(), e);
    }


}
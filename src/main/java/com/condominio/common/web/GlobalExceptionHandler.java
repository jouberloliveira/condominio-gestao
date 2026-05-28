package com.condominio.common.web;

import com.condominio.common.exception.BusinessException;
import jakarta.servlet.http.HttpServletRequest;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.ui.Model;
import org.springframework.validation.BindException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;

@ControllerAdvice
public class GlobalExceptionHandler {

    private static final Logger log = LoggerFactory.getLogger(GlobalExceptionHandler.class);

    @ExceptionHandler(BusinessException.class)
    @ResponseStatus(HttpStatus.UNPROCESSABLE_ENTITY)
    public String handleBusinessException(BusinessException ex, Model model, HttpServletRequest req) {
        log.warn("BusinessException at {}: {}", req.getRequestURI(), ex.getMessage());
        model.addAttribute("errorMessage", ex.getMessage());
        model.addAttribute("errorTitle", "Erro de Negócio");
        return "error/business";
    }

    @ExceptionHandler(BindException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public String handleBindException(BindException ex, Model model, HttpServletRequest req) {
        log.warn("ValidationException at {}: {}", req.getRequestURI(), ex.getMessage());
        model.addAttribute("errorMessage", "Dados inválidos: " + ex.getBindingResult().getFieldError().getDefaultMessage());
        model.addAttribute("errorTitle", "Erro de Validação");
        return "error/business";
    }

    @ExceptionHandler(Exception.class)
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    public String handleGenericException(Exception ex, Model model, HttpServletRequest req) {
        log.error("Unexpected error at {}", req.getRequestURI(), ex);
        model.addAttribute("errorMessage", "Ocorreu um erro inesperado. Tente novamente.");
        model.addAttribute("errorTitle", "Erro Interno");
        return "error/generic";
    }
}

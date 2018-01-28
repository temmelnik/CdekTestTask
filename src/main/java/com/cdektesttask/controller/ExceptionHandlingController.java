package com.cdektesttask.controller;

import com.cdektesttask.model.Note;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.MessageSource;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.http.HttpStatus;
import org.springframework.ui.Model;
import org.springframework.validation.BindException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Controller for Exception
 * Error name and error message get injected in error page template
 **/
@ControllerAdvice
public class ExceptionHandlingController {
    private static final Logger logger = LoggerFactory.getLogger(ExceptionHandlingController.class);

    private final MessageSource messageSource;

    public ExceptionHandlingController(MessageSource messageSource) {
        this.messageSource = messageSource;
    }

    /**
     * Specific handling validation exception for {@link Note} object,
     * which can be thrown by {@link NoteController#addNote(Note)} method
     **/
    @ExceptionHandler(BindException.class)
    public String noteValidationError(BindException e, Model model){
        if (e.getFieldError().getField().equals("record")){
            model.addAttribute("title", getLocalizedMessage("error.title"));
            model.addAttribute("error", getLocalizedMessage("error.validation.error"));
            model.addAttribute("message", getLocalizedMessage("error.validation.message"));
        }
        return "pages/error";
    }

    @ExceptionHandler(Exception.class)
    public String handleError(HttpServletRequest req, HttpServletResponse res, Exception e, Model model) {
        logger.error("Request: " + req.getRequestURL(), e);
        res.setStatus(HttpStatus.INTERNAL_SERVER_ERROR.value());
        model.addAttribute("title", getLocalizedMessage("error.title"));
        model.addAttribute("error", e.getClass().getSimpleName());
        model.addAttribute("message", e.getMessage());
        return "pages/error";
    }

    private String getLocalizedMessage(String message){
        return messageSource.getMessage(message, null, LocaleContextHolder.getLocale());
    }
}

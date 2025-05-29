package com.example.online_vote.controller;

import org.springframework.web.HttpRequestMethodNotSupportedException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

/**
 * Global exception handler to gracefully redirect users
 * when they access endpoints using unsupported HTTP methods.
 */
@ControllerAdvice
public class GlobalExceptionHandler {

    /**
     * Handle HTTP method not supported exceptions (e.g., GET on a POST-only URL).
     * Redirects users to the voter registration page.
     */
    @ExceptionHandler(HttpRequestMethodNotSupportedException.class)
    public String handleMethodNotSupported() {
        return "redirect:/voter/register"; // Fixed path
    }
}

package com.prankur.eCommerce.exceptions;

import com.prankur.eCommerce.exceptions.customExceptions.InvalidTokenException;
import com.prankur.eCommerce.exceptions.customExceptions.ResourceAlreadyExistException;
import com.prankur.eCommerce.exceptions.customExceptions.ResourceNotFoundException;
import com.prankur.eCommerce.exceptions.genericErrorResponse.ApiErrorResponse;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.mail.MailException;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@ControllerAdvice
@RestController
public class CustomResponseExceptionHandler extends ResponseEntityExceptionHandler
{
    @Override
    protected ResponseEntity<Object> handleMethodArgumentNotValid(
            MethodArgumentNotValidException ex,
            HttpHeaders headers,
            HttpStatus status,
            WebRequest request)
    {
        List<String> descriptions = new ArrayList<>();
        descriptions.add(ex.getMessage());
        ApiErrorResponse apiErrorResponse = new ApiErrorResponse(new Date(), HttpStatus.BAD_REQUEST,descriptions);
//        apiErrorResponse.setMessage("Validation Error");
        return super.handleMethodArgumentNotValid(ex, headers, status, request);
    }

    @ExceptionHandler(BadCredentialsException.class)
    protected ResponseEntity<Object> handleBadCredentialsException
            (BadCredentialsException exception, WebRequest request)
    {
        List<String> descriptions = new ArrayList<>();
        descriptions.add(exception.getMessage());
        ApiErrorResponse apiErrorResponse = new ApiErrorResponse(new Date(),HttpStatus.BAD_REQUEST,descriptions);
        return new ResponseEntity<Object>(apiErrorResponse,apiErrorResponse.getStatus());
    }

    @ExceptionHandler(ResourceAlreadyExistException.class)
    protected ResponseEntity<Object> handleResourceAlreadyExistsException
            (ResourceAlreadyExistException exception, WebRequest request)
    {
        List<String> descriptions = new ArrayList<>();
        descriptions.add(exception.getMessage());
        ApiErrorResponse apiErrorResponse = new ApiErrorResponse(new Date(),HttpStatus.CONFLICT,descriptions);
        return new ResponseEntity<Object>(apiErrorResponse,apiErrorResponse.getStatus());
    }

    @ExceptionHandler(InvalidTokenException.class)
    protected ResponseEntity<Object> handleInvalidTokenException
            (InvalidTokenException exception, WebRequest request)
    {
        List<String> descriptions = new ArrayList<>();
        descriptions.add(exception.getMessage());
        ApiErrorResponse apiErrorResponse = new ApiErrorResponse(new Date(),HttpStatus.BAD_REQUEST,descriptions);
        return new ResponseEntity<Object>(apiErrorResponse,apiErrorResponse.getStatus());
    }

    @ExceptionHandler(ResourceNotFoundException.class)
    protected ResponseEntity<Object> handleResourceNotFoundException
            (ResourceNotFoundException exception, WebRequest request)
    {
        List<String> descriptions = new ArrayList<>();
        descriptions.add(exception.getMessage());
        ApiErrorResponse apiErrorResponse = new ApiErrorResponse(new Date(),HttpStatus.NOT_FOUND,descriptions);
        return new ResponseEntity<Object>(apiErrorResponse,apiErrorResponse.getStatus());
    }


    @ExceptionHandler({MethodArgumentTypeMismatchException.class})
    public ResponseEntity<Object> handleMethodArgumentTypeMismatch(
            MethodArgumentTypeMismatchException ex, WebRequest request) {
        String error =
                ex.getName() + " should be of type " + ex.getRequiredType().getName();

        List<String> descriptions = new ArrayList<>();
        descriptions.add(error);
        ApiErrorResponse apiError = new ApiErrorResponse(new Date(), HttpStatus.BAD_REQUEST, descriptions);
        return new ResponseEntity<Object>(
                apiError, apiError.getStatus());
    }

    @ExceptionHandler(MailException.class)
    public ResponseEntity<Object> handleMailSendFailure(Exception ex, WebRequest request) {

        List<String> descriptions = new ArrayList<>();
        descriptions.add("Mail Send Failure.. \n try to activate account by using resend mail option..");
        ApiErrorResponse apiError = new ApiErrorResponse(
                HttpStatus.BAD_GATEWAY, descriptions);
        return new ResponseEntity<Object>(
                apiError, apiError.getStatus());
    }

//    @ExceptionHandler({Exception.class})
//    public ResponseEntity<Object> handleAll(Exception ex, WebRequest request) {
//        ApiErrorResponse apiError = new ApiErrorResponse(
//                HttpStatus.BAD_REQUEST, descriptions);
//        return new ResponseEntity<Object>(
//                apiError, apiError.getStatus());
//    }

    //    @ExceptionHandler(MethodArgumentNotValidException.class)
//    protected ResponseEntity<Object> handleMethodArgumentNotValid
//            (MethodArgumentNotValidException ex, HttpStatus status, WebRequest request) {
//        List<String> descriptions = new ArrayList<>();
//        for(ObjectError error : ex.getBindingResult().getAllErrors()) {
//            descriptions.add(error.getDefaultMessage());
//        }
//        ApiErrorResponse apiErrorResponse = new ApiErrorResponse(new Date(),HttpStatus.NOT_ACCEPTABLE, descriptions);
//        return new ResponseEntity(apiErrorResponse,apiErrorResponse.getStatus());
//    }
//
//    @ExceptionHandler(IllegalArgumentException.class)
//    protected ResponseEntity<Object> handleIllegalArgumentException
//            (IllegalArgumentException ex, HttpStatus status, WebRequest request) {
//        List<String> descriptions = new ArrayList<>();
////        for(ObjectError error : ex..getAllErrors()) {
//            descriptions.add(ex.getMessage());
//
//        ApiErrorResponse apiErrorResponse = new ApiErrorResponse(new Date(),HttpStatus.NOT_ACCEPTABLE, descriptions);
//        return new ResponseEntity(apiErrorResponse,apiErrorResponse.getStatus());
//    }


}

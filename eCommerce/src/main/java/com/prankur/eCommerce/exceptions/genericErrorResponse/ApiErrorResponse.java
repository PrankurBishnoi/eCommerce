package com.prankur.eCommerce.exceptions.genericErrorResponse;

import org.springframework.http.HttpStatus;
import org.springframework.validation.FieldError;
import org.springframework.validation.ObjectError;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class ApiErrorResponse
{
    private Date timeStamp;
    private HttpStatus status;
    private String descriptions;
    private List<ApiSubError> subErrors;

    public ApiErrorResponse(Date timeStamp) {
        this.timeStamp = timeStamp;
    }

    public ApiErrorResponse(HttpStatus status) {
        this.status = status;
    }

    public ApiErrorResponse(String descriptions) {
        this.descriptions = descriptions;
    }

    public ApiErrorResponse(Date timeStamp, HttpStatus status, String descriptions) {
        this.timeStamp = timeStamp;
        this.status = status;
        this.descriptions = descriptions;
    }

    public ApiErrorResponse(HttpStatus status, String descriptions) {
        this.status = status;
        this.descriptions = descriptions;
    }

    public Date getTimeStamp() {
        return timeStamp;
    }

    public void setTimeStamp(Date timeStamp) {
        this.timeStamp = timeStamp;
    }

    public HttpStatus getStatus() {
        return status;
    }

    public void setStatus(HttpStatus status) {
        this.status = status;
    }

    public String getDescriptions() {
        return descriptions;
    }

    public void setDescriptions(String descriptions) {
        this.descriptions = descriptions;
    }

    public List<ApiSubError> getSubErrors() {
        return subErrors;
    }

    public void setSubErrors(List<ApiSubError> subErrors) {
        this.subErrors = subErrors;
    }

    void addSubError(ApiSubError subError)
    {
        if (subErrors == null)
        {
            subErrors = new ArrayList<ApiSubError>();
        }
        subErrors.add(subError);
    }

    private void addValidationError(String object, String field, Object rejectedValue, String message) {
        addSubError(new ApiValidationError(object, field, rejectedValue, message));
    }

    private void addValidationError(String object, String message) {
        addSubError(new ApiValidationError(object, message));
    }

    void addValidationError(FieldError fieldError) {
        this.addValidationError(fieldError.getObjectName(), fieldError.getField(), fieldError.getRejectedValue(), fieldError.getDefaultMessage());

    }

    public void addValidationErrors(List<FieldError> fieldErrors) {
        fieldErrors.forEach(fieldError -> this.addValidationError(fieldError));

    }

    void addValidationError(ObjectError objectError) {
        this.addValidationError(objectError.getObjectName(), objectError.getDefaultMessage());

    }

    public void addValidationError(List<ObjectError> objectErrors) {
        objectErrors.forEach(objectError -> this.addValidationError(objectError));

    }

    public ApiErrorResponse() {
    }
}

package com.prankur.eCommerce.exceptions.genericErrorResponse;

import org.springframework.http.HttpStatus;

import java.util.Date;
import java.util.List;

public class ApiErrorResponse
{
    private Date timeStamp;
    private HttpStatus status;
    private List<String> descriptions;

    public ApiErrorResponse(Date timeStamp) {
        this.timeStamp = timeStamp;
    }

    public ApiErrorResponse(HttpStatus status) {
        this.status = status;
    }

    public ApiErrorResponse(List<String> descriptions) {
        this.descriptions = descriptions;
    }

    public ApiErrorResponse(Date timeStamp, HttpStatus status, List<String> descriptions) {
        this.timeStamp = timeStamp;
        this.status = status;
        this.descriptions = descriptions;
    }

    public ApiErrorResponse(HttpStatus status, List<String> descriptions) {
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

    public List<String> getDescriptions() {
        return descriptions;
    }

    public void setDescriptions(List<String> descriptions) {
        this.descriptions = descriptions;
    }

    public ApiErrorResponse() {
    }
}

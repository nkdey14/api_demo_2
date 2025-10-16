package com.api_demo.payload;

import java.util.Date; // added import

public class ErrorDetails {
    private String message;
    private Date date;
    private String details;

    public ErrorDetails(String message, Date date, String details) {
        this.message = message;
        this.date = date;
        this.details = details;
    }

    public String getMessage() {
        return message;
    }

    public Date getDate() {
        return date;
    }

    public String getDetails() {
        return details;
    }

}

package com.bangladesh20.backend.Error;

import lombok.Builder;
import lombok.Data;
import org.springframework.http.HttpStatus;

import java.time.LocalDateTime;

@Data
@Builder
public class ApiError {
    private int statusCode;
    private String error;
    private String errorMessage;
    private LocalDateTime timeStamp;
}

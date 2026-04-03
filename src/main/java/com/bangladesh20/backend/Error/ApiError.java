package com.bangladesh20.backend.Error;

import lombok.Builder;
import lombok.Data;
import org.springframework.http.HttpStatus;

import java.time.LocalDateTime;

@Data
@Builder
public class ApiError {
    private HttpStatus statusCode;
    private String error;
    private String errorMessage;
    private LocalDateTime timeStamp;
}

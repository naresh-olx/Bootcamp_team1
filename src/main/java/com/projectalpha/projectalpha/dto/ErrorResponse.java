package com.projectalpha.projectalpha.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import org.springframework.http.HttpStatusCode;

@Data
@AllArgsConstructor
public class ErrorResponse {
    private HttpStatusCode errorCode;
    private String errorMessage;
}

package com.projectalpha.projectalpha.dto;

import lombok.Builder;
import lombok.Data;

import java.time.LocalDateTime;

@Data
@Builder
public class UserResponseDTO {
    private String userId;
    private String userName;
    private String emailId;
}

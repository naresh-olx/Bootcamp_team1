package com.projectalpha.projectalpha.mapper;

import com.projectalpha.projectalpha.dto.UserRequestDTO;
import com.projectalpha.projectalpha.dto.UserResponseDTO;
import com.projectalpha.projectalpha.entity.UserEntity;

import java.util.UUID;

public class UserMapper {
    public static UserEntity toEntity(UserRequestDTO dto) {
        return UserEntity.builder()
                .userId(UUID.randomUUID().toString().split("-")[0])
                .userName(dto.getUserName())
                .emailId(dto.getEmailId())
                .password(dto.getPassword())
                .build();
    }

    public static UserResponseDTO toResponse(UserEntity user) {
        return UserResponseDTO.builder()
                .userId(user.getUserId())
                .userName(user.getUserName())
                .emailId(user.getEmailId())
                .createdAt(user.getCreatedAt())
                .build();
    }
}

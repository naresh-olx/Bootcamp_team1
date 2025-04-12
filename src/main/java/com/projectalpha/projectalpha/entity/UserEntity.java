package com.projectalpha.projectalpha.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.LocalDateTime;
import java.util.UUID;

@Document(collection = "users")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class UserEntity {

    @Id
    private String userId = UUID.randomUUID().toString().split("-")[0];

    private String userName;

    @Indexed(unique = true)
    private String emailId;

    private String password;

    @CreatedDate
    private LocalDateTime createdAt;
}

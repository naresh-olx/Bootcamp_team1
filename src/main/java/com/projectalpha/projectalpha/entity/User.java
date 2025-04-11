package com.projectalpha.projectalpha.entity;

import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.UUID;

@Document(collection = "inventories")
@Data
@NoArgsConstructor
public class User {
  @Id
  private UUID userId = UUID.randomUUID();
  private String userName;
  private String password;
  private String email;
}

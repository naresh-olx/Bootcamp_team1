package com.projectalpha.projectalpha.entity;


import lombok.AllArgsConstructor;
import lombok.Builder;
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
  private UUID userId;
  private String userName;
  private String password;
  private String email;
}

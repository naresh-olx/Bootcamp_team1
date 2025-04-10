package com.projectalpha.projectalpha.entity;


import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Document(collection = "inventories")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class User {
  @Id
  private String userId;
  private String userName;
  private String password;
  private String email;
}

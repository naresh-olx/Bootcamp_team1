package com.projectalpha.repository;


import com.projectalpha.entity.UserEntity;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface UserRepository extends MongoRepository<UserEntity, String> {
    UserEntity findByEmailId(String emailId);
    boolean existsByEmailId(String emailId);
}

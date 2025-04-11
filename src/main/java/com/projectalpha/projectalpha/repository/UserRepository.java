package com.projectalpha.projectalpha.repository;


import com.projectalpha.projectalpha.entity.UserEntity;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.UUID;

public interface UserRepository extends MongoRepository<UserEntity, UUID> {

}

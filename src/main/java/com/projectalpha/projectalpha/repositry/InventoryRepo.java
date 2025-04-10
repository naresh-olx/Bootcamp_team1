package com.projectalpha.projectalpha.repositry;

import com.projectalpha.projectalpha.entity.InventoryEntity;
import org.bson.types.ObjectId;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface InventoryRepo extends MongoRepository<InventoryEntity, ObjectId> {

}

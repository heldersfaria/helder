package com.hiring.helder.Repositories;

import com.hiring.helder.models.DiscoCashBack;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface DiscoCashBackRepository extends MongoRepository<DiscoCashBack, String> {
}

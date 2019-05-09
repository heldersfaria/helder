package com.hiring.helder.Repositories;

import com.hiring.helder.models.CashBack;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;


@Repository
public interface CashBackRepository extends MongoRepository<CashBack, String> {
}

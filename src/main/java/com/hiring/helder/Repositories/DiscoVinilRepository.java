package com.hiring.helder.Repositories;

import com.hiring.helder.models.DiscoVinil;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface DiscoVinilRepository extends MongoRepository<DiscoVinil, String> {
    Page<DiscoVinil> findByGenero(String genero, Pageable pageable);
}

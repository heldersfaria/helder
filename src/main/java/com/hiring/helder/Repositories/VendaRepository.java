package com.hiring.helder.Repositories;

import com.hiring.helder.models.Venda;
import org.springframework.data.domain.Pageable;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;

@Repository
public interface VendaRepository extends MongoRepository<Venda, String> {

    @Query(value = "{'data':{ $gte: ?0, $lte: ?1}}")
    List<Venda> findByDataBetween(LocalDate startDate, LocalDate endDate, Pageable pageable);

}


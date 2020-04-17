package com.tim18.bolnicar.repository;

import com.tim18.bolnicar.model.Drug;
import org.springframework.data.repository.CrudRepository;

public interface DrugRepository extends CrudRepository<Drug, Integer> {
}

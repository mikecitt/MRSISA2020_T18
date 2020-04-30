package com.tim18.bolnicar.service.impl;

import com.tim18.bolnicar.model.Drug;
import com.tim18.bolnicar.repository.DrugRepository;
import com.tim18.bolnicar.service.DrugService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class DrugServiceImpl implements DrugService {

    @Autowired
    private DrugRepository drugRepository;

    @Override
    public Drug findOne(int id) {
        return drugRepository.findById(id).orElse(null);
    }

    @Override
    public List<Drug> findAll() {
        return (List<Drug>)drugRepository.findAll();
    }

    @Override
    public Drug save(Drug drug) {
        return drugRepository.save(drug);
    }

    @Override
    public void remove(int id) {
        drugRepository.deleteById(id);
    }
}

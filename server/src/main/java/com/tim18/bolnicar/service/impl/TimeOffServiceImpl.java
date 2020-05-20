package com.tim18.bolnicar.service.impl;

import com.tim18.bolnicar.model.TimeOff;
import com.tim18.bolnicar.repository.TimeOffRepository;
import com.tim18.bolnicar.service.TimeOffService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class TimeOffServiceImpl implements TimeOffService {

    @Autowired
    TimeOffRepository timeOffRepository;

    @Override
    public TimeOff save(TimeOff timeOff) {
        return timeOffRepository.save(timeOff);
    }

    @Override
    public TimeOff findOne(int id) {
        return timeOffRepository.findById(id).orElseGet(null);
    }
}

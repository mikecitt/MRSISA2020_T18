package com.tim18.bolnicar.service;

import com.tim18.bolnicar.model.TimeOff;

public interface TimeOffService {
    TimeOff save(TimeOff timeOff);
    TimeOff findOne(int id);
}

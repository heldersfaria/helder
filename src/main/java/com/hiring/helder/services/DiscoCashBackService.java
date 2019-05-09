package com.hiring.helder.services;

import com.hiring.helder.Repositories.DiscoCashBackRepository;
import com.hiring.helder.models.DiscoCashBack;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class DiscoCashBackService {

    @Autowired
    private DiscoCashBackRepository discoCashBackRepository;

    public DiscoCashBack save(DiscoCashBack disco) {
        return discoCashBackRepository.save(disco);
    }
}
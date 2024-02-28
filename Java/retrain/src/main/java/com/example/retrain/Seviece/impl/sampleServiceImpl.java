package com.example.retrain.Seviece.impl;
import com.example.retrain.Seviece.sampleService;
import com.example.retrain.Respository.*;
import com.example.retrain.entities.Sample;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class sampleServiceImpl implements  sampleService{
    @Autowired
    sampleRepository sampleRepository;


    @Override
    public List<Sample> findAll() {
        return sampleRepository.findAll();
    }

    @Override
    public Sample finnById(Integer Id) {
        return sampleRepository.findById(Id).orElse(null);
    }

    @Override
    public Optional<Sample> findById(Integer Id) {
        return sampleRepository.findById(Id);
    }
}

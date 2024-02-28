package com.example.retrain.Seviece;

import com.example.retrain.entities.Sample;


import java.util.List;
import java.util.Optional;

public interface sampleService {
    List<Sample> findAll();
    Sample finnById(Integer Id);
    Optional<Sample> findById(Integer Id);


}

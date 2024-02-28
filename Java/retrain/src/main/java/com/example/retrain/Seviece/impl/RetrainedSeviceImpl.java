package com.example.retrain.Seviece.impl;

import com.example.retrain.Respository.RetrainedRespoitory;
import com.example.retrain.Seviece.RetrainedSeveice;
import com.example.retrain.entities.TrainedSample;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class RetrainedSeviceImpl implements RetrainedSeveice {

    @Autowired
    RetrainedRespoitory retrainedRespoitory;

    @Override
    public List <TrainedSample> findAll(){return retrainedRespoitory.findAll();};
}

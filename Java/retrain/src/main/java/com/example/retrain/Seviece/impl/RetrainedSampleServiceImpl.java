package com.example.retrain.Seviece.impl;
import com.example.retrain.entities.*;
import com.example.retrain.Seviece.*;
import com.example.retrain.Respository.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class RetrainedSampleServiceImpl implements RetrainedSampleService{

    @Autowired
    SampleRetraindRepository sampleRetraindRepository;
    @Override
    public List<TrainedSample> GetAllTrainedSampleModel(Integer Id) {
        return null;
    }

    @Override
    public void create(TrainedSample trainedSample) {
        sampleRetraindRepository.save(trainedSample);
    }

    @Override
    public void delete(Integer sampleId , Integer modelId) {
        sampleRetraindRepository.doDelete1(sampleId ,modelId);
    }

    @Override
    public void delete1(Integer modelId) {
        sampleRetraindRepository.doDelete2(modelId);
    }

    @Override
    public TrainedSample findById(Integer Id) {
        return sampleRetraindRepository.findById(Id).orElse(null);
    }

    @Override
    public List<Sample> findSamplesWithoutModel(Integer modelId) {
        return sampleRetraindRepository.findSamplesWithoutModel(modelId);
    }

    @Override
    public List<Sample> findSamplesByModelId(Integer modelId) {
        return sampleRetraindRepository.findSamplesByModelId(modelId);
    }
}

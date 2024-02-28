package com.example.retrain.Seviece;
import com.example.retrain.entities.*;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface RetrainedSampleService {
    List<TrainedSample> GetAllTrainedSampleModel(Integer Id);

    void create (TrainedSample trainedSample);

    void delete(Integer sampleId , Integer modelId);

    void delete1( Integer modelId);

    TrainedSample findById(Integer Id);

    List <Sample>  findSamplesWithoutModel(@Param("modelId") Integer modelId);

    List <Sample>  findSamplesByModelId(@Param("modelId") Integer modelId);

}

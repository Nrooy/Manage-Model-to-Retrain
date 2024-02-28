package com.example.retrain.Respository;

import com.example.retrain.entities.TrainedSample;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface RetrainedRespoitory extends JpaRepository<TrainedSample,Integer> {

}

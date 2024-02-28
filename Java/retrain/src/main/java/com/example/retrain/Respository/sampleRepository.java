package com.example.retrain.Respository;

import com.example.retrain.entities.Sample;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface sampleRepository extends JpaRepository<Sample,Integer> {

}

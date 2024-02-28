package com.example.retrain.Respository;
import com.example.retrain.entities.*;
import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Service;

import java.util.List;

@Transactional
public interface SampleRetraindRepository extends JpaRepository<TrainedSample,Integer> {

    @Query(value = "SELECT s FROM Sample s " +
            "LEFT JOIN TrainedSample ts " +
            "ON s.id = ts.sample.id " +
            "AND ts.model.id = :modelId " +
            "WHERE ts.sample.id IS NULL")
    List<Sample> findSamplesWithoutModel(@Param("modelId") Integer modelId);

    @Query(value = "SELECT s FROM Sample s " +
            "INNER JOIN TrainedSample ts " +
            "ON s.id = ts.sample.id " +
            "AND ts.model.id = :modelId")
    List<Sample> findSamplesByModelId(@Param("modelId") Integer modelId);

    @Modifying
    @Query("DELETE FROM TrainedSample t WHERE t.sample.id = :sampleId AND t.model.id = :modelId")
    void doDelete1(@Param("sampleId") Integer sampleId, @Param("modelId") Integer modelId);

    @Modifying
    @Query("DELETE FROM TrainedSample t WHERE  t.model.id = :modelId")
    void doDelete2( @Param("modelId") Integer modelId);
}

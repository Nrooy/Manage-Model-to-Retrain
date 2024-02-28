package com.example.retrain.entities;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@Entity
@Table(name = "sample")
@AllArgsConstructor
@NoArgsConstructor
public class Sample {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(name = "used_id")
    private String used_id;

    @Column(name = "tags")
    private String tags;

    @JsonIgnore
    @OneToMany(mappedBy = "sample")
    List<TrainedSample> trainedSamples;

}

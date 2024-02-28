package com.example.retrain.entities;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;
import java.util.List;

@Data
@Entity
@Table(name="model")
@AllArgsConstructor
@NoArgsConstructor
public class Model {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(name = "name")
    private String name;

    @Column(name = "link")
    private String link;

    @Column(name = "date_trainning")
    private Date date_trainning;

    @Column(name ="F1")
    private  Float F1;

    @Column(name = "Acc")
    private Float acc;

    @Column(name = "Pre")
    private Float Pre;

    @Column(name ="Re")
    private Float Re;

    @Column(name = "isDefault")
    private boolean dafault;

    @JsonIgnore
    @OneToMany(mappedBy = "model")
    List <TrainedSample> trainedSamples;

}

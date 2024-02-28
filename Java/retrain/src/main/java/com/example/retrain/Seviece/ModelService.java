package com.example.retrain.Seviece;

import com.example.retrain.entities.Model;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;
import java.util.Optional;

public interface ModelService {
    void create1(Model model);

    Model create(Model model);

    List<Model> fillAll();

    Optional<Model> findById(Integer id);

    Model  finById1(Integer id);

    Page<Model> findAll1(Pageable pageable);

    void update (Integer id ,Model model);

    void delete (Integer id);

    List<Model> getDefaulIsModels();

//    Page<Model> doSearch(String keyword, Pageable pageable);
}

package com.example.retrain.Seviece.impl;

import com.example.retrain.Seviece.ModelService;
import com.example.retrain.entities.Model;
import com.example.retrain.Respository.ModelRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class ModelServiceImpl implements ModelService {
    @Autowired
    ModelRepository modelRepository;
    @Override
    public void create1(Model model) {modelRepository.save(model);}
    @Override
    public Model create(Model model){return modelRepository.save(model);}

    @Override
    public List<Model> fillAll() {
        return modelRepository.findAll();
    }

    @Override
    public Optional<Model> findById(Integer id) {
        return modelRepository.findById(id);
    }

    @Override
    public Model finById1(Integer id) {
        return modelRepository.findById(id).orElse(null);
    }

    @Override
    public Page<Model> findAll1(Pageable pageable) {
        return modelRepository.findAll(pageable);
    }

    @Override
    public void update(Integer id, Model model) {

        modelRepository.save(model);
    }
    @Override
    public  void delete(Integer id){
        modelRepository.deleteById(id);
    }
    @Override
    public List<Model> getDefaulIsModels() {
        return modelRepository.findByDefaultIsTrue();
    }

//    @Override
////    public Page<Model> doSearch(String keyword, Pageable pageable) {
////        return modelRepository.doSearch(keyword , pageable);
////    }

}

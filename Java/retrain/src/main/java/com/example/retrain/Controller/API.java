package com.example.retrain.Controller;


import com.example.retrain.Seviece.ModelService;
import com.example.retrain.Seviece.sampleService;
import com.example.retrain.entities.Model;
import com.example.retrain.entities.Sample;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;

import javax.xml.transform.Result;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
public class API {
    @Autowired
    sampleService sampleService;

//    @PostMapping("/send_all_sample")
//    public String send_all_model(@RequestParam("selectedSamples") List<Integer> selectedSamples){
//        List <Sample> listSample = new ArrayList<>();
//        try {
//            for(Integer Id : selectedSamples){
//                Sample s = new Sample();
//                s = sampleService.finnById(Id);
//                listSample.add(s);
//            }
//            ObjectMapper objectMapper=new ObjectMapper();
//            String json = objectMapper.writeValueAsString(listSample);
//            return json;
//        }catch (JsonProcessingException e){
//            e.printStackTrace();
//            return "Error processing Json";
//        }
//    }
    @Autowired
    ModelService modelService;

//    @GetMapping("/send_name_model_active")
//    public String send_name_model_active() throws JsonProcessingException {
//        List <Model> m = new ArrayList<>();
//        m = modelService.getDefaulIsModels();
//        m.sort((a,b) -> a.getDate_trainning().compareTo(b.getDate_trainning()));
//        Model a = m.get(0);
//        String name = a.getName();
//        Map<String, String> result = new HashMap<>();
//        result.put("name", name);
//        ObjectMapper objectMapper = new ObjectMapper();
//        String json = objectMapper.writeValueAsString(result);
//        return json;
//    }
    @GetMapping("/send_name_model_active")
    public String send_name_model_active() throws JsonProcessingException {
        List <Model> m = new ArrayList<>();
        m = modelService.fillAll();
        Model a = m.get(0);
        String name = a.getName();
        Map<String, String> result = new HashMap<>();
        result.put("name", name);
        ObjectMapper objectMapper = new ObjectMapper();
        String json = objectMapper.writeValueAsString(result);
        return json;
    }
}

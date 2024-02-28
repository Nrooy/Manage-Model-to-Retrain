package com.example.retrain.Controller;

import com.example.retrain.Seviece.RetrainedSampleService;
import com.example.retrain.Seviece.RetrainedSeveice;
import com.example.retrain.Seviece.sampleService;
import com.example.retrain.Seviece.ModelService;
import com.example.retrain.entities.*;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;


@Controller
public class RetrainedController {

    @Autowired
    RetrainedSeveice retrainedSeveice;
    @Autowired
    sampleService sampleService;
    @Autowired
    ModelService modelService;
    @Autowired
    RetrainedSampleService retrainedSampleService;

    @GetMapping("/retrain/{id}")
    public String getHomeRetrain(ModelMap model,@PathVariable Integer id ,HttpSession session){
        List <Sample> listSamplesWithoutModel = retrainedSampleService.findSamplesWithoutModel(id);
        List<Sample> listSamplesByModelId = retrainedSampleService.findSamplesByModelId(id);
        List <Sample> listSample = sampleService.findAll();
        model.addAttribute("sample",listSample);
        model.addAttribute("listSamplesWithoutModel",listSamplesWithoutModel);
        model.addAttribute("listSamplesByModelId",listSamplesByModelId );
        session.setAttribute("IdModel",id);
        return "home";
    }


    @PostMapping("/send_all_sample")
    public String send_all_model(@RequestParam(name = "selectedSamples", required = false) Optional<List<Integer>> selectedSamples , HttpSession session , ModelMap modelMap) {

        List<Sample> listSample = new ArrayList<>();
        Integer Id = (Integer) session.getAttribute("IdModel");
        Model oldModel = modelService.finById1(Id);

        List<Sample> listSamplesByModelId = retrainedSampleService.findSamplesByModelId(Id);
        for(Sample s: listSamplesByModelId){
            listSample.add(s);
        }
        try {
            if (selectedSamples.isPresent()) {
                List<Integer> samples = selectedSamples.get();
                for (Integer id : samples) {
                    Optional<Sample> optionalSample = sampleService.findById(id);
                    if (optionalSample.isPresent()) {
                        Sample sample = optionalSample.get();
                        listSample.add(sample);
                    } else {
                        System.err.println("Sample not found with ID: " + id);
                    }
                }
            }
            String pythonServerUrl = "http://127.0.0.1:5000";
            String requestUrl = pythonServerUrl + "/receive_sample";
            HttpHeaders headers = new HttpHeaders();
            headers.setContentType(MediaType.APPLICATION_JSON);

            ObjectMapper objectMapper = new ObjectMapper();
            String json = objectMapper.writeValueAsString(listSample);

            // Chuyển đổi List<Sample> thành JSON
            HttpEntity<String> requestEntity = new HttpEntity<>(json, headers);

            // Create RestTemplate outside the try-with-resources block
            RestTemplate restTemplate = new RestTemplate();

            try {
                ResponseEntity<String> response = restTemplate.postForEntity(requestUrl, requestEntity, String.class);

                // Lưu kết quả vào Model
//                String processedData = response.getBody();
                Model pythonResponse =objectMapper.readValue(response.getBody(), Model.class);
                Model model = new Model();
                model.setName(pythonResponse.getName());
                model.setLink(pythonResponse.getLink());
                model.setAcc( pythonResponse.getAcc());
                model.setDate_trainning(new Date());
                model.setF1(pythonResponse.getF1());
                model.setPre(pythonResponse.getPre());
                model.setRe(pythonResponse.getRe());
                model.setDafault(pythonResponse.isDafault());
                modelMap.addAttribute("model",model);
                session.setAttribute("newModel",model);
                session.setAttribute("listSample",listSample);

                modelMap.addAttribute("oldmodel",oldModel);

                return "show";
            } catch (Exception e) {
                e.printStackTrace();
                return "error";
            } finally {
                // Close the RestTemplate
                restTemplate = null;
            }
        } catch (JsonProcessingException e) {
            e.printStackTrace();
            return "error";
        }
    }
    @PostMapping("retrain/back")
    public String BackRetrain(HttpSession session){
                // Sử dụng RestTemplate để gửi tên linkModel lên server Python
        Model model = (Model) session.getAttribute("newModel");

        //Xóa model ở Server
        RestTemplate restTemplate = new RestTemplate();
        String pythonServerUrl = "http://127.0.0.1:5000"; // Thay đổi thành URL của server Python
        String requestUrl = pythonServerUrl + "/delete_model/"; // Thay đổi thành endpoint của server Python
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        // Gửi chuỗi `linkModel` trong đối tượng JSON
        String json = "{\"linkModel\":\"" + model.getName() + "\"}";
        HttpEntity<String> requestEntity = new HttpEntity<>(json, headers);
        // Thực hiện yêu cầu HTTP POST
        ResponseEntity<String> response = restTemplate.postForEntity(requestUrl, requestEntity, String.class);

        return "redirect:/model/get-all";
    }
    @PostMapping("model/create-new")
    public String CreateModel(HttpSession session){
        // xóa model ở csdl
        Integer Id = (Integer) session.getAttribute("IdModel");
        Model oldmodel = (Model) modelService.finById1(Id);

        //Xóa dữ liệu ở bảng RetrainSampled
        retrainedSampleService.delete1(Id);
        modelService.delete(Id);

        //Xóa model ở Server
        RestTemplate restTemplate = new RestTemplate();
        String pythonServerUrl = "http://127.0.0.1:5000"; // Thay đổi thành URL của server Python
        String requestUrl = pythonServerUrl + "/delete_model/"; // Thay đổi thành endpoint của server Python
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        // Gửi chuỗi `linkModel` trong đối tượng JSON
        String json = "{\"linkModel\":\"" + oldmodel.getName() + "\"}";
        HttpEntity<String> requestEntity = new HttpEntity<>(json, headers);
        // Thực hiện yêu cầu HTTP POST
        ResponseEntity<String> response = restTemplate.postForEntity(requestUrl, requestEntity, String.class);

        //thêm mới model
        Model model = (Model) session.getAttribute("newModel");
        modelService.create1(model);

        // thêm mới trainedSample
        List<Sample> listSample = (List<Sample>)  session.getAttribute("listSample");
        int a = model.getId();
        for (Sample s : listSample){
            TrainedSample t = new TrainedSample();
            t.setModel(model);
            t.setSample(s);
            retrainedSampleService.create(t);
        }

        return "redirect:/model/get-all";
    }


    @GetMapping("/retrain/delete/{id}")
    public  String dodelete(@PathVariable Integer id , HttpSession session){
        Integer IdModel = (Integer) session.getAttribute("IdModel");
        retrainedSampleService.delete(id ,IdModel );
        return "redirect:/retrain/"+IdModel;
    }

//    @PostMapping("/saveSelectedSample")
//    public String saveSelectedSample(@RequestParam("selectedSamples") List<Integer> selectedSamples ){
//        for( Integer id :selectedSamples){
//            newSample newSample =new newSample();
//            newSample.setId(sampleService.finnById(id).getId());
//            newSample.setUsed_id(sampleService.finnById(id).getUsed_id());
//            newSample.setTags(sampleService.finnById(id).getTags());
//            newSampleService.save(newSample);
//        }
//        return "redirect:/login";
//    }


}




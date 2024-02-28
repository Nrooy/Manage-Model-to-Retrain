package com.example.retrain.Controller;

import com.example.retrain.entities.Model;
import com.example.retrain.Seviece.ModelService;
import com.example.retrain.entities.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;

import java.util.List;

//@RestController
@Controller
@RequestMapping("model")
public class ModelController {

    @Autowired
    private ModelService modelService;

//    @GetMapping("/get-all")
//    public String doRetrieveAll(ModelMap modelMap,@RequestParam(name = "pageNo", defaultValue = "1") Integer pageNo){
//        Pageable pageable = PageRequest.of(pageNo - 1 , 5);
//        Page<Model> list =  modelService.findAll1(pageable);
//        modelMap.addAttribute("totalPage", list.getTotalPages());
//        modelMap.addAttribute("currentPage", pageNo);
//        modelMap.addAttribute("listM", list.getContent());
//        return "admin/list-model";
//    }
    @GetMapping("/get-all")
    public String doRetrieveAll(ModelMap modelMap){
        List<Model> listModel = modelService.fillAll();
        modelMap.addAttribute("listModel",listModel);
        return "admin/list-model";
    }
    @GetMapping("/detail/{id}")
    public String viewDetail(ModelMap modelMap , @PathVariable Integer id){
        Model model = modelService.findById(id).get();
        modelMap.addAttribute("model",model);
        return "detail-model";
    }
//    @GetMapping("/{id}")
//    public String doRetrieve(ModelMap modelMap,@PathVariable Integer id){
//        Model model = modelService.findById(id).get();
//        modelMap.addAttribute("model", model);
//        return "admin/edit-model";
//    }
//    @PostMapping("/update")
//    public String doUpdate( @ModelAttribute("model") Model model){
//        Model m = modelService.findById(model.getId()).get();
//        m.setDafault(model.isDafault());
//        modelService.update(m.getId(), m);
//        return "redirect:/model/get-all";
//    }

//    @GetMapping("/delete/{id}")
//    public String doDelete(ModelMap modelMap, @PathVariable Integer id, @ModelAttribute("model") Model model) {
//        Model m = modelService.findById(model.getId()).get();
//        String linkModel = m.getName();
//        // Xóa mô hình từ cơ sở dữ liệu
//        modelService.delete(id);
//        // Sử dụng RestTemplate để gửi tên linkModel lên server Python
//        RestTemplate restTemplate = new RestTemplate();
//        String pythonServerUrl = "http://127.0.0.1:5000"; // Thay đổi thành URL của server Python
//        String requestUrl = pythonServerUrl + "/delete_model/"; // Thay đổi thành endpoint của server Python
//        HttpHeaders headers = new HttpHeaders();
//        headers.setContentType(MediaType.APPLICATION_JSON);
//        // Gửi chuỗi `linkModel` trong đối tượng JSON
//        String json = "{\"linkModel\":\"" + linkModel + "\"}";
//        HttpEntity<String> requestEntity = new HttpEntity<>(json, headers);
//        // Thực hiện yêu cầu HTTP POST
//        ResponseEntity<String> response = restTemplate.postForEntity(requestUrl, requestEntity, String.class);
//        return "redirect:/model/get-all";
//    }
//    @GetMapping("/search")
//    public String doSearch(ModelMap modelMap,
//                           @RequestParam(name = "keyword", defaultValue = "") String keyword,
//                           @RequestParam(name = "pageNo", defaultValue = "1") Integer pageNo)
//    {
//        Pageable pageable = PageRequest.of(pageNo - 1, 5);
//        Page<User> list = modelService.doSearch(keyword,pageable);
//        modelMap.addAttribute("totalPage", list.getTotalPages());
//        modelMap.addAttribute("currentPage", pageNo);
//        modelMap.addAttribute("listU", list.getContent());
//        return "admin/list-user";
//    }
}

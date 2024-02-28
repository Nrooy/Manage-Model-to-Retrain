package com.example.retrain.Controller;


import com.example.retrain.entities.User;
import com.example.retrain.Seviece.UserService;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.*;


@Controller
public class LoginController {

    @Autowired
    private UserService userService;

    @RequestMapping("/login-page")
    public String getLogin(){
        return "login1";
    }

    @PostMapping("/login")
    public String login(ModelMap modelMap, @RequestParam("email") String email, @RequestParam("password") String password, HttpSession session) {
        User userExist = userService.findByEmail(email);
        if (userExist == null) {
            modelMap.addAttribute("ERROR", "Không tồn tại người dùng");
            return "login1";
        } else {
            if (userExist.getRole() == 0 ) {
                modelMap.addAttribute("ERROR", "Yêu cầu quyền quản trị");
                return "login1";
                //return "redirect:/favor/get-all";
            }
            if(userExist.getPassword().equals(password)){
                session.setAttribute("user", userExist.getUserName());
                return "redirect:/model/get-all";
            }else{
                modelMap.addAttribute("ERROR", "Sai mật khẩu");
                return "login1";
            }

        }
    }

    @RequestMapping("/register-page")
    public String getRegister(){
        return "register";
    }

    @PostMapping("/register")
    public String register(ModelMap modelMap,@ModelAttribute("user") User user){
        User userExist = userService.findByEmail(user.getEmail());
        if(userExist == null){
            User newUser = new User();
            newUser.setUserName(user.getUserName());
            newUser.setPassword(user.getPassword());
            newUser.setEmail(user.getEmail());
            newUser.setRole(1);
            userService.create(newUser);
            return "login1";
        }else{
            modelMap.addAttribute("message", "Người dùng đã tồn tại");
            return "register";
        }
    }

    @GetMapping("/logout")
    public String logout(HttpSession session){
        session.removeAttribute("user");
        return "login1";
    }
}

package kz.jm.nazira.springbootcrudapp.controller;

import kz.jm.nazira.springbootcrudapp.dao.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import java.security.Principal;

@Controller
public class AuthController {

    private UserRepository userRepository;

    public AuthController(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @GetMapping("/user")
    public String getUserPage(Model model, Principal principal){
        model.addAttribute("user", userRepository.findByUsername(principal.getName()));
        return "users/show";
    }

    @GetMapping("/admin")
    public String getAdminPage(){
        return "others/admin";
    }

}

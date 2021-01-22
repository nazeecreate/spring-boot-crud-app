package kz.jm.nazira.springbootcrudapp.controller;

import kz.jm.nazira.springbootcrudapp.dao.UserRepository;
import kz.jm.nazira.springbootcrudapp.model.User;
import kz.jm.nazira.springbootcrudapp.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;

import java.security.Principal;

@Controller
public class AuthController {

    private UserService userService;

    public AuthController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping("/")
    public String getLoginPage(){
        return "others/login";
    }

    @GetMapping("/user")
    public String getUserPage(Model model, Principal principal){
        model.addAttribute("currentUser", userService.findByUsername(principal.getName()));
        return "users/user";
    }

    @GetMapping("/admin")
    public String index(Model model, Principal principal, @ModelAttribute("newUser") User newUser){
        model.addAttribute("users", userService.findAll());
        model.addAttribute("currentUser", userService.findByUsername(principal.getName()));
        return "users/admin";
    }

}

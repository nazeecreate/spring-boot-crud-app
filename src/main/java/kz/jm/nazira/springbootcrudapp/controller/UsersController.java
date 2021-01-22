package kz.jm.nazira.springbootcrudapp.controller;

import kz.jm.nazira.springbootcrudapp.model.User;
import kz.jm.nazira.springbootcrudapp.service.UserService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;
import java.util.List;

@Controller
@RequestMapping("admin/users")
public class UsersController {

    private UserService userService;
    public UsersController(UserService userService) {
        this.userService = userService;
    }

    @PostMapping()
    public String create(@ModelAttribute("user") User user, @RequestParam(value = "roleSelect", required = false) List<String> roleSelect) {
        userService.save(user, roleSelect);
        return "redirect:/admin";
    }

    @PatchMapping("/{id}")
    public String update(@PathVariable("id") Long id, @ModelAttribute("user") User user){
        System.out.println(user.getFirstName() + user.getLastName() + user.getPassword());

       // userService.update(id,user);
        return "redirect:/admin";
    }

    @DeleteMapping("/{id}")
    public String delete(@PathVariable("id") Long id){
        userService.deleteById(id);
        return "redirect:/admin";
    }
}

package kz.jm.nazira.springbootcrudapp.controller;

import kz.jm.nazira.springbootcrudapp.model.User;
import kz.jm.nazira.springbootcrudapp.service.UserService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("admin/users")
public class UsersController {

    private UserService userService;

    public UsersController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping()
    public String index(Model model){
        model.addAttribute("users", userService.findAll());
        return "users/index";
    }

    @GetMapping("/new")
    public String index(@ModelAttribute("user") User user){
        return "users/new";
    }

    @PostMapping()
    public String create(@ModelAttribute("user") User user, @RequestParam(value = "makeAdmin", required = false) String makeAdmin) {
        userService.save(user, makeAdmin);
        return "redirect:/admin/users";
    }

    @GetMapping("/{id}/edit")
    public String edit(@PathVariable("id") Long id, Model model){
        model.addAttribute("user", userService.findById(id));
        return "users/edit";
    }

    @GetMapping("/{id}")
    public String show(@PathVariable("id") Long id, Model model){
        model.addAttribute("user", userService.findById(id));
        return "users/show";
    }

    @PatchMapping("/{id}")
    public String update(@PathVariable("id") Long id, @ModelAttribute("user") User user){
        userService.update(id,user);
        return "redirect:/admin/users";
    }

    @DeleteMapping("/{id}")
    public String delete(@PathVariable("id") Long id){
        userService.deleteById(id);
        return "redirect:/admin/users";
    }
}

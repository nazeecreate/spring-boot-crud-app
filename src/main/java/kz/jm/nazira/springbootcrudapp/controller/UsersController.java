package kz.jm.nazira.springbootcrudapp.controller;

import kz.jm.nazira.springbootcrudapp.model.User;
import kz.jm.nazira.springbootcrudapp.service.UserService;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpSession;
import java.security.Principal;
import java.util.List;

@Controller
@RequestMapping("admin/users")
public class UsersController {

    private UserService userService;

    public UsersController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping()
    public String index(Model model, Principal principal, @ModelAttribute("newUser") User newUser,
                        @ModelAttribute("editUser") User editUser){
        model.addAttribute("users", userService.findAll());
        model.addAttribute("currentUser", userService.findByUsername(principal.getName()));
        return "users/index";
    }

    @GetMapping("/new")
    public String index(@ModelAttribute("user") User user){
        return "users/new";
    }

    @PostMapping()
    public String create(@ModelAttribute("user") User user, @RequestParam(value = "roleSelect", required = false) List<String> roleSelect) {
        System.out.println(roleSelect);

        userService.save(user, roleSelect);
        return "redirect:/admin/users";
    }

    @GetMapping("/{id}/edit")
    public void edit(@PathVariable("id") Long id, Model model){
        model.addAttribute("editUser", userService.findById(id));
       // return "users/edit";
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

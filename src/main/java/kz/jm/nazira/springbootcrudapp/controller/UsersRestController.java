package kz.jm.nazira.springbootcrudapp.controller;

import kz.jm.nazira.springbootcrudapp.model.User;
import kz.jm.nazira.springbootcrudapp.service.UserService;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import java.security.Principal;
import java.util.List;
import javax.validation.Valid;

@RestController
@RequestMapping("/api")
public class UsersRestController {
    private UserService userService;

    public UsersRestController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping(value = "/currentUser", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<User> getCurrentUser(Model model, Principal principal){
        User user = (User) userService.findByUsername(principal.getName());
        return new ResponseEntity<>(user, HttpStatus.OK);
    }

    @GetMapping(value = "", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<List<User>> getAllUsers(){
        List<User> users = userService.findAll();
        return new ResponseEntity<>(users, HttpStatus.OK);
    }

    @GetMapping(value = "/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<User> getUserById(@PathVariable("id") Long id){
        User user = userService.findById(id);
        return new ResponseEntity<>(user, HttpStatus.OK);
    }

    @PutMapping(value = "", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<User> updateUser(@RequestBody @Valid User user, Long id){
        HttpHeaders headers = new HttpHeaders();
        userService.save(user);
        return new ResponseEntity<>(user, headers, HttpStatus.OK);
    }

    @DeleteMapping(value = "/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<User> deleteById(@PathVariable("id") Long id){
        userService.deleteById(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    @PostMapping(value = "", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<User> saveUser(@RequestBody @Valid User user, Long id){
        HttpHeaders headers = new HttpHeaders();
        System.out.println("saving");
        userService.save(user);
        return new ResponseEntity<>(user, headers, HttpStatus.CREATED);
    }

}

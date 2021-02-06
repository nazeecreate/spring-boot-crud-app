package kz.jm.nazira.springbootcrudapp.service;

import kz.jm.nazira.springbootcrudapp.model.User;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.List;

public interface UserService {
    public List<User> findAll();

    public User findById(Long id);

    public void save(User user);

    public void deleteById(Long id);

    public UserDetails findByUsername(String username);

}

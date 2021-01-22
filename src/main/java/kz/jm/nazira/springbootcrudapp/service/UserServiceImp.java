package kz.jm.nazira.springbootcrudapp.service;

import kz.jm.nazira.springbootcrudapp.dao.UserRepository;
import kz.jm.nazira.springbootcrudapp.model.Role;
import kz.jm.nazira.springbootcrudapp.model.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Service
public class UserServiceImp implements UserService {
    UserRepository userRepository;
    BCryptPasswordEncoder bCryptPasswordEncoder;

    public UserServiceImp(UserRepository userRepository, BCryptPasswordEncoder bCryptPasswordEncoder) {
        this.userRepository = userRepository;
        this.bCryptPasswordEncoder = bCryptPasswordEncoder;
    }

    @Override
    public List<User> findAll() {
        return userRepository.findAll();
    }

    @Override
    public User findById(Long id) {
        return userRepository.findById(id).get();
    }

    @Override
    public void update(Long id, User updatedUser, List<String> roleSelect) {
        User user = findById(id);
        Set<Role> roles = new HashSet<>();

        if (roleSelect != null) {
            for (String role : roleSelect) {
                if (role.equals("USER")) {
                    roles.add(new Role(1L, role));
                } else if (role.equals("ADMIN")) {
                    roles.add(new Role(2L, role));
                }
            }
        }

        user.setRoles(roles);
        user.setFirstName(updatedUser.getFirstName());
        user.setLastName(updatedUser.getLastName());
        user.setAge(updatedUser.getAge());
        user.setUsername(updatedUser.getUsername());
        user.setPassword(bCryptPasswordEncoder.encode(updatedUser.getPassword()));
        userRepository.save(user);
    }

    @Override
    public void save(User user, List<String> roleSelect) {
        Set<Role> roles = new HashSet<>();

        if (roleSelect != null) {
            for (String role : roleSelect) {
                if (role.equals("USER")) {
                    roles.add(new Role(1L, role));
                } else if (role.equals("ADMIN")) {
                    roles.add(new Role(2L, role));
                }
            }
        }

        user.setRoles(roles);
        user.setPassword(bCryptPasswordEncoder.encode(user.getPassword()));
        userRepository.save(user);
    }

    @Override
    public void deleteById(Long id) {
        userRepository.deleteById(id);
    }

    @Override
    public UserDetails findByUsername(String username) {
        return userRepository.findByUsername(username);
    }
}

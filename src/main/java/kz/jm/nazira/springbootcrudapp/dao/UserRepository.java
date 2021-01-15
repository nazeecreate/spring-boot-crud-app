package kz.jm.nazira.springbootcrudapp.dao;

import kz.jm.nazira.springbootcrudapp.model.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User,Long> {
    public User findByUsername(String username);
}

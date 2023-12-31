package com.fciencias.freshbowl.services.users;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.fciencias.freshbowl.models.User;
import com.fciencias.freshbowl.models.UserRole;

public interface UserRepository extends JpaRepository<User,Integer>{
    
    public User findByUsername(String username);

    public List<User> findByFirstName(String firstName);

    public List<User> findByRole(UserRole role);
}

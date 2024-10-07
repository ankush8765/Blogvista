package com.ankush.Blogvista.repositories;

import com.ankush.Blogvista.entities.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepo extends JpaRepository<User,Integer> {
    User findByName(String username);
}

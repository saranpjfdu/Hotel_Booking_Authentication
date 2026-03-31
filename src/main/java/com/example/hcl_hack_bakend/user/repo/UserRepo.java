package com.example.hcl_hack_bakend.user.repo;

import org.springframework.data.jpa.repository.JpaRepository;
import com.example.hcl_hack_bakend.user.entity.User;
import org.springframework.stereotype.Repository;

import java.util.Optional;
@Repository
    public interface UserRepo extends JpaRepository<User,Long> {
        Optional<User> findByEmail(String email);
        Optional<User> findByUsername(String name);
        boolean existsByEmail(String email);

        boolean existsByUsername(String username);

    }



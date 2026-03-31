package com.example.hcl_hack_bakend.auth;

import com.example.hcl_hack_bakend.user.repo.UserRepo;
import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import com.example.hcl_hack_bakend.user.entity.User;
import java.util.Optional;

@Service
public class MyUserDetailService implements UserDetailsService {
    @Autowired
    UserRepo userRepo;


    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        Optional<User> user=userRepo.findByEmail(email);
        if(user.isEmpty()){
            throw new  UsernameNotFoundException("user-email :"+email+"not found->");
        }
        return new UserPrincipal(user.get());
    }
}

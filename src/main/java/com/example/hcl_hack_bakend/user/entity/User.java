package com.example.hcl_hack_bakend.user.entity;

import com.example.hcl_hack_bakend.user.enums.Role;
import jakarta.persistence.Entity;




import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;
@Builder
@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "users")
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String username;
    @Column(unique = true)
    private String email;

    @Column(unique = true,nullable = false)
    private String phoneNumber;
    // authentication
    private String password;
    // authorization
    @Enumerated(value = EnumType.STRING)
    private Role role;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
    // account status
    private boolean enabled;
    private boolean accountNonLocked;


}

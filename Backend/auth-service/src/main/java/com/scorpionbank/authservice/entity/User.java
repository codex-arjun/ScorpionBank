package com.scorpionbank.authservice.entity;

import com.scorpionbank.authservice.enums.Role;
import com.scorpionbank.authservice.enums.UserStatus;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;
import java.util.UUID;

@Entity
@Table(
        name = "users",
        uniqueConstraints = {
                @UniqueConstraint(
                        name = "uk_users_email",
                        columnNames = "email"
                )
        }
)
@NoArgsConstructor
@AllArgsConstructor
@Setter
@Getter
@Builder
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;
    @Column(nullable = false,unique = true,length = 255)
    private String email;
    @Column(nullable = false)
    private String password;
    @Enumerated(EnumType.STRING)
    @Column(nullable = false,length = 20)
    private Role role;
    @Enumerated(EnumType.STRING)
    @Column(nullable = false,length = 20)
    private UserStatus userStatus;
    @Column(name="customer_id",unique = true)
    private UUID customerId;
    @Column(nullable = false,updatable = false)
    private LocalDateTime createdAt;

    @PrePersist
    public void onCreate(){
        createdAt=LocalDateTime.now();
    }

}

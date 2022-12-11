package jmaster.io.project2_2.entity;

import jakarta.persistence.*;
import lombok.Data;

@Data
@Entity
public class UserRole {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    @ManyToOne // many UserRole - one User
    //@JoinColumn(name = "user_id") - tu gen
    private User user;
    private String role; // ADMIN, MEMBER
}

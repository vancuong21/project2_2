package jmaster.io.project2_2.dto;

import lombok.Data;

@Data
public class UserRoleDTO {
    private Integer id;
    private Integer userId;
    private String role; // ADMIN, MEMBER
}

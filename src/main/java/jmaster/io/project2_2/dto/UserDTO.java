package jmaster.io.project2_2.dto;

import jakarta.persistence.Transient;
import jakarta.validation.constraints.NotBlank;
import lombok.Data;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.web.multipart.MultipartFile;

import java.util.Date;
import java.util.List;

@Data
public class UserDTO {
    private Integer id;
    @NotBlank // kiểm tra dữ liệu nhập vào
    private String name;
    private String avatar;
    private String username;
    private String password;
    @DateTimeFormat(pattern = "dd/MM/yyyy")
    private Date birthdate;
    @Transient
    private MultipartFile file;
    private Date createdAt;
    // them UserRoleDTO
    private List<UserRoleDTO> userRoles;
}

package jmaster.io.project2_2.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;
import org.springframework.format.annotation.DateTimeFormat;

import java.util.Date;
import java.util.List;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "user")
@EntityListeners(AuditingEntityListener.class) // thêm vào để tự gen created_at
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    private String name;
    private String avatar; // lưu URL
    @Column(unique = true)
    private String username;
    private String password;
    @DateTimeFormat(pattern = "dd/MM/yyyy")
    private Date birthdate;
    //    @Transient // vì trong db không lưu file, nên dùng @Transient để bỏ qua thuộc tính này
//    private MultipartFile file;
    @CreatedDate // tự gen thời gian tạo, phải add @EnableJpaAuditing để nó tự gen, tự set
    @Column(updatable = false)
    private Date createdAt;
    @LastModifiedDate
    private Date lastUpdateAt;

    // ko bat buoc
    @OneToMany(mappedBy = "user", fetch = FetchType.LAZY) //
    private List<UserRole> userRoles;
}

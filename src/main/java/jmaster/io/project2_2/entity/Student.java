package jmaster.io.project2_2.entity;

import jakarta.persistence.*;
import lombok.Data;

@Data
@Entity
public class Student {
    @Id
    private Integer id;
    @Column(unique = true)
    private String studentCode;
    @OneToOne
    @PrimaryKeyJoinColumn
    private User user;

    // cách 2: không tạo bảng Score riêng, và Score chứa 2 thuộc tính
//    @ManyToMany(fetch = FetchType.LAZY)
//    @JoinTable(name = "score",
//            joinColumns = @JoinColumn(name = "student_id"),
//            inverseJoinColumns = @JoinColumn(name = "course_id"))
//    private List<Course> courses;
}

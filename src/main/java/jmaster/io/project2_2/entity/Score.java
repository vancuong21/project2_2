package jmaster.io.project2_2.entity;

import jakarta.persistence.*;
import lombok.Data;

@Data
@Entity
public class Score {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    private double score; // diem thi monhoc
    @ManyToOne
    private Student student;
    @ManyToOne
    private Course course;
}

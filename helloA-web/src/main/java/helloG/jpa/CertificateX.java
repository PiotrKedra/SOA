package helloG.jpa;

import javax.persistence.*;
import java.io.Serializable;


@Entity
@Table(name = "CertificateX", schema = "public")
public class CertificateX implements Serializable {


    @ManyToOne
    @JoinColumn(name="teacher_id", nullable = false)
    private TeacherX teacher;

    @Id
    @Column(name = "name")
    private String name;

    public CertificateX() {
    }

    public TeacherX getTeacher() {
        return teacher;
    }

    public void setTeacher(TeacherX teacher) {
        this.teacher = teacher;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}

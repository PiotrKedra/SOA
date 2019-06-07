package helloG.jpa;

import javax.persistence.*;
import java.util.Set;


@Entity
@Table(name = "TeacherX", schema = "public")
public class TeacherX {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "teacher_id")
    private int id;

    @Column(name = "name")
    private String name;

    @Column(name = "surname")
    private String surname;
    @Column(name = "email")
    private String email;

    @OneToMany(mappedBy = "teacher")
    private Set<CertificateX> certificateXES;


    public TeacherX() {
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getSurname() {
        return surname;
    }

    public void setSurname(String surname) {
        this.surname = surname;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public Set<CertificateX> getCertificateXES() {
        return certificateXES;
    }

    public void setCertificateXES(Set<CertificateX> certificateXES) {
        this.certificateXES = certificateXES;
    }
}

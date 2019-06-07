package helloG.jpa;

import com.fasterxml.jackson.annotation.JsonIgnore;

import javax.persistence.*;
import java.io.Serializable;
import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

@Entity
@Table(name = "ProjectX")
public class ProjectX implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "project_id")
    private int id;

    @JsonIgnore
    @ManyToMany(fetch = FetchType.EAGER, mappedBy = "projects")
    private Set<StudentX> students = new HashSet<>();

    @Column(name = "tilte")
    private String title;

    public ProjectX() {
    }

    public ProjectX(String title) {
        this.title = title;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public Set<StudentX> getStudents() {
        return students;
    }

    public void setStudents(Set<StudentX> students) {
        this.students = students;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    @Override
    public String toString() {
        return "ProjectX{" +
                "id=" + id +
                ", title='" + title + '\'' +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        ProjectX projectX = (ProjectX) o;
        return id == projectX.id &&
                Objects.equals(title, projectX.title);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, title);
    }
}

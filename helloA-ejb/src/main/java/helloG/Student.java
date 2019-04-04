package helloG;

import javax.xml.bind.annotation.*;
import java.awt.*;
import java.util.List;


@XmlRootElement(name = "agh.edu.pl.soa.Student")
@XmlAccessorType(XmlAccessType.FIELD)
public class Student {

    @XmlElement(name = "id", required = true)
    private String id;
    @XmlElement(name = "first_name")
    private String first_name;
    @XmlElement(name = "last_name")
    private String last_name;
    @XmlElement(name = "email")
    private String email;
    @XmlElementWrapper(name="courses")
    @XmlElement(name = "course")
    private List<String> courses;
    @XmlElement(name="avatar")
    private Image avatar;

    // to return as XML need to have this constructor
    public Student() {
    }

    public Student(String id, String first_name, String last_name, String email, List<String> courses, Image avatar) {
        this.id = id;
        this.first_name = first_name;
        this.last_name = last_name;
        this.email = email;
        this.courses = courses;
        this.avatar = avatar;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getFirst_name() {
        return first_name;
    }

    public void setFirst_name(String first_name) {
        this.first_name = first_name;
    }

    public String getLast_name() {
        return last_name;
    }

    public void setLast_name(String last_name) {
        this.last_name = last_name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public List<String> getCourses() {
        return courses;
    }

    public void setCourses(List<String> courses) {
        this.courses = courses;
    }

    public Image getAvatar() {
        return avatar;
    }

    public void setAvatar(Image avatar) {
        this.avatar = avatar;
    }
}

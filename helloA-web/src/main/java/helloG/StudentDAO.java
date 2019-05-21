package helloG;

import helloG.resources.StudentNotFoundException;

import javax.inject.Singleton;
import java.io.File;
import java.util.*;

import java.util.List;

@Singleton
public class StudentDAO {

    private final String AVATAR_PATH = "D:\\code\\helloA\\soap-api\\src\\main\\resources\\avatar.jpg";

    private List<Student> students;

    {
        students = new ArrayList<>();
        students.add(new Student("1","Piotr", "Kedr", "pkedr@gmail.com", Arrays.asList("SOA", "PIS", "SW"), AVATAR_PATH));
        students.add(new Student("2","Miłosz", "Kedr", "pkedr@gmail.com", Arrays.asList("SOA", "PIS", "SW"), AVATAR_PATH));
        students.add(new Student("3","Krzychu", "Kedr", "pkedr@gmail.com", Arrays.asList("SOA", "PIS", "SW"), AVATAR_PATH));
        students.add(new Student("4","Pablito", "Kedr", "pkedr@gmail.com", Arrays.asList("SOA", "PIS", "SW"), AVATAR_PATH));
        students.add(new Student("5","Ziomek", "Kedr", "pkedr@gmail.com", Arrays.asList("SOA", "PIS", "SW"), AVATAR_PATH));
        students.add(new Student("6","Reszta", "Kedr", "pkedr@gmail.com", Arrays.asList("SOA", "PIS", "SW"), AVATAR_PATH));
    }

    public void add(Student student){
        students.add(student);
    }

    public Student get(String id) throws StudentNotFoundException {
        Student student = students.stream()
                .filter(s -> s.getId().equals(id))
                .findFirst()
                .orElse(null);
        if (student==null) throw new StudentNotFoundException("Wrong id: " + id);
        return student;
    }

    public void delete(String id) throws StudentNotFoundException {
        students.remove(get(id));
    }

    public List<Student> getAllStudents(){
        return students;
    }

    public Student edit(String id, String name, String lastName, String email, List<String> courses) throws StudentNotFoundException {
        Student student = get(id);
        if(name!=null)
            student.setFirst_name(name);
        if(lastName!=null)
            student.setLast_name(lastName);
        if(email!=null)
            student.setEmail(email);
        if(courses!=null && courses.size()==0)
            student.setCourses(courses);



        return student;
    }

    public List<Student> filter(String id, String firstName, String lastName, String email, List<String> courses){
        List<Student> students = null;

        if(id!=null) {
            if (id.equals("?") || id.equals("")) id = null;
            else {
                try {
                    return Collections.singletonList(get(id));
                } catch (StudentNotFoundException e) {
                    e.printStackTrace();
                }
            }
        }
        if(firstName!=null) {
            if (firstName.equals("?") || firstName.equals("")) firstName = null;
        }
        if(lastName!=null) {
            if (lastName.equals("?") || lastName.equals("")) lastName = null;
        }
        if(email!=null) {
            if (email.equals("?") || email.equals("")) email = null;
        }
        if(courses!=null) {
            if (courses.size() == 1 && courses.get(0).equals("?") || courses.get(0).equals("")) courses = null;
        }

        List<Student> matchedStudent = new ArrayList<>();
        for(Student student: students){
            boolean student_match = false;
            if(firstName!=null){
                student_match = student.getFirst_name().equals(firstName);
            }
            if(lastName!=null){
                student_match = student.getLast_name().equals(lastName);
            }
            if(email!=null){
                student_match = student.getEmail().equals(email);
            }
            if(courses!=null){
                for (String course: courses){
                    if (student.getCourses().contains(course)) student_match = true;
                    else {
                        student_match = false;
                        break;
                    }
                }
            }

            if(student_match) matchedStudent.add(student);
        }

        return matchedStudent;
    }

    public String getAvatar(String id) throws StudentNotFoundException {
        return get(id).getAvatar();
    }
}

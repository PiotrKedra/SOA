package helloG;

import org.xml.sax.SAXException;

import javax.annotation.security.PermitAll;
import javax.ejb.Lock;
import javax.ejb.LockType;
import javax.ejb.Stateless;
import javax.jws.WebMethod;
import javax.jws.WebParam;
import javax.jws.WebService;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.ws.soap.MTOM;
import java.awt.*;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

@Stateless
@WebService
@MTOM(enabled = true, threshold = 2048)
public class StudentService {

    @WebMethod
    @PermitAll
    @Lock(LockType.READ)
    public Image getAvatar(@WebParam(name = "id") String id){
        StudentXML DB = new StudentXML();

        List<StudentSOAP> students = null;
        try {
            students = DB.getAllStudents();
        } catch (IOException | SAXException | ParserConfigurationException e) {
            e.printStackTrace();
        }

        assert students != null;
        for (StudentSOAP student: students){
            if (student.getId().equals(id)){
                return student.getAvatar();
            }
        }
        return null;
    }

    @WebMethod
    @PermitAll
    @Lock(LockType.READ)
    public StudentSOAP getStudentById(@WebParam(name = "id") String id){

        StudentXML DB = new StudentXML();

        List<StudentSOAP> students = null;
        try {
            students = DB.getAllStudents();
        } catch (IOException | SAXException | ParserConfigurationException e) {
            e.printStackTrace();
        }

        assert students != null;
        for (StudentSOAP student: students){
            if (student.getId().equals(id)){
                return student;
            }
        }
        return new StudentSOAP(id, "There is no such a student ", "", "", null, null);
    }

    @WebMethod
    @PermitAll
    @Lock(LockType.READ)
    public StudentSOAP[] getAllStudents(){
        StudentXML DB = new StudentXML();

        List<StudentSOAP> students = null;
        try {
            students = DB.getAllStudents();
        } catch (IOException | SAXException | ParserConfigurationException e) {
            e.printStackTrace();
        }

        StudentSOAP[] stu = {};
        assert students != null;

        return students.toArray(stu);
    }

    //@WebResult(name = "students")

    @WebMethod
    @PermitAll
    @Lock(LockType.READ)
    public List<StudentSOAP> filtr_students(
            @WebParam(name = "id") String id,
            @WebParam(name = "first_name") String first_name,
            @WebParam(name = "last_name") String last_name,
            @WebParam(name = "email") String email,
            @WebParam(name = "course") List<String> courses)
    {
        StudentXML DB = new StudentXML();
        List<StudentSOAP> students = null;
        try {
            students = DB.getAllStudents();
        } catch (IOException | SAXException | ParserConfigurationException e) {
            e.printStackTrace();
        }

        if(id.equals("?")) id = null;
        else {
            return Collections.singletonList(getStudentById(id));
        }
        if(first_name.equals("?")) first_name = null;
        if(last_name.equals("?")) last_name = null;
        if(email.equals("?")) email = null;
        if(courses.size()==1 && courses.get(0).equals("?")) courses = null;

        List<StudentSOAP> matched_student = new ArrayList<>();
        assert students != null;
        for(StudentSOAP student: students){
            boolean student_match = false;
            if(first_name!=null){
                student_match = student.getFirst_name().equals(first_name);
            }
            if(last_name!=null){
                student_match = student.getLast_name().equals(last_name);
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

            if(student_match) matched_student.add(student);
        }

        return matched_student;
    }

    @WebMethod
    @PermitAll
    @Lock(LockType.READ)
    public String hello(){
        return "TEST";
    }
}

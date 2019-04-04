package helloG;

import org.jboss.annotation.security.SecurityDomain;
import org.jboss.ws.api.annotation.WebContext;
import org.xml.sax.SAXException;

import javax.annotation.security.DeclareRoles;
import javax.annotation.security.PermitAll;
import javax.annotation.security.RolesAllowed;
import javax.ejb.Lock;
import javax.ejb.LockType;
import javax.ejb.Stateless;
import javax.jws.WebMethod;
import javax.jws.WebParam;
import javax.jws.WebResult;
import javax.jws.WebService;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.TransformerConfigurationException;
import javax.xml.transform.TransformerException;
import javax.xml.transform.stream.StreamResult;
import java.io.IOException;
import java.util.List;

@Stateless
@WebService
@DeclareRoles({"managers"})
@WebContext(authMethod="BASIC",transportGuarantee="NONE")
public class StudentManage {

    @WebMethod
    @RolesAllowed({"managers"})
    @Lock(LockType.WRITE)
    public String addStudent(
            @WebParam(name = "id") String id,
            @WebParam(name = "first_name") String first_name,
            @WebParam(name = "last_name") String last_name,
            @WebParam(name = "email") String email,
            @WebParam(name = "course") List<String> courses)
    {
        StudentXML DB =  new StudentXML();

        Student student = new Student(id, first_name, last_name, email, courses, null);

        try {
            DB.addStudent(student);
        } catch (ParserConfigurationException | TransformerConfigurationException | SAXException | IOException e) {
            e.printStackTrace();
        }

        return "Student added";
    }

    @WebMethod
    @RolesAllowed({"managers"})
    @Lock(LockType.WRITE)
    public String editStudent(
            @WebParam(name = "id") String id,
            @WebParam(name = "first_name") String first_name,
            @WebParam(name = "last_name") String last_name,
            @WebParam(name = "email") String email,
            @WebParam(name = "course") List<String> courses)
    {
        if(first_name.equals("?")) first_name = null;
        if(last_name.equals("?")) last_name = null;
        if(email.equals("?")) email = null;
        if(courses.size()==1 && courses.get(0).equals("?")) courses = null;

        StudentXML DB = new StudentXML();
        try {
            DB.editStudent(id, first_name, last_name, email, courses);
        } catch (ParserConfigurationException | IOException | SAXException | TransformerException e) {
            e.printStackTrace();
        }
        return "Student edited";
    }

}

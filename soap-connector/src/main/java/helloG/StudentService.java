
package helloG;

import java.util.List;
import javax.jws.WebMethod;
import javax.jws.WebParam;
import javax.jws.WebResult;
import javax.jws.WebService;
import javax.xml.bind.annotation.XmlSeeAlso;
import javax.xml.ws.RequestWrapper;
import javax.xml.ws.ResponseWrapper;


/**
 * This class was generated by the JAX-WS RI.
 * JAX-WS RI 2.3.0-SNAPSHOT
 * Generated source version: 2.2
 * 
 */
@WebService(name = "StudentService", targetNamespace = "http://helloG/")
@XmlSeeAlso({
    ObjectFactory.class
})
public interface StudentService {


    /**
     * 
     * @return
     *     returns java.util.List<helloG.Student>
     */
    @WebMethod
    @WebResult(targetNamespace = "")
    @RequestWrapper(localName = "getAllStudents", targetNamespace = "http://helloG/", className = "helloG.GetAllStudents")
    @ResponseWrapper(localName = "getAllStudentsResponse", targetNamespace = "http://helloG/", className = "helloG.GetAllStudentsResponse")
    public List<Student> getAllStudents();

    /**
     * 
     * @param id
     * @return
     *     returns byte[]
     */
    @WebMethod
    @WebResult(targetNamespace = "")
    @RequestWrapper(localName = "getAvatar", targetNamespace = "http://helloG/", className = "helloG.GetAvatar")
    @ResponseWrapper(localName = "getAvatarResponse", targetNamespace = "http://helloG/", className = "helloG.GetAvatarResponse")
    public byte[] getAvatar(
        @WebParam(name = "id", targetNamespace = "")
        String id);

    /**
     * 
     * @param id
     * @return
     *     returns helloG.Student
     */
    @WebMethod
    @WebResult(targetNamespace = "")
    @RequestWrapper(localName = "getStudentById", targetNamespace = "http://helloG/", className = "helloG.GetStudentById")
    @ResponseWrapper(localName = "getStudentByIdResponse", targetNamespace = "http://helloG/", className = "helloG.GetStudentByIdResponse")
    public Student getStudentById(
        @WebParam(name = "id", targetNamespace = "")
        String id);

    /**
     * 
     * @return
     *     returns java.lang.String
     */
    @WebMethod
    @WebResult(targetNamespace = "")
    @RequestWrapper(localName = "hello", targetNamespace = "http://helloG/", className = "helloG.Hello")
    @ResponseWrapper(localName = "helloResponse", targetNamespace = "http://helloG/", className = "helloG.HelloResponse")
    public String hello();

    /**
     * 
     * @param firstName
     * @param lastName
     * @param course
     * @param id
     * @param email
     * @return
     *     returns java.util.List<helloG.Student>
     */
    @WebMethod(operationName = "filtr_students")
    @WebResult(targetNamespace = "")
    @RequestWrapper(localName = "filtr_students", targetNamespace = "http://helloG/", className = "helloG.FiltrStudents")
    @ResponseWrapper(localName = "filtr_studentsResponse", targetNamespace = "http://helloG/", className = "helloG.FiltrStudentsResponse")
    public List<Student> filtrStudents(
        @WebParam(name = "id", targetNamespace = "")
        String id,
        @WebParam(name = "first_name", targetNamespace = "")
        String firstName,
        @WebParam(name = "last_name", targetNamespace = "")
        String lastName,
        @WebParam(name = "email", targetNamespace = "")
        String email,
        @WebParam(name = "course", targetNamespace = "")
        List<String> course);

}

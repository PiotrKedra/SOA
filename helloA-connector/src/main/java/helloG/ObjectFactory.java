
package helloG;

import javax.xml.bind.JAXBElement;
import javax.xml.bind.annotation.XmlElementDecl;
import javax.xml.bind.annotation.XmlRegistry;
import javax.xml.namespace.QName;


/**
 * This object contains factory methods for each 
 * Java content interface and Java element interface 
 * generated in the hellog package. 
 * <p>An ObjectFactory allows you to programatically 
 * construct new instances of the Java representation 
 * for XML content. The Java representation of XML 
 * content can consist of schema derived interfaces 
 * and classes representing the binding of schema 
 * type definitions, element declarations and model 
 * groups.  Factory methods for each of these are 
 * provided in this class.
 * 
 */
@XmlRegistry
public class ObjectFactory {

    private final static QName _AddStudent_QNAME = new QName("http://helloG/", "addStudent");
    private final static QName _AddStudentResponse_QNAME = new QName("http://helloG/", "addStudentResponse");
    private final static QName _EditStudent_QNAME = new QName("http://helloG/", "editStudent");
    private final static QName _EditStudentResponse_QNAME = new QName("http://helloG/", "editStudentResponse");

    /**
     * Create a new ObjectFactory that can be used to create new instances of schema derived classes for package: hellog
     * 
     */
    public ObjectFactory() {
    }

    /**
     * Create an instance of {@link AddStudent }
     * 
     */
    public AddStudent createAddStudent() {
        return new AddStudent();
    }

    /**
     * Create an instance of {@link AddStudentResponse }
     * 
     */
    public AddStudentResponse createAddStudentResponse() {
        return new AddStudentResponse();
    }

    /**
     * Create an instance of {@link EditStudent }
     * 
     */
    public EditStudent createEditStudent() {
        return new EditStudent();
    }

    /**
     * Create an instance of {@link EditStudentResponse }
     * 
     */
    public EditStudentResponse createEditStudentResponse() {
        return new EditStudentResponse();
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link AddStudent }{@code >}
     * 
     * @param value
     *     Java instance representing xml element's value.
     * @return
     *     the new instance of {@link JAXBElement }{@code <}{@link AddStudent }{@code >}
     */
    @XmlElementDecl(namespace = "http://helloG/", name = "addStudent")
    public JAXBElement<AddStudent> createAddStudent(AddStudent value) {
        return new JAXBElement<AddStudent>(_AddStudent_QNAME, AddStudent.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link AddStudentResponse }{@code >}
     * 
     * @param value
     *     Java instance representing xml element's value.
     * @return
     *     the new instance of {@link JAXBElement }{@code <}{@link AddStudentResponse }{@code >}
     */
    @XmlElementDecl(namespace = "http://helloG/", name = "addStudentResponse")
    public JAXBElement<AddStudentResponse> createAddStudentResponse(AddStudentResponse value) {
        return new JAXBElement<AddStudentResponse>(_AddStudentResponse_QNAME, AddStudentResponse.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link EditStudent }{@code >}
     * 
     * @param value
     *     Java instance representing xml element's value.
     * @return
     *     the new instance of {@link JAXBElement }{@code <}{@link EditStudent }{@code >}
     */
    @XmlElementDecl(namespace = "http://helloG/", name = "editStudent")
    public JAXBElement<EditStudent> createEditStudent(EditStudent value) {
        return new JAXBElement<EditStudent>(_EditStudent_QNAME, EditStudent.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link EditStudentResponse }{@code >}
     * 
     * @param value
     *     Java instance representing xml element's value.
     * @return
     *     the new instance of {@link JAXBElement }{@code <}{@link EditStudentResponse }{@code >}
     */
    @XmlElementDecl(namespace = "http://helloG/", name = "editStudentResponse")
    public JAXBElement<EditStudentResponse> createEditStudentResponse(EditStudentResponse value) {
        return new JAXBElement<EditStudentResponse>(_EditStudentResponse_QNAME, EditStudentResponse.class, null, value);
    }

}

package helloG;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

import javax.swing.*;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerConfigurationException;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;
import java.awt.*;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class StudentXML {

    private final String data_xml_path = "D:\\code\\helloA\\soap-api\\src\\main\\resources\\students.xml";

    public List<StudentSOAP> getAllStudents() throws IOException, SAXException, ParserConfigurationException {

        List<StudentSOAP> result = new ArrayList<>();

        NodeList nodeList = getStudentsNodes();

        ImageIcon icon = new ImageIcon("D:\\code\\helloA\\soap-api\\src\\main\\resources\\avatar.jpg");
        Image image = icon.getImage();

        for (int i = 0; i < nodeList.getLength(); ++i) {
            Node node = nodeList.item(i);

            if (node.getNodeType() == Node.ELEMENT_NODE) {

                Element element = (Element) node;

                String id = element.getElementsByTagName("id").item(0).getTextContent();
                String first_name = element.getElementsByTagName("first_name").item(0).getTextContent();
                String last_name = element.getElementsByTagName("last_name").item(0).getTextContent();
                String email = element.getElementsByTagName("email").item(0).getTextContent();
                List<String> courses = new ArrayList<>();
                for(int j =0; j < element.getElementsByTagName("course").getLength(); ++j){
                    courses.add(element.getElementsByTagName("course").item(j).getTextContent());
                }

                result.add(new StudentSOAP(id, first_name, last_name, email, courses, image));
            }

        }
        return result;
    }

    public void addStudent(StudentSOAP student) throws ParserConfigurationException, IOException, SAXException, TransformerConfigurationException {
        DocumentBuilderFactory documentBuilderFactory = DocumentBuilderFactory.newInstance();
        DocumentBuilder documentBuilder = documentBuilderFactory.newDocumentBuilder();
        Document document = documentBuilder.parse(data_xml_path);
        Element root = document.getDocumentElement();

        Element newStudent = document.createElement("student");

        Element id = document.createElement("id");
        id.appendChild(document.createTextNode(student.getId()));
        newStudent.appendChild(id);

        Element first_name = document.createElement("first_name");
        first_name.appendChild(document.createTextNode(student.getFirst_name()));
        newStudent.appendChild(first_name);

        Element last_name = document.createElement("last_name");
        last_name.appendChild(document.createTextNode(student.getLast_name()));
        newStudent.appendChild(last_name);

        Element email = document.createElement("email");
        email.appendChild(document.createTextNode(student.getEmail()));
        newStudent.appendChild(email);

        Element courses = document.createElement("courses");
        for(String course_string: student.getCourses()){
            Element course = document.createElement("course");
            course.appendChild(document.createTextNode(course_string));
            courses.appendChild(course);
        }
        newStudent.appendChild(courses);

        root.appendChild(newStudent);

        DOMSource source = new DOMSource(document);

        TransformerFactory transformerFactory = TransformerFactory.newInstance();
        Transformer transformer = transformerFactory.newTransformer();
        StreamResult result = new StreamResult(data_xml_path);
        try {
            transformer.transform(source, result);
        } catch (TransformerException e) {
            e.printStackTrace();
        }
    }

    public void editStudent(String id, String first_name, String last_name, String email, List<String> courses) throws ParserConfigurationException, IOException, SAXException, TransformerException {
        DocumentBuilderFactory documentBuilderFactory =  DocumentBuilderFactory.newInstance();
        DocumentBuilder documentBuilder = documentBuilderFactory.newDocumentBuilder();
        Document document = documentBuilder.parse(data_xml_path);

        Node root = document.getFirstChild();

        NodeList students = document.getElementsByTagName("student");

        for(int i=0; i<students.getLength(); ++i){
            System.out.println(i);
            Node attr = students.item(i);

            Element element = (Element) attr;

            Node id_node = element.getElementsByTagName("id").item(0);

            if (id_node == null) continue;

            if (id_node.getTextContent().equals(id)){
                if (first_name != null) {
                    System.out.println(first_name);
                    Node first_time_node = element.getElementsByTagName("first_name").item(0);
                    first_time_node.setTextContent(first_name);
                }
                if (last_name != null){
                    System.out.println(last_name);
                    element.getElementsByTagName("last_name").item(0).setTextContent(last_name);
                }
                if (email != null){
                    System.out.println(email);
                    element.getElementsByTagName("email").item(0).setTextContent(email);
                }
                if (courses != null){
                    System.out.println(courses);
                    int amount_of_courses = element.getElementsByTagName("course").getLength();
                    for(int j=0; j<amount_of_courses; ++j) {
                        Node child = element.getElementsByTagName("course").item(0);
                        element.getElementsByTagName("courses").item(0).removeChild(child);
                    }
                    for(String course: courses){
                        Element course_ele = document.createElement("course");
                        course_ele.appendChild(document.createTextNode(course));
                        element.getElementsByTagName("courses").item(0).appendChild(course_ele);
                    }
                }
                break;
            }
        }

        TransformerFactory transformerFactory = TransformerFactory.newInstance();
        Transformer transformer = transformerFactory.newTransformer();
        DOMSource source = new DOMSource(document);
        StreamResult result = new StreamResult(new File(data_xml_path));
        transformer.transform(source, result);

    }

    private NodeList getStudentsNodes() throws ParserConfigurationException, IOException, SAXException {

        File xml_file = new File(data_xml_path);
        DocumentBuilderFactory documentBuilderFactory = DocumentBuilderFactory.newInstance();
        DocumentBuilder documentBuilder = documentBuilderFactory.newDocumentBuilder();
        Document document = documentBuilder.parse(xml_file);

        document.getDocumentElement().normalize();

        return document.getElementsByTagName("student");
    }

}
package consumption;

import helloG.*;

import javax.imageio.ImageIO;
import javax.swing.*;
import javax.xml.ws.BindingProvider;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.lang.reflect.Array;
import java.util.Arrays;
import java.util.List;

public class MainTest {

    public static void main(String[] args) {


        StudentServiceService studentServiceService = new StudentServiceService();


        StudentService studentService = studentServiceService.getStudentServicePort();

        for(Student s:studentService.getAllStudents()){
            System.out.println("-----------");
            System.out.println("id: " + s.getId());
            System.out.println("name: " + s.getFirstName());
            System.out.println("surname: "  + s.getLastName());
            System.out.println("email: "+s.getEmail());
            for(String cor:s.getCourses().getCourse()){
                System.out.println("course: "+ cor);
            }
            System.out.println("-----------");


        }


        byte[] img = studentService.getAvatar("1");

        BufferedImage imgB = null;
        try {
            imgB = ImageIO.read(new ByteArrayInputStream(img));
        } catch (IOException e) {
            e.printStackTrace();
        }

        JFrame frame = new JFrame();
        frame.getContentPane().setLayout(new FlowLayout());
        frame.getContentPane().add(new JLabel(new ImageIcon(imgB)));
        frame.pack();
        frame.setVisible(true);


        StudentManageService studentManageService = new StudentManageService();



        StudentManage studentManage = studentManageService.getStudentManagePort();

        setCredentials((BindingProvider) studentManage,"manager", "manager1");


        studentManage.addStudent("51", "Ziomek", "Mat", "wp@wp.pl", Arrays.asList("KOR", "WOR"));


        System.out.println(studentService.getStudentById("51").getFirstName() + studentService.getStudentById("51").getLastName());
    }

    static private void setCredentials(BindingProvider provider, String username, String password){
        provider.getRequestContext().put(BindingProvider.USERNAME_PROPERTY, username);
        provider.getRequestContext().put(BindingProvider.PASSWORD_PROPERTY, password);
    }
}

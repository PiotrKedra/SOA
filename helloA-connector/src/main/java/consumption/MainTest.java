package consumption;

import helloG.HelloResponse;
import helloG.Student;
import helloG.StudentService;
import helloG.StudentServiceService;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.IOException;

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
    }
}

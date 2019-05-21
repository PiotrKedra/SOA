package helloG;

import javax.ws.rs.core.GenericType;
import javax.ws.rs.core.Response;
import java.util.Arrays;
import java.util.List;

import com.google.protobuf.InvalidProtocolBufferException;
import helloG.messages.Student;
import helloG.messages.StudentProtos;
import org.jboss.resteasy.client.jaxrs.ResteasyClient;
import org.jboss.resteasy.client.jaxrs.ResteasyClientBuilder;
import org.jboss.resteasy.client.jaxrs.ResteasyWebTarget;


public class Main {

    static private final String API_STUDENT_ALL = "http://127.0.0.1:8080/helloA-web/api/student/all";
    static private final String API_STUDENT_PROTO = "http://127.0.0.1:8080/helloA-web/api/student/get_proto";
    static private final String API_STUDENT_AVATAR = "http://127.0.0.1:8080/helloA-web/api/student/getAvatar";
    static private final String API_STUDENT_TOKEN = "http://127.0.0.1:8080/helloA-web/api/auth/login";
    static private final String API_STUDENT_DELETE = "http://127.0.0.1:8080/helloA-web/api/student/delete";

    static private ResteasyClient client = new ResteasyClientBuilder().build();


    public static void main(String[] args) {

        System.out.println("Listed students: ");
        System.out.println(getStudentList());

        System.out.println("Student in proto: ");
        System.out.println(getStudentInProto("1"));

        System.out.println("Binary data: ");
        System.out.println(Arrays.toString(getAvatar("1")));

        System.out.println("Auth token: ");
        String token = authorize("pkedra", "pk");
        System.out.println(token);

        System.out.println("Deleting: id=1");
        deleteStudent("1", token);

        System.out.println("Listed students: ");
        System.out.println(getStudentList());

        client.close();
    }

    private static List<Student> getStudentList() {
        ResteasyWebTarget target = client.target(API_STUDENT_ALL);
        Response response = target.request().get();
        return response.readEntity(new GenericType<List<Student>>() {});
    }

    private static StudentProtos.Student getStudentInProto(String id){
        ResteasyWebTarget target = client.target(API_STUDENT_PROTO).queryParam("id", id);
        Response response = target.request().get();

        try{
            return StudentProtos.Student.parseFrom(response.readEntity(byte[].class));
        } catch (InvalidProtocolBufferException e) {
            e.printStackTrace();
        }
        return null;
    }

    private static byte[] getAvatar(String id){
        ResteasyWebTarget target = client.target(API_STUDENT_AVATAR).queryParam("id", id);
        Response response = target.request().get();
        return response.readEntity(byte[].class);
    }

    private static String authorize(String user, String pass){
        ResteasyWebTarget target = client.target(API_STUDENT_TOKEN)
                .queryParam("username", user)
                .queryParam("password", pass);
        Response response = target.request().get();

        return response.readEntity(String.class);
    }

    private static void deleteStudent(String id, String token){
        ResteasyWebTarget target = client.target(API_STUDENT_DELETE).queryParam("id", id);
        Response response = target.request()
                .header("Authorization", "Bearer " + token)
                .method("post");
        System.out.println("Delete student response status: " + response.getStatus());
        response.close();
    }
}

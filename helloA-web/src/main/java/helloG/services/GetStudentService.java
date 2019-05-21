package helloG.services;


import helloG.Student;
import helloG.StudentDAO;
import helloG.messages.StudentProtos;
import helloG.resources.BinaryResource;
import helloG.resources.StudentNotFoundException;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;


import javax.inject.Inject;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.io.IOException;
import java.util.List;


@Path("/student")
@Consumes(MediaType.APPLICATION_JSON)
@Produces(MediaType.APPLICATION_JSON)
@Api(value = "Get Student Service", description = "Student swagger")
public class GetStudentService {

    @Inject
    StudentDAO studentDAO;

    @Inject
    BinaryResource binaryResource;

    @GET
    @Path("/get_proto")
    @ApiOperation(value = "Get student by id", notes = "Get student with given id in protocol buffer")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Student found"),
            @ApiResponse(code = 404, message = "Student not found")
    })
    public Response get(@QueryParam("id") String id){
        try {
            return Response.ok(studentToProto(studentDAO.get(id)).toByteArray())
                    .build();
        } catch (StudentNotFoundException e) {
            return Response.status(Response.Status.NOT_FOUND)
                    .entity("Student with id: " + id + " not found")
                    .build();
        }
    }

    @GET
    @Path("/get")
    @ApiOperation(value = "Get student by id", notes = "Get student with given id")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Student found"),
            @ApiResponse(code = 404, message = "Student not found")
    })
    public Response getProto(@QueryParam("id") String id){
        try {
            return Response.ok(studentDAO.get(id))
                    .build();
        } catch (StudentNotFoundException e) {
            return Response.status(Response.Status.NOT_FOUND)
                    .entity("Student with id: " + id + " not found")
                    .build();
        }
    }

    @GET
    @Path("/all")
    @ApiOperation(value = "Get all students", notes = "Get all students")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "ok"),
    })
    public Response getAll(){
        return Response.ok(studentDAO.getAllStudents())
                .build();
    }

    @GET
    @Path("/filter")
    @ApiOperation(value = "filter students", notes = "filter by id, name, last_name, email, courses")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "ok"),
    })
    public Response filter(@QueryParam("id") String id,
                           @QueryParam("name") String name,
                           @QueryParam("last_name") String lastName,
                           @QueryParam("email") String email,
                           @QueryParam("courses")List<String> courses){
        return Response.ok(studentDAO.filter(id, name, lastName, email, courses))
                .build();
    }

    @GET
    @Path("/getAvatar")
    @Produces("image/jpeg")
    @ApiOperation(value = "Get student avatar", notes = "Get student avatar by id")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Student image found"),
            @ApiResponse(code = 404, message = "Student image not found"),
            @ApiResponse(code = 500, message = "Error while encoding image")
    })
    public Response getAvatar(@QueryParam("id") String id){
        try {
            return Response.ok(binaryResource.convertToBinary(studentDAO.getAvatar(id)))
                    .build();
        } catch (IOException e) {
            e.printStackTrace();
            return  Response.status(Response.Status.INTERNAL_SERVER_ERROR)
                    .build();
        } catch (StudentNotFoundException e) {
            e.printStackTrace();
            return Response.status(Response.Status.NOT_FOUND)
                    .build();
        }
    }


    private StudentProtos.Student studentToProto(Student student){
        return StudentProtos.Student.newBuilder()
                .setId(student.getId())
                .setFirstName(student.getFirst_name())
                .setLastName(student.getLast_name())
                .setEmail(student.getEmail())
                .setAvatar(student.getAvatar())
                .build();
    }
}

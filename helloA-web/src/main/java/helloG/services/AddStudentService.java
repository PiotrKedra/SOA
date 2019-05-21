package helloG.services;

import helloG.Student;
import helloG.StudentDAO;
import helloG.auth.AuthTokenRequired;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;

import javax.inject.Inject;
import javax.validation.constraints.NotNull;
import javax.ws.rs.Consumes;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.util.List;

@Consumes(MediaType.APPLICATION_JSON)
@Path("/student")
@Api(value = "Add student service", description = "Student swagger")
public class AddStudentService {

    @Inject
    StudentDAO studentDAO;

    @POST
    @Path("/add")
    @AuthTokenRequired
    @ApiOperation(value = "Add student", notes = "Add student id, name, last_name, email, courses")
    @ApiResponses(value = {
            @ApiResponse(code = 204, message = "Student added"),
            @ApiResponse(code = 401, message = "Invalid token"),
    })
    public Response addStudent(@QueryParam("id") @NotNull String id,
                               @QueryParam("name") @NotNull String name,
                               @QueryParam("last_name") @NotNull String lastName,
                               @QueryParam("email") @NotNull String email,
                               @QueryParam("courses") @NotNull List<String> courses){

        studentDAO.add(new Student(id, name, lastName, email, courses, null));
        return Response.ok("Student created")
                .status(Response.Status.CREATED)
                .build();
    }
}

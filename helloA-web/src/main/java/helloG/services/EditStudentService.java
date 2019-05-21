package helloG.services;

import helloG.Student;
import helloG.StudentDAO;
import helloG.auth.AuthTokenRequired;
import helloG.resources.StudentNotFoundException;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import org.hibernate.validator.constraints.Email;

import javax.inject.Inject;
import javax.validation.constraints.NotNull;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.util.List;

@Consumes(MediaType.APPLICATION_JSON)
@Produces(MediaType.APPLICATION_JSON)
@Path("/student")
@Api(value = "Edit student service", description = "Student swagger")
public class EditStudentService {


    @Inject
    StudentDAO studentDAO;

    @PUT
    @Path("/edit")
    @AuthTokenRequired
    @ApiOperation(value = "Edit student", notes = "Edit student by id")
    @ApiResponses(value = {
            @ApiResponse(code = 204, message = "Student edited"),
            @ApiResponse(code = 401, message = "Invalid token"),
            @ApiResponse(code = 400, message = "There is not id"),
            @ApiResponse(code = 404, message = "Student not found")
    })
    public Response editStudent(@QueryParam("id") @NotNull String id,
                                @QueryParam("name") String name,
                                @QueryParam("last_name") String lastName,
                                @QueryParam("email") @Email String email,
                                @QueryParam("courses") List<String> courses){
        try {
            Student s = studentDAO.edit(id, name, lastName, email, courses);
            return Response.ok(s)
                    .build();
        } catch (StudentNotFoundException e) {
            e.printStackTrace();
            return Response.status(Response.Status.NOT_FOUND)
                    .build();
        }
    }
}

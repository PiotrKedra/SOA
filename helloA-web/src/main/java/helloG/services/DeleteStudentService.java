package helloG.services;

import helloG.StudentDAO;
import helloG.auth.AuthTokenRequired;
import helloG.resources.StudentNotFoundException;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;

import javax.inject.Inject;
import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import javax.ws.rs.Consumes;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

@Consumes(MediaType.APPLICATION_JSON)
@Path("/student")
@Api(value = "Delete student service", description = "Student swagger")
public class DeleteStudentService {

    @Inject
    StudentDAO studentDAO;

    @POST
    @Path("/delete")
    @AuthTokenRequired
    @ApiOperation(value = "Delete student", notes = "Delete student by id")
    @ApiResponses(value = {
            @ApiResponse(code = 204, message = "Student deleted"),
            @ApiResponse(code = 401, message = "Invalid token"),
            @ApiResponse(code = 400, message = "There is not id"),
            @ApiResponse(code = 404, message = "Student not found")
    })
    public Response delete(@QueryParam("id") @Valid @NotNull String id){
        if(id==null){
            return Response.status(Response.Status.BAD_REQUEST)
                    .build();
        }
        try {
            studentDAO.delete(id);
            System.out.println("wtf deleted student with id: " + id);
            return Response.ok()
                    .build();
        } catch (StudentNotFoundException e) {
            System.out.println(e);
            return Response.status(Response.Status.NOT_FOUND)
                    .entity("Student with id: " + id + " not found")
                    .build();
        }
    }
}

package helloG.jpaservice;

import helloG.Student;
import helloG.dao.ProjectxDAO;
import helloG.dao.StudentxDAO;
import helloG.jpa.ProjectX;
import helloG.jpa.StudentX;
import helloG.messages.StudentProtos;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;

import javax.annotation.PostConstruct;
import javax.inject.Inject;
import javax.validation.constraints.NotNull;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;


@Path("/jpa/project")
@Consumes(MediaType.APPLICATION_JSON)
@Produces(MediaType.APPLICATION_JSON)
@Api(value = "JPA service", description = "Student swagger")
public class JpaProjectService {

    @Inject
    ProjectxDAO projectxDAO;

    @Inject
    StudentxDAO studentxDAO;

    @POST
    @Path("/add")
    @ApiOperation(value = "add project", notes = "new project")
    @ApiResponses(value = {
            @ApiResponse(code = 204, message = "Project added"),
            @ApiResponse(code = 400, message = "Sth wrong"),
    })
    public Response addProject(@QueryParam("title") @NotNull String title) {
        projectxDAO.save(new ProjectX(title));
        return Response.ok("Project created")
                .status(Response.Status.CREATED)
                .build();
    }


    @GET
    @Path("/get")
    @ApiOperation(value = "get project", notes = "get project")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "ok"),
            @ApiResponse(code = 404, message = "not found"),
    })
    public Response get(@QueryParam("id") @NotNull int id) {
        Optional<ProjectX> projectX = projectxDAO.get(id);
        if (projectX.isPresent()) {
            System.out.println(projectX.get());
            return Response.ok(projectX.get())
                    .build();
        }
        return Response.status(Response.Status.NOT_FOUND)
                .build();
    }

    @POST
    @Path("/add_students")
    @ApiOperation(value = "add students to project", notes = "add student by id to project by id")
    @ApiResponses(value = {
            @ApiResponse(code = 204, message = "student added"),
            @ApiResponse(code = 404, message = ""),
    })
    public Response addStudents(@QueryParam("id") int id,
                                @QueryParam("student_id") int sid) {

        Optional<StudentX> s = studentxDAO.get(sid);
        System.out.println(s);

        StudentX studentx;
        if (s.isPresent()) {
            studentx = s.get();
        } else {
            return Response.status(Response.Status.NOT_FOUND)
                    .entity("Student not found")
                    .build();
        }

        Optional<ProjectX> projectX = projectxDAO.get(id);
        if (projectX.isPresent()) {

            ProjectX project = projectX.get();
            System.out.println(studentx);
            project.getStudents().add(studentx);

            projectxDAO.update(project);
            return Response.ok(project)
                    .build();
        }
        return Response.status(Response.Status.NOT_FOUND)
                .entity("Project not found id: " + id)
                .build();

        }




}

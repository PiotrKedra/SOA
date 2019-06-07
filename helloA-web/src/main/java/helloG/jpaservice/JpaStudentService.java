package helloG.jpaservice;

import helloG.dao.ProjectxDAO;
import helloG.dao.StudentxDAO;
import helloG.jpa.ProjectX;
import helloG.jpa.StudentX;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;

import javax.inject.Inject;
import javax.validation.constraints.NotNull;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.lang.reflect.Array;
import java.util.*;
import java.util.stream.Collectors;


@Path("/jpa")
@Consumes(MediaType.APPLICATION_JSON)
@Produces(MediaType.APPLICATION_JSON)
@Api(value = "JPA service", description = "Student swagger")
public class JpaStudentService {

    @Inject
    StudentxDAO studentxDAO;

    @Inject
    ProjectxDAO projectxDAO;

    @GET
    @Path("/search")
    @ApiOperation(value = "Search student", notes = "Custom search student")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "ok"),
            @ApiResponse(code = 404, message = "Student not found")
    })
    public Response search(@QueryParam("name") String name,
                           @QueryParam("last_name") String lastName,
                           @QueryParam("email") String email){

        StudentX studentX = new StudentX(name, lastName, email);

        System.out.println(studentX);

        List<StudentX> students = studentxDAO.search(studentX);

        return Response.ok(students)
                .build();
    }

    @GET
    @Path("/get")
    @ApiOperation(value = "Get student by id", notes = "Get student with given id")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Student found"),
            @ApiResponse(code = 404, message = "Student not found")
    })
    public Response get(@QueryParam("id") int id){
        Optional<StudentX> student = studentxDAO.get(id);
        if (student.isPresent()) {
            System.out.println(student.get());
            return Response.ok(student.get())
                    .build();
        }
        return Response.status(Response.Status.NOT_FOUND)
                .build();
    }

    @GET
    @Path("/all")
    @ApiOperation(value = "Get all students", notes = "get all students")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "ok"),
    })
    public Response getAll(){
        return Response.ok(studentxDAO.getAll())
                .build();
    }


    @POST
    @Path("/add")
    @ApiOperation(value = "Add student", notes = "Add student id, name, last_name, email")
    @ApiResponses(value = {
            @ApiResponse(code = 204, message = "Student added"),
            @ApiResponse(code = 400, message = "Sth wrong"),
    })
    public Response addStudent(@QueryParam("name") @NotNull String name,
                               @QueryParam("last_name") @NotNull String lastName,
                               @QueryParam("email") @NotNull String email) {
        studentxDAO.save(new StudentX(name, lastName, email));
        return Response.ok("Student created")
                .status(Response.Status.CREATED)
                .build();
    }

    @PUT
    @Path("/update")
    @ApiOperation(value = "Update student", notes = "Update student based on id")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Student updated"),
            @ApiResponse(code = 400, message = "Sth wrong"),
    })
    public Response updateStudent(@QueryParam("id") @NotNull int id,
                               @QueryParam("name") String name,
                               @QueryParam("last_name") String lastName,
                               @QueryParam("email") String email) {
        Optional<StudentX> student = studentxDAO.get(id);

        if(student.isPresent()) {
            StudentX studentX = updateStudent(name, lastName, email, student);
            studentxDAO.update(studentX);
            return Response.ok("Student updated")
                    .status(Response.Status.ACCEPTED)
                    .build();
        }else {
            return Response.status(Response.Status.NOT_FOUND)
                    .build();
        }
    }

    private StudentX updateStudent(String name, String lastName, String email, Optional<StudentX> student) {
        StudentX studentX = student.get();
        if(name!=null) {
            studentX.setName(name);
        }
        if(lastName!=null) {
            studentX.setSurname(lastName);
        }
        if(email!=null) {
            studentX.setEmail(email);
        }
        return studentX;
    }


    @DELETE
    @Path("/delete")
    @ApiOperation(value = "Delete student", notes = "Delete student based on id")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Student deleted"),
            @ApiResponse(code = 400, message = "Sth wrong"),
    })
    public Response deleteStudent(@QueryParam("id") @NotNull int id) {
        Optional<StudentX> student = studentxDAO.get(id);

        if(student.isPresent()) {
            studentxDAO.delete(student.get());
            return Response.ok("Student deleted")
                    .status(Response.Status.ACCEPTED)
                    .build();
        }else {
            return Response.status(Response.Status.NOT_FOUND)
                    .build();
        }
    }

    @PUT
    @Path("/add_project")
    @ApiOperation(value = "add to project to student", notes = "add project by id")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "project added"),
            @ApiResponse(code = 404, message = ""),
    })
    public Response addProject(@QueryParam("id") int id,
                                @QueryParam("project_id") int projectId) {

        Optional<ProjectX> projectX = projectxDAO.get(projectId);
        ProjectX project;
        if(projectX.isPresent()){
            project = projectX.get();
        }else {
            System.out.println("not found project: " + projectId);
            return Response.status(Response.Status.NOT_FOUND)
                    .entity("Project not found id: " + projectId)
                    .build();
        }

        Optional<StudentX> studentX = studentxDAO.get(id);
        if(studentX.isPresent()){
            StudentX student = studentX.get();
            student.getProjects().add(project);
            studentxDAO.update(student);
            return Response.ok(student)
                    .build();
        }
        System.out.println("not found student: " + id);
        return Response.status(Response.Status.NOT_FOUND)
                .entity("Student not found id: " + id)
                .build();

    }

}

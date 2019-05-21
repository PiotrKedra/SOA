package helloG.services;

import helloG.auth.AuthResource;
import helloG.auth.TokenResource;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;

import javax.inject.Inject;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

@Path("/auth")
@Consumes(MediaType.APPLICATION_JSON)
@Produces(MediaType.APPLICATION_JSON)
@Api(value = "Login service", description = "Student swagger")
public class AuthService {

    @Inject
    TokenResource tokenResource;

    @Inject
    AuthResource authResource;

    @GET
    @Path("/login")
    @ApiOperation(value = "Login", notes = "Login with username and password and then returns token")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Created token"),
            @ApiResponse(code = 400, message = "Invalid request"),
            @ApiResponse(code = 401, message = "Unauthorized")
    })
    public Response login(@QueryParam("username") String username,
                          @QueryParam("password") String password){
        if(username==null || password==null){
            return Response.status(Response.Status.BAD_REQUEST)
                    .entity("Lack of password or username")
                    .build();
        }
        try{
            authResource.authenticate(username, password);
            return Response.ok(tokenResource.generateToken())
                    .build();
        } catch (NotAuthorizedException e){
            return Response.status(Response.Status.UNAUTHORIZED)
                    .entity("Wrong password or username")
                    .build();
        }
    }

}

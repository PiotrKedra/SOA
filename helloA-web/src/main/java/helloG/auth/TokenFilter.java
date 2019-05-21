package helloG.auth;


import javax.annotation.Priority;
import javax.inject.Inject;
import javax.ws.rs.Priorities;
import javax.ws.rs.container.ContainerRequestContext;
import javax.ws.rs.container.ContainerRequestFilter;
import javax.ws.rs.core.Response;
import javax.ws.rs.ext.Provider;
import java.io.IOException;
import com.auth0.jwt.exceptions.JWTVerificationException;
import helloG.resources.Unauthorize;


@Provider
@AuthTokenRequired   // 1
@Priority(Priorities.AUTHENTICATION)  // 2
public class TokenFilter implements ContainerRequestFilter {

    @Inject
    TokenResource tokenResource;


    @Override
    public void filter(ContainerRequestContext containerRequestContext) {
        String authHeaderValue = containerRequestContext.getHeaderString("Authorization");
        try {
            String token = authHeaderValue.substring("Bearer".length()).trim();
            tokenResource.verifyToken(token);
            System.out.println("Authorized");
        } catch (JWTVerificationException e){
            System.out.println("Unauthorized");
            containerRequestContext.abortWith(Response.status(Response.Status.UNAUTHORIZED).build());
        } catch (NullPointerException e){
            System.out.println("Lack of token");
            containerRequestContext.abortWith(Response.status(Response.Status.UNAUTHORIZED).build());
        }
    }
}

package helloG.auth;

import com.auth0.jwt.exceptions.JWTVerificationException;

import javax.inject.Singleton;
import javax.ws.rs.NotAuthorizedException;
import java.util.HashMap;
import java.util.Map;

@Singleton
public class AuthResource {

    private Map<String,String> accounts;

    {
        accounts = new HashMap<>();
        accounts.put("admin", "admin");
        accounts.put("pkedra", "pk");
    }

    public void authenticate(String username, String password){
        if(!accounts.containsKey(username)) throw new NotAuthorizedException("Wrong username");
        if(!accounts.get(username).equals(password)) throw new NotAuthorizedException("Wrong password");
    }
}

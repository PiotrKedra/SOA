package helloG.auth;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.JWTCreationException;
import com.auth0.jwt.exceptions.JWTVerificationException;
import com.auth0.jwt.interfaces.JWTVerifier;

import javax.inject.Singleton;
import java.nio.charset.Charset;
import java.util.Random;

@Singleton
public class TokenResource {

    private String secret;
    private Algorithm algorithm;

    public TokenResource(){
        secret = generateRandomSecret();
        System.out.println(secret);
        algorithm = Algorithm.HMAC256(secret);
    }


    private String generateRandomSecret(){
        byte[] array = new byte[15]; // length is bounded by 7
        new Random().nextBytes(array);
        return new String(array, Charset.forName("UTF-8"));
    }

    public String generateToken() throws JWTCreationException {
        String token = JWT.create()
                .withIssuer("auth0")
                .sign(algorithm);
        return token;
    }

    public void verifyToken(String token) throws JWTVerificationException {
        JWTVerifier verifier = JWT.require(algorithm)
                .withIssuer("auth0")
                .build();
        verifier.verify(token);
        System.out.println("OK");
    }

}

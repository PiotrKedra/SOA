package helloG.jpaservice;

import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;


@Path("/swagger")
@Produces(MediaType.TEXT_HTML)
public class swager {

    @GET
    @Produces({MediaType.TEXT_HTML})
    public InputStream viewHome() throws FileNotFoundException {
        File f = new File("D:\\code\\helloA\\helloA-web\\src\\main\\webapp\\index.html");
        return new FileInputStream(f);
    }
}

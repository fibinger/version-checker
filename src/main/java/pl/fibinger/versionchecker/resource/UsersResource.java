package pl.fibinger.versionchecker.resource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import pl.fibinger.versionchecker.representation.UserRepresentation;
import pl.fibinger.versionchecker.service.UserService;

import javax.ws.rs.POST;
import javax.ws.rs.Path;

@Path("/users")
@Component
public class UsersResource {

    @Autowired
    private UserService userService;

    @POST
    @Path("/")
    public UserRepresentation postUser(String userName) {
        return userService.addUser(userName);
    }

}

package ch.bzz.javaKeep.service;

/**
 * service for users
 * <p>
 * M133: Restful Webservice
 *
 * @author Aladin Boudouda
 */

import ch.bzz.javaKeep.data.DataHandler;
import ch.bzz.javaKeep.model.User;

import javax.ws.rs.*;
import javax.ws.rs.core.*;

@Path("user")
public class UserService {

    /**
     * login a user with username/password
     *
     * @param username the username
     * @param password the password
     * @return Response-object with the userrole
     */
    @POST
    @Path("login")
    @Produces(MediaType.TEXT_PLAIN)
    public Response login(
            @FormParam("username") String username,
            @FormParam("password") String password
    ) {
        int httpStatus;

        User user = DataHandler.readUser(username, password);
        if (user.getUserRole().equals("guest")) {
            httpStatus = 404;
        } else {
            httpStatus = 200;
        }

        NewCookie cookie = new NewCookie(
                "userRole",
                user.getUserRole(),
                "/",
                "",
                "Login-Cookie",
                600,
                false
        );
        Response response = Response
                .status(httpStatus)
                .cookie(cookie)
                .entity("")
                .build();
        return response;
    }

    /**
     * logout current user
     *
     * @return Response object with guest-Cookie
     */
    @DELETE
    @Path("logout")
    @Produces(MediaType.TEXT_PLAIN)
    public Response logout() {
        NewCookie cookie = new NewCookie(
                "userRole",
                "guest",
                "/",
                "",
                "Login-Cookie",
                1,
                false
        );
        Response response = Response
                .status(200)
                .cookie(cookie)
                .entity("")
                .build();
        return response;
    }
}

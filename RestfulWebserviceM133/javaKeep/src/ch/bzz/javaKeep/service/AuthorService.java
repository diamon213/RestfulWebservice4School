package ch.bzz.javaKeep.service;

import ch.bzz.javaKeep.data.DataHandler;
import ch.bzz.javaKeep.model.Author;

import javax.validation.Valid;
import javax.validation.constraints.Pattern;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.NewCookie;
import javax.ws.rs.core.Response;
import java.util.Map;
import java.util.UUID;

/**
 * service for authors
 * <p>
 * M133: Restful Webservice
 *
 * @author Aladin Boudouda
 */

@Path("author")
public class AuthorService {

    /**
     * produces a map of all authors
     *
     * @param userRole the role of the current use
     * @return Response
     */
    @GET
    @Path("list")
    @Produces(MediaType.APPLICATION_JSON)

    public Response listAuthors(
            @CookieParam("userRole") String userRole
    ) {
        Map<String, Author> authorMap = null;
        int httpStatus;
        if (userRole == null || userRole.equals("guest")) {
            httpStatus = 403;
        } else {
            httpStatus = 200;
            authorMap = DataHandler.getAuthorMap();
        }
        NewCookie cookie = new NewCookie(
                "userRole",
                userRole,
                "/",
                "",
                "Login-Cookie",
                600,
                false
        );
        Response response = Response
                .status(httpStatus)
                .entity(authorMap)
                .cookie(cookie)
                .build();
        return response;

    }

    /**
     * reads author identified by its uuid
     *
     * @param authorUUID the authorUUID to be searched
     * @param userRole  the role of the current user
     * @return Response with publisher-object
     */
    @GET
    @Path("read")
    @Produces(MediaType.APPLICATION_JSON)
    public Response readAuthors(
            @Pattern(regexp = "[0-9a-fA-F]{8}-([0-9a-fA-F]{4}-){3}[0-9a-fA-F]{12}")
            @QueryParam("uuid") String authorUUID,
            @CookieParam("userRole") String userRole) {
        Author author = null;
        int httpStatus;
        if (userRole == null || userRole.equals("guest")) {
            httpStatus = 403;
        } else {
            author = DataHandler.getAuthorMap().get(authorUUID);

            if (author != null) {
                httpStatus = 200;
            } else {
                httpStatus = 404;
            }
        }
        NewCookie cookie = new NewCookie(
                "userRole",
                userRole,
                "/",
                "",
                "Login-Cookie",
                600,
                false
        );
        Response response = Response
                .status(httpStatus)
                .entity(author)
                .cookie(cookie)
                .build();
        return response;

    }

    /**
     * creates a new author
     *
     * @param author a valid publisher
     * @param userRole  the role of the current user
     * @return Response
     */
    @POST
    @Path("create")
    @Produces(MediaType.TEXT_PLAIN)
    public Response createAuthor(
            @Valid @BeanParam Author author,
            @CookieParam("userRole") String userRole
    ) {
        int httpStatus;
        if (userRole != null && userRole.equals("admin")) {

            httpStatus = 200;

            author.setAuthorUUID(UUID.randomUUID().toString());

            Map<String, Author> authorMap = DataHandler.getAuthorMap();

            authorMap.put(author.getAuthorUUID(), author);
            DataHandler.writeAuthors(authorMap);

        } else {
            httpStatus = 403;
        }
        NewCookie cookie = new NewCookie(
                "userRole",
                userRole,
                "/",
                "",
                "Login-Cookie",
                600,
                false
        );
        Response response = Response
                .status(httpStatus)
                .entity("")
                .cookie(cookie)
                .build();
        return response;
    }

    /**
     * updates an existing author
     *
     * @param author a valid author
     * @param userRole the role of the current user
     * @return Response
     */
    @PUT
    @Path("update")
    @Produces(MediaType.TEXT_PLAIN)
    public Response updateAuthor(
            @Valid @BeanParam Author author,
            @CookieParam("userRole") String userRole
    ) {
        int httpStatus;
        if (userRole != null && userRole.equals("admin")) {

            Map<String, Author> authorMap = DataHandler.getAuthorMap();
            if (authorMap.containsKey(author.getAuthorUUID())) {
                authorMap.put(author.getAuthorUUID(), author);
                DataHandler.writeAuthors(authorMap);
                httpStatus = 200;
            } else {
                httpStatus = 404;
            }
        } else {
            httpStatus = 403;
        }
        NewCookie cookie = new NewCookie(
                "userRole",
                userRole,
                "/",
                "",
                "Login-Cookie",
                600,
                false
        );
        Response response = Response
                .status(httpStatus)
                .entity("")
                .cookie(cookie)
                .build();
        return response;
    }

}

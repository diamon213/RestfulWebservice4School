package ch.bzz.javaKeep.service;

/**
 * service for the Notizen
 * <p>
 * M133: Restful Webservice
 *
 * @author Aladin Boudouda
 */

import ch.bzz.javaKeep.data.DataHandler;
import ch.bzz.javaKeep.model.Author;
import ch.bzz.javaKeep.model.Notiz;

import javax.validation.Valid;
import javax.validation.constraints.Pattern;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.NewCookie;
import javax.ws.rs.core.Response;
import java.util.Map;
import java.util.UUID;

@Path("notiz")
public class NotizService {

    /**
     * produziert eine map von allen Notizen
     *
     * @Param userRole the userRole of the current user
     * @return Response
     */
    @GET
    @Path("list")
    @Produces(MediaType.APPLICATION_JSON)

    public Response listNotizen(
            @CookieParam("userRole") String userRole
    ) {
        Map<String, Notiz> notizMap = null;
        int httpStatus;
        if (userRole == null || userRole.equals("guest")) {
            httpStatus = 403;
        } else {
            httpStatus = 200;
            notizMap = DataHandler.getNotizMap();
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
                .entity(notizMap)
                .cookie(cookie)
                .build();
        return response;

    }

    /**
     * liest eine einzelne Notiz über die UUID
     *
     * @param notizUUID die notizUUID in der URL
     * @Param userRole the userRole of the current user
     * @return Response
     */
    @GET
    @Path("read")
    @Produces(MediaType.APPLICATION_JSON)

    public Response readNotiz(
            @Pattern(regexp = "[0-9a-fA-F]{8}-([0-9a-fA-F]{4}-){3}[0-9a-fA-F]{12}")
            @QueryParam("uuid") String notizUUID,

            @CookieParam("userRole") String userRole
    ) {
        Notiz notiz = null;
        int httpStatus;
        if (userRole == null || userRole.equals("guest")) {
            httpStatus = 403;
        } else {
            notiz = DataHandler.getNotizMap().get(notizUUID);

            if (notiz != null) {
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
                .entity(notiz)
                .cookie(cookie)
                .build();
        return response;
    }


    /**
     * creates a new book
     *
     * @param notiz a valid Notiz
     * @param authorUUID the unique key of the author
     * @param userRole the userRole of the current use
     * @return Response-object
     */
    @POST
    @Path("create")
    @Produces(MediaType.TEXT_PLAIN)
    public Response createNotiz(
            @Valid @BeanParam Notiz notiz,
            @Pattern(regexp = "[0-9a-fA-F]{8}-([0-9a-fA-F]{4}-){3}[0-9a-fA-F]{12}")
            @FormParam("authorUUID") String authorUUID,

            @CookieParam("userRole") String userRole
    ) {
        int httpStatus;
        if (userRole != null && userRole.equals("admin")) {

            httpStatus = 200;

            notiz.setNotizUUID(UUID.randomUUID().toString());
            if (DataHandler.getAuthorMap().containsKey(authorUUID)) {
                Author author = DataHandler.getAuthorMap().get(authorUUID);
                notiz.setAuthor(author);

                Map<String, Notiz> notizMap = DataHandler.getNotizMap();

                notizMap.put(notiz.getNotizUUID(), notiz);
                DataHandler.writeNotizen(notizMap);
            } else {
                httpStatus = 400;
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

    /**
     * updates an existing book
     *
     * @param notiz a valid notiz
     * @param authorUUID the unique key of the author
     * @param userRole the userRole of the current use
     * @return Response-object
     */
    @PUT
    @Path("update")
    @Produces(MediaType.TEXT_PLAIN)
    public Response updateNotiz(
            @Valid @BeanParam Notiz notiz,
            @FormParam("authorUUID") String authorUUID,

            @CookieParam("userRole") String userRole
    ) {
        int httpStatus;
        if (userRole != null && userRole.equals("admin")) {

            Map<String, Notiz> notizMap = DataHandler.getNotizMap();
            if (notizMap.containsKey(notiz.getNotizUUID())) {
                if (DataHandler.getAuthorMap().containsKey(authorUUID)) {
                    Author author = DataHandler.getAuthorMap().get(authorUUID);

                    notiz.setAuthor(author);
                    notizMap.put(notiz.getNotizUUID(), notiz);
                    DataHandler.writeNotizen(notizMap);
                    httpStatus = 200;
                } else {
                    httpStatus = 400;
                }
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

    /**
     * deletes an existing notiz identified by its uuid
     *
     * @param notizUUID the unique key for the notiz
     * @param userRole the userRole of the current use
     * @return Response-object
     */
    @DELETE
    @Path("delete")
    @Produces(MediaType.TEXT_PLAIN)
    public Response deleteNotiz(
            @Pattern(regexp = "[0-9a-fA-F]{8}-([0-9a-fA-F]{4}-){3}[0-9a-fA-F]{12}")
            @QueryParam("uuid") String notizUUID,

            @CookieParam("userRole") String userRole
    ) {
        int httpStatus;
        if (userRole != null && userRole.equals("admin")) {

            Map<String, Notiz> notizMap = DataHandler.getNotizMap();
            Notiz notiz = notizMap.get(notizUUID);
            if (notiz != null) {
                httpStatus = 200;
                notizMap.remove(notizUUID);
                DataHandler.writeNotizen(notizMap);
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
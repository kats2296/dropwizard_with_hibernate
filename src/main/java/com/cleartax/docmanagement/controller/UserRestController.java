package com.cleartax.docmanagement.controller;

import com.cleartax.docmanagement.dao.UserDAO;
import com.cleartax.docmanagement.domain.User;
import io.dropwizard.hibernate.UnitOfWork;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.util.HashMap;
import java.util.Map;

@Path("/v1.0/user")
@Produces(MediaType.APPLICATION_JSON)
public class UserRestController {

    private UserDAO userDAO;

    public UserRestController(UserDAO userDAO) {
        this.userDAO = userDAO;
    }

    @GET
    @UnitOfWork
    public Response getAllUsers() {
        return Response.ok(userDAO.getAllUsers()).build();
    }

    @GET
    @Path("/{userId}")
    @UnitOfWork
    public Response getUserById(@PathParam("userId") @Valid final int userId) {
        return Response.ok(userDAO.getUserById(userId)).build();
    }

    @POST
    @UnitOfWork
    public Response createUser(@NotNull @Valid final User user) {
        return Response.ok(userDAO.createUser(user)).build();
    }

    @PUT
    @Path("/{userId}")
    @UnitOfWork
    public Response updateUser(@NotNull @Valid final User user,
                               @PathParam("userId") final int userId) {
        user.setUserId(userId);
        userDAO.updateUser(user);
        return Response.ok(userDAO.getUserById(userId)).build();
    }

    @DELETE
    @Path("/{id}")
    @UnitOfWork
    public Response deleteUser(@PathParam("id") @Valid final int userId) {
        Map<String, String> response = new HashMap<>();
        userDAO.deleteUser(userId);
        Object user = userDAO.getUserById(userId);
        if(user == null)
            response.put("status", "User is deleted");

        else
            response.put("status", "Error in deleting the user");

        return Response.ok(response).build();
    }
}

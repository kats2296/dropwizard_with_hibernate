package com.cleartax.docmanagement.service;

import com.cleartax.docmanagement.dao.UserDAO;
import com.cleartax.docmanagement.domain.User;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.ws.rs.WebApplicationException;
import javax.ws.rs.core.Response;
import java.util.List;
import java.util.Objects;

public class UserService {

    private static final Logger logger = LoggerFactory.getLogger(UserService.class);

    private static final String DATABASE_ACCESS_ERROR =
            "Could not reach the MySQL database. The database may be down or there may be network connectivity issues. Details: ";
    private static final String DATABASE_CONNECTION_ERROR =
            "Could not create a connection to the MySQL database. The database configurations are likely incorrect. Details: ";
    private static final String UNEXPECTED_DATABASE_ERROR =
            "Unexpected error occurred while attempting to reach the database. Details: ";
    private static final String SUCCESS = "Success";
    private static final String UNEXPECTED_DELETE_ERROR = "An unexpected error occurred while deleting user.";
    private static final String USER_NOT_FOUND = "User id %s not found.";

    private UserDAO userDAO;

    public UserService(UserDAO userDAO) {
        this.userDAO = userDAO;
    }

    public List<User> getAllUsers() {
        return userDAO.getAllUsers();
    }

    public User getUserById(int userId) {
        User user = userDAO.getUserById(userId);
        if (Objects.isNull(user)) {
            throw new WebApplicationException(String.format(USER_NOT_FOUND, userId), Response.Status.NOT_FOUND);
        }
        return user;
    }

    public User createUser(User user) {
        userDAO.createUser(user);
        return userDAO.getUserById(user.getUserId());
    }

    public User updateUser(User user) {
        if (Objects.isNull(userDAO.getUserById(user.getUserId()))) {
            throw new WebApplicationException(String.format(USER_NOT_FOUND, user.getUserId()),
                    Response.Status.NOT_FOUND);
        }
        userDAO.updateUser(user);
        return userDAO.getUserById(user.getUserId());
    }

    public String deleteUser(int userId) {
        userDAO.deleteUser(userId);
        Object user = userDAO.getUserById(userId);
        if(user == null) {
            return SUCCESS;
        }

        else {
            throw new WebApplicationException(String.format(USER_NOT_FOUND, userId), Response.Status.NOT_FOUND);
        }
    }

    /*@CreateSqlObject
    abstract UserDao userDao();

    public List<User> getUsers() {
        return userDao().getAllUsers();
    }

    public User getUser(int userId) {
        User user = userDao().getUserById(userId);
        if (Objects.isNull(user)) {
            throw new WebApplicationException(String.format(USER_NOT_FOUND, userId), Response.Status.NOT_FOUND);
        }
        return user;
    }

    public User createUser(User user) {
        userDao().createUser(user);
        return userDao().getUserById(userDao().lastInsertId());
    }

    public User updateUser(User user) {
        if (Objects.isNull(userDao().getUserById(user.getUserId()))) {
            throw new WebApplicationException(String.format(USER_NOT_FOUND, user.getUserId()),
                    Response.Status.NOT_FOUND);
        }
        userDao().updateUser(user);
        return userDao().getUserById(user.getUserId());
    }

    public String deleteUser(final int userId) {
        int result = userDao().deleteUser(userId);
        switch (result) {
            case 1:
                return SUCCESS;
            case 0:
                throw new WebApplicationException(String.format(USER_NOT_FOUND, userId), Response.Status.NOT_FOUND);
            default:
                throw new WebApplicationException(UNEXPECTED_DELETE_ERROR, Response.Status.INTERNAL_SERVER_ERROR);
        }
    }*/
}

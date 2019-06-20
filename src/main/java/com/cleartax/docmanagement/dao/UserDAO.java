package com.cleartax.docmanagement.dao;

import com.cleartax.docmanagement.domain.User;
import io.dropwizard.hibernate.AbstractDAO;
import org.hibernate.SessionFactory;

import java.util.List;

public class UserDAO extends AbstractDAO<User> {

    public UserDAO(SessionFactory sessionFactory) {
        super(sessionFactory);
    }

    public List<User> getAllUsers() {
        return list(criteria());
    }

    public User getUserById(int userId) {
        return get(userId);
    }

    public User createUser(User user) {
         currentSession().save(user);
         currentSession().getTransaction().commit();
         return user;
    }

    public void deleteUser(int userId) {
        User user = getUserById(userId);
        currentSession().delete(user);
    }

    public void updateUser(User user) {
        User userToBeUpdated = getUserById(user.getUserId());
        userToBeUpdated.setUserName(user.getUserName());
        userToBeUpdated.setEmailId(user.getEmailId());
        persist(userToBeUpdated);
    }
}

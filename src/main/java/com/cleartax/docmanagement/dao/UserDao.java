package com.cleartax.docmanagement.dao;

import com.cleartax.docmanagement.domain.User;
import com.cleartax.docmanagement.mapper.UserMapper;
import org.skife.jdbi.v2.sqlobject.Bind;
import org.skife.jdbi.v2.sqlobject.BindBean;
import org.skife.jdbi.v2.sqlobject.SqlQuery;
import org.skife.jdbi.v2.sqlobject.SqlUpdate;
import org.skife.jdbi.v2.sqlobject.customizers.RegisterMapper;

import java.util.List;

@RegisterMapper(UserMapper.class)
public interface UserDao  {

    /*
    public UserDao(SessionFactory sessionFactory) {
        super(sessionFactory);
    }

    public List<User> getAllUsers() {
        return list(namedQuery("getAllUsers"));
    }

    public List<User> getUserById(int userId) {
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append(userId);
        return list(
                namedQuery("getUserById")
                .setParameter("userId", stringBuilder.toString())
        );
    }

    public List<User> createUser(User user) {
        StringBuilder builder = new StringBuilder();
        builder.append()
    }*/


    @SqlQuery("select * from user;")
    List<User> getAllUsers();

    @SqlQuery("select * from user where userId = :userId;")
    User getUserById(@Bind("userId") final int userId);

    @SqlUpdate("insert into user values (:userId, :userName, :emailId);")
    void createUser(@BindBean final User user);

    @SqlUpdate("update user set userId = coalesce(:userId, userId), " +
            " userName = coalesce(:userName, userName), emailId = coalesce(:emailId, emailId)" +
            " where userId = :userId")
    void updateUser(@BindBean final User user);

    @SqlUpdate("delete from user where userId = :userId")
    int deleteUser(@Bind("userId") final int userId);

    @SqlQuery("select last_insert_id();")
    int lastInsertId();


}

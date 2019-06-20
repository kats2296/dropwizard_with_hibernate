package com.cleartax.docmanagement.mapper;

import com.cleartax.docmanagement.domain.User;
import java.sql.ResultSet;
import java.sql.SQLException;
import org.skife.jdbi.v2.StatementContext;
import org.skife.jdbi.v2.tweak.ResultSetMapper;

public class UserMapper implements ResultSetMapper<User> {
    private static final String USERID = "userId";
    private static final String USERNAME = "userName";
    private static final String EMAILID = "emailId";

    public User map(int i, ResultSet resultSet, StatementContext statementContext)
            throws SQLException {
        User user = new User(resultSet.getInt(USERID),resultSet.getString(USERNAME), resultSet.getString(EMAILID));
        return user;
    }
}

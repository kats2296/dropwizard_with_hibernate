package com.cleartax.docmanagement.mapper;
import com.cleartax.docmanagement.domain.SharedLink;
import org.skife.jdbi.v2.StatementContext;
import org.skife.jdbi.v2.tweak.ResultSetMapper;

import java.sql.ResultSet;
import java.sql.SQLException;

public class SharedLinkMapper implements ResultSetMapper<SharedLink> {
    private static final String SHAREDLINKID = "sharedLinkId";
    private static final String SHAREDLINK = "sharedLink";
    private static final String DOCUMENTID = "documentId";


    public SharedLink map(int i, ResultSet resultSet, StatementContext statementContext)
            throws SQLException {
        SharedLink sharedLink = new SharedLink(resultSet.getInt(SHAREDLINKID), resultSet.getString(SHAREDLINK), resultSet.getInt(DOCUMENTID));
        return sharedLink;
    }
}


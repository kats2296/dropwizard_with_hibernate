package com.cleartax.docmanagement.mapper;

import com.cleartax.docmanagement.domain.DocumentPojo;
import org.skife.jdbi.v2.StatementContext;
import org.skife.jdbi.v2.tweak.ResultSetMapper;

import java.sql.ResultSet;
import java.sql.SQLException;

public class DocumentPojoMapper implements ResultSetMapper<DocumentPojo> {
    private static final String DOCUMENTID = "documentId";
    private static final String USERID = "userId";
    private static final String USERLOGGEDIN = "userLoggedIn";
    private static final String DOCNAME = "docName";
    private static final String DOCHEADER = "docHeader";
    private static final String DOCCONTENT = "docContent";
    private static final String DOCNOTES = "docNotes";


    public DocumentPojo map(int i, ResultSet resultSet, StatementContext statementContext)
            throws SQLException {
        DocumentPojo documentPojo = new DocumentPojo(resultSet.getInt(DOCUMENTID),resultSet.getInt(USERID), resultSet.getInt(USERLOGGEDIN),
                resultSet.getString(DOCNAME), resultSet.getString(DOCHEADER), resultSet.getString(DOCCONTENT), resultSet.getString(DOCNOTES));
        return documentPojo;
    }
}

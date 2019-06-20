package com.cleartax.docmanagement.mapper;

import com.cleartax.docmanagement.domain.Partner;
import org.skife.jdbi.v2.StatementContext;
import org.skife.jdbi.v2.tweak.ResultSetMapper;

import java.sql.ResultSet;
import java.sql.SQLException;

public class PartnerMapper implements ResultSetMapper<Partner> {
    private static final String PARTNERID = "partnerId";
    private static final String USERID = "userId";
    private static final String PARTNERNAME = "partnerName";
    private static final String PARTNEREMAIL = "partnerEmail";

    public Partner map(int i, ResultSet resultSet, StatementContext statementContext)
            throws SQLException {
        Partner partner = new Partner(resultSet.getInt(PARTNERID), resultSet.getInt(USERID),resultSet.getString(PARTNERNAME), resultSet.getString(PARTNEREMAIL));
        return partner;
    }
}

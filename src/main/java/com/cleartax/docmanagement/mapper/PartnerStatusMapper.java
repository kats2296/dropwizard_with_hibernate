package com.cleartax.docmanagement.mapper;

import com.cleartax.docmanagement.domain.PartnerStatus;
import org.skife.jdbi.v2.StatementContext;
import org.skife.jdbi.v2.tweak.ResultSetMapper;

import java.sql.ResultSet;
import java.sql.SQLException;

public class PartnerStatusMapper implements ResultSetMapper<PartnerStatus> {
    private static final String SHAREDLINKID = "sharedLinkId";
    private static final String PARTNERID = "partnerId";
    private static final String DOCUMENTID = "documentId";
    private static final String STATUS = "status";


    public PartnerStatus map(int i, ResultSet resultSet, StatementContext statementContext)
            throws SQLException {
        PartnerStatus partnerStatus = new PartnerStatus(resultSet.getInt(SHAREDLINKID), resultSet.getInt(PARTNERID), resultSet.getInt(DOCUMENTID), resultSet.getString(STATUS));
        return partnerStatus;
    }
}

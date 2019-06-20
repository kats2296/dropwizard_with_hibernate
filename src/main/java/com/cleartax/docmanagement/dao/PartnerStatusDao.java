package com.cleartax.docmanagement.dao;

import com.cleartax.docmanagement.domain.PartnerStatus;
import com.cleartax.docmanagement.mapper.PartnerStatusMapper;
import org.skife.jdbi.v2.sqlobject.Bind;
import org.skife.jdbi.v2.sqlobject.SqlQuery;
import org.skife.jdbi.v2.sqlobject.customizers.RegisterMapper;

import java.util.List;

@RegisterMapper(PartnerStatusMapper.class)
public interface PartnerStatusDao {

    @SqlQuery("select * from partnerstatus where status = :status;")
    List<PartnerStatus> getActivePartners(@Bind("status") final String status);

//    @SqlUpdate("select status from partnerstatus where partnerId = :partnerId")
//    String checkPartnerStatus(@Bind("partnerId") final int partnerId);

    @SqlQuery("select status from partnerstatus where partnerId = :partnerId and documentId = :documentId")
    String getPartnerStatus(@Bind("partnerId") final int partnerId, @Bind("documentId") final int documentId);


//    @SqlQuery("select * from partnerstatus where activeStatus = :activeStatus;")
//    List<PartnerStatusWithLink> getPartnersWithStatus(@Bind("documentId") final int documentId);
//
//    @SqlQuery("select * from partnerstatus where sharedLinkId = :sharedLinkId;")
//    SharedLink getSharedLinkByLinkId(@Bind("sharedLinkId") final int sharedLinkId);
//
//    @SqlUpdate("insert into partnerstatus (sharedLink, documentId) values (:sharedLink, :documentId);")
//    void addSharedLinktoDb(@BindBean final SharedLink sharedLink);
//
//    @SqlUpdate("delete from partnerstatus where documentId = :documentId")
//    int deleteSharedLink(@Bind("documentId") final int documentId);
//
//    @SqlQuery("select last_insert_id();")
//    int lastInsertId();

}

package com.cleartax.docmanagement.dao;

import com.cleartax.docmanagement.domain.SharedLink;
import com.cleartax.docmanagement.mapper.SharedLinkMapper;
import org.skife.jdbi.v2.sqlobject.Bind;
import org.skife.jdbi.v2.sqlobject.BindBean;
import org.skife.jdbi.v2.sqlobject.SqlQuery;
import org.skife.jdbi.v2.sqlobject.SqlUpdate;
import org.skife.jdbi.v2.sqlobject.customizers.RegisterMapper;

import java.util.List;

@RegisterMapper(SharedLinkMapper.class)
public interface SharedLinkDao {

    @SqlQuery("select * from sharedlink;")
    List<SharedLink> getAllSharedLinks();

    @SqlQuery("select * from sharedlink where documentId = :documentId;")
    SharedLink getSharedLinkByDocId(@Bind("documentId") final int documentId);

    @SqlQuery("select * from sharedlink where sharedLinkId = :sharedLinkId;")
    SharedLink getSharedLinkByLinkId(@Bind("sharedLinkId") final int sharedLinkId);

    @SqlUpdate("insert into sharedlink values (:sharedLinkId, :sharedLink, :documentId);")
    void addSharedLinktoDb(@BindBean final SharedLink sharedLink);

    @SqlUpdate("delete from sharedlink where documentId = :documentId")
    int deleteSharedLink(@Bind("documentId") final int documentId);

    @SqlQuery("select last_insert_id();")
    int lastInsertId();

}

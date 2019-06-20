package com.cleartax.docmanagement.dao;

import com.cleartax.docmanagement.domain.DocumentPojo;
import com.cleartax.docmanagement.mapper.DocumentPojoMapper;
import org.skife.jdbi.v2.sqlobject.Bind;
import org.skife.jdbi.v2.sqlobject.BindBean;
import org.skife.jdbi.v2.sqlobject.SqlQuery;
import org.skife.jdbi.v2.sqlobject.SqlUpdate;
import org.skife.jdbi.v2.sqlobject.customizers.RegisterMapper;

import java.util.List;

@RegisterMapper(DocumentPojoMapper.class)
public interface DocumentPojoDao {

    @SqlQuery("select * from documententity;")
    List<DocumentPojo> getAllDocuments();

    @SqlQuery("select * from documententity where documentId = :documentId;")
    DocumentPojo getDocumentByDocId(@Bind("documentId") final int documentId);

    @SqlQuery("select * from documententity where userId = :userId;")
    List<DocumentPojo> getDocumentsByUserId(@Bind("userId") final int userId);

    @SqlUpdate("insert into documententity values (:documentId, :userId, :userLoggedIn, :docName, :docHeader, :docContent, :docNotes);")
    void createDocument(@BindBean final DocumentPojo documentPojo);

    @SqlUpdate("update documententity set documentId = coalesce(:documentId, documentId), " +
            " userId = coalesce(:userId, userId), userLoggedIn = coalesce(:userLoggedIn, userLoggedIn), docName = coalesce(:docName, docName), " +
            "docHeader = coalesce(:docHeader, docHeader), docContent = coalesce(:docContent, docContent),  " +
            " docNotes = coalesce(:docNotes, docNotes) where documentId = :documentId")
    void updateDocument(@BindBean final DocumentPojo DocumentPojo);

    @SqlUpdate("delete from documententity where documentId = :documentId")
    int deleteDocument(@Bind("documentId") final int documentId);

    @SqlQuery("select last_insert_id();")
    int lastInsertId();

    @SqlQuery("select documentId from documententity where userId = :userId")
    List<Integer> getDocIdCorrespondingToUserId(@Bind("userId") final int userId);

    @SqlQuery("select userId from documententity where documentId = :documentId")
    int getUserIdOfDocument(@Bind("documentId") final int documentId);

    @SqlQuery("select documentId from documententity")
    List<Integer> getAllDocumentIds();
}

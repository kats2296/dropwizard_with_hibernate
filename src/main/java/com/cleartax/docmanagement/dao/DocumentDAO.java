package com.cleartax.docmanagement.dao;

import com.cleartax.docmanagement.domain.Document;
import io.dropwizard.hibernate.AbstractDAO;
import org.hibernate.SQLQuery;
import org.hibernate.SessionFactory;
import java.util.List;

public class DocumentDAO extends AbstractDAO<Document> {

    public DocumentDAO(SessionFactory sessionFactory) {
        super(sessionFactory);
    }

    public List<Document> getAllDocuments() {
        return list(criteria());
    }

    public Document getDocumentById(int documentId) {
        return get(documentId);
    }

    public Document createDocument(Document document) {
        currentSession().save(document);
        currentSession().getTransaction().commit();
        return document;
    }

    public void deleteDocument(int documentId) {
        Document document = getDocumentById(documentId);
        currentSession().delete(document);
    }

    public void updateDocument(Document document) {
        Document documentToBeUpdated = getDocumentById(document.getDocumentId());
        documentToBeUpdated.setUserId(document.getUserId());
        documentToBeUpdated.setDocName(document.getDocName());
        documentToBeUpdated.setDocHeader(document.getDocHeader());
        documentToBeUpdated.setDocContent(document.getDocContent());
        documentToBeUpdated.setDocNotes(document.getDocNotes());
        persist(documentToBeUpdated);
    }

    public List<Document> getDocumentsByUserId(int userId) {
        SQLQuery query = currentSession().createSQLQuery("select * from documententity where userId = :userId");
        query.addEntity(Document.class);
        query.setParameter("userId", userId);
        List documents = query.list();
        return documents;
    }

    public List<Integer> getDocIdCorrespondingToUserId(int userId) {
        SQLQuery query = currentSession().createSQLQuery("select documentId from documententity where userId = :userId");
        query.setParameter("userId", userId);
        List documentIds = query.list();
        return documentIds;
    }

    public int getUserIdOfDocument(int documentId) {
        SQLQuery query = currentSession().createSQLQuery("select userId from documententity where documentId = :documentId");
        query.setParameter("documentId", documentId);
        List userIds = query.list();
        return (int) userIds.get(0);
    }

    public List<Integer> getAllDocumentIds() {
        SQLQuery query = currentSession().createSQLQuery("select documentId from documententity");
        List docIds = query.list();
        if(docIds.size() == 0)
            return null;

        return docIds;
    }
}

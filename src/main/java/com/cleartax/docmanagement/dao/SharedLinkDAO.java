package com.cleartax.docmanagement.dao;

import com.cleartax.docmanagement.domain.SharedLink;
import io.dropwizard.hibernate.AbstractDAO;
import org.hibernate.SQLQuery;
import org.hibernate.SessionFactory;

import java.util.List;

public class SharedLinkDAO extends AbstractDAO<SharedLink> {

    public SharedLinkDAO(SessionFactory sessionFactory) {
        super(sessionFactory);
    }

    public List<SharedLink> getAllSharedLinks() {
        return list(criteria());
    }

    public SharedLink getSharedLinkById(int sharedLinkId) {
        return get(sharedLinkId);
    }

    public SharedLink getSharedLinkByDocumentId(int documentId) {
        SQLQuery query = currentSession().createSQLQuery("select * from sharedlink where documentId = :documentId");
        query.addEntity(SharedLink.class);
        query.setParameter("documentId", documentId);
        List sharedLinks = query.list();

        if(sharedLinks.size() == 0)
            return null;

        Object sharedLink = sharedLinks.get(0);
        return (SharedLink) sharedLink;
    }

    public SharedLink createSharedLink(SharedLink sharedLink) {
        currentSession().save(sharedLink);
        currentSession().getTransaction().commit();
        return sharedLink;
    }

    public void deleteSharedLink(int sharedLinkId) {
        SharedLink sharedLink = getSharedLinkById(sharedLinkId);
        currentSession().delete(sharedLink);
    }

    public void updateSharedLink(SharedLink sharedLink) {
        SharedLink sharedLinkToBeUpdated = getSharedLinkById(sharedLink.getSharedLinkId());
        sharedLinkToBeUpdated.setSharedLinkId(sharedLink.getSharedLinkId());
        sharedLink.setSharedLink(sharedLink.getSharedLink());
        sharedLinkToBeUpdated.setDocumentId(sharedLink.getDocumentId());
        persist(sharedLinkToBeUpdated);
    }
}

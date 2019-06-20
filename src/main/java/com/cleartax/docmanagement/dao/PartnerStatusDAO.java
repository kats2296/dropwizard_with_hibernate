package com.cleartax.docmanagement.dao;

import com.cleartax.docmanagement.domain.PartnerStatus;
import io.dropwizard.hibernate.AbstractDAO;
import org.hibernate.SQLQuery;
import org.hibernate.SessionFactory;

import java.util.List;

public class PartnerStatusDAO extends AbstractDAO<PartnerStatus> {

    public PartnerStatusDAO(SessionFactory sessionFactory) {
        super(sessionFactory);
    }

    public List<PartnerStatus> getActivePartners(String status) {
        SQLQuery query = currentSession().createSQLQuery("select * from partnerstatus where status = :status");
        query.addEntity(PartnerStatus.class);
        query.setParameter("status", status);
        List activePartners = query.list();
        if(activePartners.size()==0)
            return null;

        return activePartners;
    }

    public  String getPartnerStatus(int partnerId, int documentId) {
        SQLQuery query = currentSession().createSQLQuery("select status from partnerstatus where partnerId = :partnerId and documentId = :documentId");
        query.setParameter("partnerId", partnerId);
        query.setParameter("documentId", documentId);
        List s = query.list();

        if(s.size() == 0)
            return null;

        Object status = s.get(0);
        return (String) status;
    }
}
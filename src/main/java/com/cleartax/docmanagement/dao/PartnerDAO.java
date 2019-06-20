package com.cleartax.docmanagement.dao;

import com.cleartax.docmanagement.domain.Partner;
import io.dropwizard.hibernate.AbstractDAO;
import org.hibernate.SQLQuery;
import org.hibernate.SessionFactory;

import java.util.List;

public class PartnerDAO extends AbstractDAO<Partner> {

    public PartnerDAO(SessionFactory sessionFactory) {
        super(sessionFactory);
    }

    public List<Partner> getAllPartners() {
        return list(criteria());
    }

    public Partner getPartnerById(int partnerId) {
        return get(partnerId);
    }

    public List<Partner> getPartnerByUserId(int userId) {
        SQLQuery query = currentSession().createSQLQuery("select * from partner where userId = :userId");
        query.addEntity(Partner.class);
        query.setParameter("userId", userId);
        List partners = query.list();
        System.out.println("****" + partners);
        return partners;
    }

    public Partner createPartner(Partner partner) {
        currentSession().save(partner);
        currentSession().getTransaction().commit();
        return partner;
    }

    public void updatePartner(Partner partner) {
        Partner partnerToBeUpdated = getPartnerById(partner.getPartnerId());
        partnerToBeUpdated.setPartnerId(partner.getPartnerId());
        partnerToBeUpdated.setPartnerName(partner.getPartnerName());
        partnerToBeUpdated.setPartnerEmail(partner.getPartnerEmail());
        partnerToBeUpdated.setUserId(partner.getUserId());
        persist(partnerToBeUpdated);
    }

    public void deletePartner(int partnerId) {
        Partner partner = getPartnerById(partnerId);
        currentSession().delete(partner);
    }

    public List<Integer> getPartnerIdsByUserId(int userId) {
        SQLQuery query = currentSession().createSQLQuery("select partnerId from partner where userId = :userId");
        query.setParameter("userId", userId);
        List partners = query.list();
        return partners;
    }

}

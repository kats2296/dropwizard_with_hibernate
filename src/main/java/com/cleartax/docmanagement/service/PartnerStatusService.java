package com.cleartax.docmanagement.service;

import com.cleartax.docmanagement.dao.PartnerStatusDAO;

public class PartnerStatusService {

    private PartnerStatusDAO partnerStatusDAO;

    public PartnerStatusService(PartnerStatusDAO partnerStatusDAO) {
        this.partnerStatusDAO = partnerStatusDAO;
    }

    public int getPartnerStatus(int partnerId, int documentId) {
        Object status = partnerStatusDAO.getPartnerStatus(partnerId, documentId);
        if(status == null)
            return 0;
        if(status.toString().equalsIgnoreCase("A"))
            return 1;
        else
            return 0;
    }
}

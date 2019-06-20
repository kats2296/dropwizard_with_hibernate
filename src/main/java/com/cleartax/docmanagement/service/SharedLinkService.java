package com.cleartax.docmanagement.service;

import com.cleartax.docmanagement.dao.PartnerDAO;
import com.cleartax.docmanagement.dao.SharedLinkDAO;
import com.cleartax.docmanagement.domain.Partner;
import com.cleartax.docmanagement.domain.SharedLink;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class SharedLinkService {

    private static final Logger logger = LoggerFactory.getLogger(UserService.class);

    private static final String DATABASE_ACCESS_ERROR =
            "Could not reach the MySQL database. The database may be down or there may be network connectivity issues. Details: ";
    private static final String DATABASE_CONNECTION_ERROR =
            "Could not create a connection to the MySQL database. The database configurations are likely incorrect. Details: ";
    private static final String UNEXPECTED_DATABASE_ERROR =
            "Unexpected error occurred while attempting to reach the database. Details: ";
    private static final String SUCCESS = "Success";
    private static final String UNEXPECTED_DELETE_ERROR = "An unexpected error occurred while deleting document.";
    private static final String DOCUMENT_NOT_FOUND = "Document id %s not found.";
    private static final String LINK_NOT_FOUND = "Shared link id %s not found.";
    private static final String PARTNER_NOT_FOUND = "Partner id %s not found.";

    private SharedLinkDAO sharedLinkDAO;
    private PartnerDAO partnerDAO;

    public SharedLinkService(SharedLinkDAO sharedLinkDAO, PartnerDAO partnerDAO) {
        this.sharedLinkDAO = sharedLinkDAO;
        this.partnerDAO = partnerDAO;
    }

    public int checkIfPartnerExist(final int partnerId) {
        Partner partner = partnerDAO.getPartnerById(partnerId);
        if(partner == null) {
            return 0;
        }
        else {
            return 1;
        }
    }

    public int documentHasSharedLink(int documentId) {
        Object sharedLinkByDocId = sharedLinkDAO.getSharedLinkByDocumentId(documentId);
        if(sharedLinkByDocId == null) {
            return 0;
        }

        else
            return 1;
    }

    public SharedLink addSharedLinktoDb(SharedLink sharedLink) {
        sharedLinkDAO.createSharedLink(sharedLink);
        return sharedLinkDAO.getSharedLinkByDocumentId(sharedLink.getSharedLinkId());
    }

}

package com.cleartax.docmanagement.service;

import com.cleartax.docmanagement.dao.PartnerDAO;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;

public class PartnerService {

    private static final Logger logger = LoggerFactory.getLogger(UserService.class);

    private static final String DATABASE_ACCESS_ERROR =
            "Could not reach the MySQL database. The database may be down or there may be network connectivity issues. Details: ";
    private static final String DATABASE_CONNECTION_ERROR =
            "Could not create a connection to the MySQL database. The database configurations are likely incorrect. Details: ";
    private static final String UNEXPECTED_DATABASE_ERROR =
            "Unexpected error occurred while attempting to reach the database. Details: ";
    private static final String SUCCESS = "Success";
    private static final String UNEXPECTED_DELETE_ERROR = "An unexpected error occurred while deleting user.";
    private static final String USER_NOT_FOUND = "User id %s not found.";
    private static final String PARTNER_NOT_FOUND = "Partner id %s not found.";

    private PartnerDAO partnerDAO;

    public PartnerService(PartnerDAO partnerDAO) {
        this.partnerDAO = partnerDAO;
    }

    public List<Integer> getPartnerIdsByUserId(int userId) {
        return partnerDAO.getPartnerIdsByUserId(userId);
    }
}

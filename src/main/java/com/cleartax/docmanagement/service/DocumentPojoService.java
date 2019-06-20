package com.cleartax.docmanagement.service;

import com.cleartax.docmanagement.dao.DocumentDAO;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.ws.rs.WebApplicationException;
import javax.ws.rs.core.Response;
import java.util.List;

public class DocumentPojoService {

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

    private DocumentDAO documentDAO;

    public DocumentPojoService(DocumentDAO documentDAO) {
        this.documentDAO = documentDAO;
    }

    public int checkIfDocumentExist(final int documentId) {
        Object allDocumentIds = documentDAO.getAllDocumentIds();
        if (allDocumentIds == null) {
            return 0;
        }

        else {
            List<Integer> allDocIds = (List<Integer>) allDocumentIds;

            if(allDocIds.contains(documentId)) {
                return 1;
            }

            else {
                throw new WebApplicationException(String.format(DOCUMENT_NOT_FOUND, documentId), Response.Status.NOT_FOUND);
            }
        }

    }

    public int getUserIdOfDocument(int documentId) {
        int userId = documentDAO.getUserIdOfDocument(documentId);
        return userId;
    }

    public int checkIfUserCreatedDoc(int userId, int documentId) {
        List<Integer> docIdCorrespondingToUserId = documentDAO.getDocIdCorrespondingToUserId(userId);
        boolean contains = docIdCorrespondingToUserId.contains(documentId);
        if(!contains)
            return 0;
        else
            return 1;
    }
}




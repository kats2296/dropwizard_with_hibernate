package com.cleartax.docmanagement.controller;

import com.cleartax.docmanagement.dao.DocumentDAO;
import com.cleartax.docmanagement.dao.PartnerDAO;
import com.cleartax.docmanagement.dao.PartnerStatusDAO;
import com.cleartax.docmanagement.domain.Document;
import com.cleartax.docmanagement.requests.AddNotesRequest;
import com.codahale.metrics.annotation.Timed;
import io.dropwizard.hibernate.UnitOfWork;

import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

@Path("/v1.0/document/")
@Produces(MediaType.APPLICATION_JSON)
public class PartnerStatusController {

    private PartnerStatusDAO partnerStatusDAO;
    private DocumentDAO documentDAO;
    private PartnerDAO partnerDAO;

    public PartnerStatusController(PartnerStatusDAO partnerStatusDAO, DocumentDAO documentDAO, PartnerDAO partnerDAO) {
        this.partnerStatusDAO = partnerStatusDAO;
        this.documentDAO = documentDAO;
        this.partnerDAO = partnerDAO;
    }

    @POST
    @Timed
    @Path("addNotes")
    @Produces(MediaType.APPLICATION_JSON)
    @UnitOfWork
    public Response addNotestoDocument(final AddNotesRequest addNotesRequest) {
        Document documentByDocId = documentDAO.getDocumentById(addNotesRequest.getDocumentId());
        if(documentByDocId == null) {
            System.out.println("This document doesn't exist anymore");
            return Response.status(Response.Status.BAD_REQUEST).build();
        }

        else {
           Object partner = partnerDAO.getPartnerById(addNotesRequest.getPartnerId());
           if(partner != null) {
               String partnerStatus = partnerStatusDAO.getPartnerStatus(addNotesRequest.getPartnerId(), addNotesRequest.getDocumentId());
               if(partnerStatus.equals("A")) {
                   Document document = documentDAO.getDocumentById(addNotesRequest.getDocumentId());

                   String noteAlreadyPresent = document.getDocNotes();
                   String notesAdded = null;
                   if(noteAlreadyPresent != null)
                       notesAdded = noteAlreadyPresent.concat("<br>").concat(addNotesRequest.getNotes());
                   else
                       notesAdded = addNotesRequest.getNotes();

                   Document documentUpdated = new Document(document.getDocumentId(), document.getUserId(),
                           document.getUserLoggedIn(), document.getDocName(), document.getDocHeader(),
                           document.getDocHeader(), notesAdded);
                   documentDAO.updateDocument(documentUpdated);
                   return Response.ok(documentDAO.getDocumentById(addNotesRequest.getDocumentId())).build();
               }

               else {
                   System.out.println("You are not an accessible user for this link");
                   return Response.status(Response.Status.BAD_REQUEST).build();
               }
           }

           else {
               System.out.println("You are no longer a partner for this link");
               return Response.status(Response.Status.NOT_FOUND).build();
           }
        }

    }
}

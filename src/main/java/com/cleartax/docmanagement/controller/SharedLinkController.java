package com.cleartax.docmanagement.controller;

import com.cleartax.docmanagement.dao.*;
import com.cleartax.docmanagement.domain.SharedLink;
import com.cleartax.docmanagement.requests.SendMailRequest;
import com.cleartax.docmanagement.service.*;
import com.codahale.metrics.annotation.Timed;
import io.dropwizard.hibernate.UnitOfWork;

import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.util.Iterator;
import java.util.List;

@Path("/v1.0/sharedLink")
@Produces(MediaType.APPLICATION_JSON)
public class SharedLinkController {

    private SharedLinkDAO sharedLinkDAO;
    private PartnerDAO partnerDAO;
    private DocumentDAO documentDAO;
    private PartnerStatusDAO partnerStatusDAO;
    private UserDAO userDAO;

    public SharedLinkController(SharedLinkDAO sharedLinkDAO, PartnerDAO partnerDAO, DocumentDAO documentDAO,
                                PartnerStatusDAO partnerStatusDAO, UserDAO userDAO) {
        this.sharedLinkDAO = sharedLinkDAO;
        this.partnerDAO = partnerDAO;
        this.documentDAO = documentDAO;
        this.partnerStatusDAO = partnerStatusDAO;
        this.userDAO = userDAO;
    }

    private DocumentPojoService documentPojoService;
    private SharedLinkService sharedLinkService;
    private PartnerService partnerService;
    private PartnerStatusService partnerStatusService;
    private UserService userService;

    @POST
    @Timed
    @Path("/sendMailtoPartners")
    @Produces(MediaType.APPLICATION_JSON)
    @UnitOfWork
    public Response sendMail(final SendMailRequest sendMailRequest) {

         documentPojoService = new DocumentPojoService(documentDAO);
         sharedLinkService = new SharedLinkService(sharedLinkDAO, partnerDAO);
         partnerService = new PartnerService(partnerDAO);
         partnerStatusService = new PartnerStatusService(partnerStatusDAO);
         userService = new UserService(userDAO);

        int documentId = sendMailRequest.getDocumentId();
        int documentExist = documentPojoService.checkIfDocumentExist(documentId);

        if(documentExist == 1) {
            int hasSharedLink = sharedLinkService.documentHasSharedLink(documentId);
           if(hasSharedLink == 0) {
               List<Integer> partnerIds = sendMailRequest.getPartnerIds();
               Iterator<Integer> iterator = partnerIds.iterator();

               while (iterator.hasNext()) {
                   Integer partnerId = iterator.next();
                   int partnerExist = sharedLinkService.checkIfPartnerExist(partnerId);
                   if(partnerExist == 1) {

                   }

                   else {
                       System.out.println("Partner id " + partnerId + "doesn't exist. Change the partners list and try again");
                       return Response.status(Response.Status.NOT_FOUND).build();
                   }
               }


               List<Integer> partnerIds1 = sendMailRequest.getPartnerIds();
               Iterator<Integer> iterator1 = partnerIds.iterator();

               while (iterator1.hasNext()) {
                   Integer partnerId = iterator1.next();
                   int userId = documentPojoService.getUserIdOfDocument(sendMailRequest.getDocumentId());
                   List<Integer> partners = partnerService.getPartnerIdsByUserId(userId);
                   boolean contains = partners.contains(partnerId);
                   if(!contains) {
                       System.out.println("Sorry partnerId " + partnerId + "is not your partner. You cannot send mail to this partner");
                       return Response.status(Response.Status.BAD_REQUEST).build();
                   }
               }

               String documentWebLink = "http://localhost:4000/v1.0/document/link/" + documentId;
               SharedLink sharedLink = new SharedLink(documentId, documentWebLink, documentId);
               SharedLink addedLink = sharedLinkService.addSharedLinktoDb(sharedLink);
               System.out.println("Successfully added shared link entry to the database");
               return Response.ok().build();
           }

           else {
               System.out.println("A shared link for this document already exists");
               return Response.status(Response.Status.BAD_REQUEST).build();
           }
        }

        else {
            System.out.println("Document id " + documentId + "doesn't exist.");
            return Response.status(Response.Status.NOT_FOUND).build();
        }
    }

   @GET
   @Timed
   @Path("link/{documentId}/{partnerId}")
   @Produces(MediaType.TEXT_HTML)
   @UnitOfWork
   public String accessDocumentUsingLink(@PathParam("documentId") int documentId, @PathParam("partnerId") int partnerId) {

       documentPojoService = new DocumentPojoService(documentDAO);
       partnerStatusService = new PartnerStatusService(partnerStatusDAO);

        int docExists = documentPojoService.checkIfDocumentExist(documentId);
        if(docExists == 0) {
            System.out.println("Document id " + documentId + "doesn't exist.");
            return Response.status(Response.Status.NOT_FOUND).build().toString();
        }

        else {
            int partnerStatus = partnerStatusService.getPartnerStatus(partnerId, documentId);
            if(partnerStatus==1) {
                System.out.println("You can access the document");
                DocumentRestController documentRestController = new DocumentRestController(documentDAO, userDAO);
                return documentRestController.createHtmlDoc(documentId);
            }

            else {
                return Response.Status.BAD_REQUEST.toString() + "You are no longer an accessible user for this link";
            }
        }
    }
}

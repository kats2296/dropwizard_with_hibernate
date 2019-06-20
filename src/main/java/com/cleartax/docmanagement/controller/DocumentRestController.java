package com.cleartax.docmanagement.controller;

import com.cleartax.docmanagement.Response.CreatedDocumentResponse;
import com.cleartax.docmanagement.dao.DocumentDAO;
import com.cleartax.docmanagement.dao.UserDAO;
import com.cleartax.docmanagement.domain.Document;
import com.cleartax.docmanagement.domain.DocumentPojo;
import com.codahale.metrics.annotation.Timed;
import io.dropwizard.hibernate.UnitOfWork;

import javax.validation.Valid;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.io.*;
import java.net.MalformedURLException;
import java.nio.charset.Charset;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Path("/v1.0/document")
@Produces(MediaType.APPLICATION_JSON)
public class DocumentRestController {

    private DocumentDAO documentDAO;
    private UserDAO userDAO;

    public DocumentRestController(DocumentDAO documentDAO, UserDAO userDAO) {
        this.documentDAO = documentDAO;
        this.userDAO = userDAO;
    }

    @POST
    @Path("/create")
    @Produces({MediaType.TEXT_HTML, MediaType.APPLICATION_JSON})
    @UnitOfWork
    public Response createDocument(final DocumentPojo documentPojo) {

        int userId = documentPojo.getUserId();

        if(userDAO.getUserById(userId) == null) {
            System.out.println("You are not a user in our database. Please sign up first");
            return Response.status(Response.Status.NOT_FOUND).build();
        }

        if (documentPojo.getUserLoggedIn() == 0) {
            System.out.println("The user is not logged in. Please login to proceed further");
            return Response.status(Response.Status.BAD_REQUEST).build();
        }


        Document documentCreated = new Document(documentPojo.getDocumentId(), documentPojo.getUserId(),
                documentPojo.getUserLoggedIn(), documentPojo.getDocName(), documentPojo.getDocHeader(),
                documentPojo.getDocContent(), documentPojo.getDocNotes());

        documentDAO.createDocument(documentCreated);
        String docCreated = createHtmlDoc(documentPojo.getDocumentId());

        try {

            InputStream inputStream = new ByteArrayInputStream(docCreated.getBytes(Charset.forName("UTF-8")));

            BufferedReader readr =
                    new BufferedReader(new InputStreamReader(inputStream));

            BufferedWriter writer =
                    new BufferedWriter(new FileWriter(documentCreated.getDocName() + ".html"));

            String line;
            while ((line = readr.readLine()) != null) {
                writer.write(line);
            }

            readr.close();
            writer.close();
            System.out.println("Successfully Downloaded.");
        } catch (MalformedURLException mue) {
            System.out.println("Malformed URL Exception raised");
            return Response.ok("Malformed URL Exception raised").build();
        } catch (IOException ie) {
            System.out.println("IOException raised : " + ie);
            return Response.ok("IO Exception Exception raised").build();

        }

        String documentName = documentCreated.getDocName();
        CreatedDocumentResponse createdDocumentResponse = new CreatedDocumentResponse(documentCreated.getDocumentId(), documentCreated.getUserId(), documentName + ".html", "/home/khyatisehgal/IdeaProjects/docmanagement/" + documentName + ".html");
        return Response.ok(createdDocumentResponse).build();
    }


    @GET
    @Timed
    @Produces(MediaType.TEXT_HTML)
    @Path("/displayDocument/{documentId}/{userId}")
    @UnitOfWork
    public String displayDocUsingLink(@PathParam("documentId") final int documentId, @PathParam("userId") final int userId) {
        //check if the document belongs to the same user
        int ifUserCreatedDoc = checkIfUserCreatedDoc(userId, documentId);
        System.out.println("****" + ifUserCreatedDoc );
        if(ifUserCreatedDoc == 1) {
            return createHtmlDoc(documentId);
        }

        else {
            System.out.println("Sorry you don't have access rights for this document");
            return Response.Status.BAD_REQUEST.toString() + " Sorry you don't have access rights for this document";
        }
    }

    public int checkIfUserCreatedDoc(int userId, int documentId) {
        List<Integer> docIdCorrespondingToUserId = documentDAO.getDocIdCorrespondingToUserId(userId);
        System.out.println("&&&&&" + docIdCorrespondingToUserId);
        boolean contains = docIdCorrespondingToUserId.contains(documentId);
        if(!contains)
            return 0;
        else
            return 1;
    }

    public String createHtmlDoc(int documentId) {
        Document documentByDocId = documentDAO.getDocumentById(documentId);
        return "<!DOCTYPE html>" + "<html>" + "<head>" + documentByDocId.getDocHeader() + "<title>" +  "User Management Page" + "</title>" + "</head>" + "<body>" +
                "<br>" + "<br>" + "<br>" + "<br>" + "<br>" +
                "<h2>" + "CONTENT SECTION" + "</h2>" + "<p>" + documentByDocId.getDocContent() +"</p>" +
                "<br>" + "<br>" + "<br>" + "<br>" + "<br>" +
                "<br>" + "<br>" + "<br>" + "<br>" + "<br>" +
                "<br>" + "<br>" + "<br>" + "<br>" + "<br>" +
                "<br>" + "<br>" + "<br>" + "<br>" + "<br>" +
                "<br>" + "<br>" + "<br>" + "<br>" + "<br>" +
                "<h2>" + "NOTES SECTION"  + "</h2>" + "<p>" + documentByDocId.getDocNotes() + "</p>" +
                "</body>" + "</html>";
    }



    @GET
    @Timed
    @Produces(MediaType.TEXT_HTML)
    @Path("/link/{documentId}")
    @UnitOfWork
    public String displayDocUsingLink(@PathParam("documentId") final int documentId) {
        Document documentByDocId = documentDAO.getDocumentById(documentId);
        return createHtmlDoc(documentId);
    }

    @GET
    @Timed
    @UnitOfWork
    public Response getAllDocuments() {
        return Response.ok(documentDAO.getAllDocuments()).build();
    }

    @GET
    @Timed
    @Path("/{documentId}")
    @UnitOfWork
    public Response getDocumentById(@PathParam("documentId") @Valid final int documentId) {
        return Response.ok(documentDAO.getDocumentById(documentId)).build();
    }

    @GET
    @Timed
    @Path("/by/{userId}")
    @UnitOfWork
    public Response getDocumentByUserId(@PathParam("userId") @Valid final int userId) {
        return Response.ok(documentDAO.getDocumentsByUserId(userId)).build();
    }

    @PUT
    @Timed
    @Path("/{documentId}")
    @UnitOfWork
    public Response updateDocument(final DocumentPojo documentPojo,
                                   @PathParam("documentId")@Valid final int documentId) {
        documentPojo.setDocumentId(documentId);

        String docCreated = createHtmlDoc(documentId);
        Document documentCreated = new Document(documentPojo.getDocumentId(), documentPojo.getUserId(),
                documentPojo.getUserLoggedIn(), documentPojo.getDocName(), documentPojo.getDocHeader(),
                documentPojo.getDocContent(), documentPojo.getDocNotes());

        documentDAO.updateDocument(documentCreated);
        return Response.ok(documentDAO.getDocumentById(documentId)).build();
    }

    @DELETE
    @Timed
    @Path("/{documentId}")
    @UnitOfWork
    public Response deleteDocument(@PathParam("documentId") @Valid final int documentId) {
        Map<String, String> response = new HashMap<>();
        documentDAO.deleteDocument(documentId);
        Object document = documentDAO.getDocumentById(documentId);
        if(document == null)
            response.put("status", "Document is deleted");

        else
            response.put("status", "Error in deleting the document");

        return Response.ok(response).build();
    }
}

package com.cleartax.docmanagement.controller;

import com.cleartax.docmanagement.dao.PartnerDAO;
import com.cleartax.docmanagement.domain.Partner;
import com.codahale.metrics.annotation.Timed;
import io.dropwizard.hibernate.UnitOfWork;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.util.HashMap;
import java.util.Map;

@Path("/v1.0/partner")
@Produces(MediaType.APPLICATION_JSON)
public class PartnerRestController {

    private PartnerDAO partnerDAO;

    public PartnerRestController(PartnerDAO partnerDAO) {
        this.partnerDAO = partnerDAO;
    }

    @GET
    @Timed
    @UnitOfWork
    public Response getAllPartners() {
        return Response.ok(partnerDAO.getAllPartners()).build();
    }

    @GET
    @Timed
    @Path("/{partnerId}")
    @UnitOfWork
    public Response getPartnerById(@PathParam("partnerId") @Valid final int partnerId) {
        return Response.ok(partnerDAO.getPartnerById(partnerId)).build();
    }

    @GET
    @Timed
    @Path("/user/{userId}")
    @UnitOfWork
    public Response getPartnersByUserId(@PathParam("userId") int userId) {
        System.out.println("inside");
      return Response.ok(partnerDAO.getPartnerByUserId(userId)).build();
    }

    @POST
    @Timed
    @UnitOfWork
    public Response createPartner(@NotNull @Valid final Partner partner) {
        Partner createdPartner = new Partner(partner.getPartnerId(), partner.getUserId(), partner.getPartnerName(), partner.getPartnerEmail());
        return Response.ok(partnerDAO.createPartner(createdPartner)).build();
    }

    @PUT
    @Timed
    @Path("/{partnerId}")
    @UnitOfWork
    public Response updateUser(@NotNull @Valid final Partner partner,
                               @PathParam("partnerId") final int partnerId) {
        partner.setPartnerId(partnerId);
        partnerDAO.updatePartner(partner);
        return Response.ok(partnerDAO.getPartnerById(partnerId)).build();
    }

    @DELETE
    @Timed
    @Path("/{partnerId}")
    @UnitOfWork
    public Response deletePartner(@PathParam("partnerId") @Valid final int partnerId) {
        Map<String, String> response = new HashMap<>();
        partnerDAO.deletePartner(partnerId);
        Object partner = partnerDAO.getPartnerById(partnerId);
        if(partner == null)
            response.put("status", "User is deleted");

        else
            response.put("status", "Error in deleting the user");

        return Response.ok(response).build();
    }
}


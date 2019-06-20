package com.cleartax.docmanagement.domain;

import org.hibernate.validator.constraints.NotBlank;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;

@Table
@Entity(name = "partnerstatus")
public class PartnerStatus {

    @Id
    private int sharedLinkId;

    @NotNull
    @NotBlank
    private int documentId;

    @NotNull
    @NotBlank
    private int partnerId;

    @NotNull
    @NotBlank
    private String status;

    public PartnerStatus() {
        super();
    }


    public PartnerStatus(int sharedLinkId, int documentId, int partnerId, String status) {
        this.sharedLinkId = sharedLinkId;
        this.documentId = documentId;
        this.partnerId = partnerId;
        this.status = status;
    }

    public int getDocumentId() {
        return documentId;
    }

    public void setDocumentId(int documentId) {
        this.documentId = documentId;
    }

    public int getSharedLinkId() {
        return sharedLinkId;
    }

    public void setSharedLinkId(int sharedLinkId) {
        this.sharedLinkId = sharedLinkId;
    }

    public int getPartnerId() {
        return partnerId;
    }

    public void setPartnerId(int partnerId) {
        this.partnerId = partnerId;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    @Override
    public String toString() {
        return "PartnerStatus{" +
                "documentId=" + documentId +
                ", sharedLinkId=" + sharedLinkId +
                ", partnerId=" + partnerId +
                ", status='" + status + '\'' +
                '}';
    }
}

package com.cleartax.docmanagement.requests;

import javax.validation.constraints.NotNull;
import java.util.List;

public class SendMailRequest {

    @NotNull
    private int documentId;
    @NotNull
    private List<Integer> partnerIds;

    public SendMailRequest() {
        super();
    }

    public SendMailRequest(int documentId, List<Integer> partnerIds) {
        this.documentId = documentId;
        this.partnerIds = partnerIds;
    }

    public int getDocumentId() {
        return documentId;
    }

    public void setDocumentId(int documentId) {
        this.documentId = documentId;
    }

    public List<Integer> getPartnerIds() {
        return partnerIds;
    }

    public void setPartnerIds(List<Integer> partnerIds) {
        this.partnerIds = partnerIds;
    }

    @Override
    public String toString() {
        return "SendMailRequest{" +
                "documentId=" + documentId +
                ", partnerIds=" + partnerIds +
                '}';
    }
}

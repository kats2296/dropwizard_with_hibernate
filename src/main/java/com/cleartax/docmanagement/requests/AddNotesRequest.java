package com.cleartax.docmanagement.requests;

public class AddNotesRequest {

    private int documentId;
    private int partnerId;
    private String notes;

    public AddNotesRequest() {
        super();
    }

    public AddNotesRequest(int documentId, int partnerId, String notes) {
        this.documentId = documentId;
        this.partnerId = partnerId;
        this.notes = notes;
    }

    public int getDocumentId() {
        return documentId;
    }

    public void setDocumentId(int documentId) {
        this.documentId = documentId;
    }

    public int getPartnerId() {
        return partnerId;
    }

    public void setPartnerId(int partnerId) {
        this.partnerId = partnerId;
    }

    public String getNotes() {
        return notes;
    }

    public void setNotes(String notes) {
        this.notes = notes;
    }
}

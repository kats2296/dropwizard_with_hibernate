package com.cleartax.docmanagement.Response;

public class CreatedDocumentResponse {

    private int documentId;
    private int userId;
    private String documentName;
    private String downloadedLocation;

    public CreatedDocumentResponse(int documentId, int userId, String documentName, String downloadedLocation) {
        this.documentId = documentId;
        this.userId = userId;
        this.documentName = documentName;
        this.downloadedLocation = downloadedLocation;
    }

    public int getDocumentId() {
        return documentId;
    }

    public void setDocumentId(int documentId) {
        this.documentId = documentId;
    }

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public String getDocumentName() {
        return documentName;
    }

    public void setDocumentName(String documentName) {
        this.documentName = documentName;
    }

    public String getDownloadedLocation() {
        return downloadedLocation;
    }

    public void setDownloadedLocation(String downloadedLocation) {
        this.downloadedLocation = downloadedLocation;
    }

    @Override
    public String toString() {
        return "CreatedDocumentResponse{" +
                "documentId=" + documentId +
                ", userId=" + userId +
                ", documentName='" + documentName + '\'' +
                ", downloadedLocation='" + downloadedLocation + '\'' +
                '}';
    }
}

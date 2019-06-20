package com.cleartax.docmanagement.domain;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;

@Table
@Entity(name = "sharedlink")
public class SharedLink {

    @Id
    private int sharedLinkId;

    @NotNull
    private String sharedLink;

    @NotNull
    private int documentId;

    public SharedLink() {
        super();
    }

    public SharedLink(int sharedLinkId, String sharedLink, int documentId) {
        this.sharedLinkId = sharedLinkId;
        this.sharedLink = sharedLink;
        this.documentId = documentId;
    }

    public int getSharedLinkId() {
        return sharedLinkId;
    }

    public void setSharedLinkId(int sharedLinkId) {
        this.sharedLinkId = sharedLinkId;
    }

    public String getSharedLink() {
        return sharedLink;
    }

    public void setSharedLink(String sharedLink) {
        this.sharedLink = sharedLink;
    }

    public int getDocumentId() {
        return documentId;
    }

    public void setDocumentId(int documentId) {
        this.documentId = documentId;
    }

    @Override
    public String toString() {
        return "SharedLink{" +
                "sharedLinkId=" + sharedLinkId +
                ", sharedLink=" + sharedLink +
                ", documentId=" + documentId +
                '}';
    }
}

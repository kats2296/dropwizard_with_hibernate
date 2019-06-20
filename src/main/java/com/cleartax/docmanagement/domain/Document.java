package com.cleartax.docmanagement.domain;

import org.hibernate.validator.constraints.NotBlank;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;

@Table
@Entity(name = "documententity")
public class Document {

    @Id
    private int documentId;

    @NotNull
    private int userId;

    @NotNull
    private int userLoggedIn;

    @NotNull
    private String docName;

    @NotNull
    private String docHeader;

    @NotNull
    private String docContent;

    @NotNull
    private String docNotes;

    public Document() {
        super();
    }

    public Document(int documentId, int userId, int userLoggedIn, String docName, String docHeader, String docContent, String docNotes) {
        this.documentId = documentId;
        this.userId = userId;
        this.userLoggedIn = userLoggedIn;
        this.docName = docName;
        this.docHeader = docHeader;
        this.docContent = docContent;
        this.docNotes = docNotes;
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

    public int getUserLoggedIn() {
        return userLoggedIn;
    }

    public void setUserLoggedIn(int userLoggedIn) {
        this.userLoggedIn = userLoggedIn;
    }

    public String getDocName() {
        return docName;
    }

    public void setDocName(String docName) {
        this.docName = docName;
    }

    public String getDocHeader() {
        return docHeader;
    }

    public void setDocHeader(String docHeader) {
        this.docHeader = docHeader;
    }

    public String getDocContent() {
        return docContent;
    }

    public void setDocContent(String docContent) {
        this.docContent = docContent;
    }

    public String getDocNotes() {
        return docNotes;
    }

    public void setDocNotes(String docNotes) {
        this.docNotes = docNotes;
    }

    @Override
    public String toString() {
        return "Document{" +
                "documentId=" + documentId +
                ", userId=" + userId +
                ", userLoggedIn=" + userLoggedIn +
                ", docName='" + docName + '\'' +
                ", docHeader='" + docHeader + '\'' +
                ", docContent='" + docContent + '\'' +
                ", docNotes='" + docNotes + '\'' +
                '}';
    }
}

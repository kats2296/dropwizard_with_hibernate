package com.cleartax.docmanagement.domain;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;

@Table
@Entity(name = "partner")
public class Partner {

    @Id
    private int partnerId;

    @NotNull
    private int userId;

    @NotNull
    private String partnerName;

    @NotNull
    private String partnerEmail;

    public Partner() {
        super();
    }

    public Partner(int partnerId, int userId, String partnerName, String partnerEmail) {
        this.partnerId = partnerId;
        this.userId = userId;
        this.partnerName = partnerName;
        this.partnerEmail = partnerEmail;
    }

    public int getPartnerId() {
        return partnerId;
    }

    public void setPartnerId(int partnerId) {
        this.partnerId = partnerId;
    }

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public String getPartnerName() {
        return partnerName;
    }

    public void setPartnerName(String partnerName) {
        this.partnerName = partnerName;
    }

    public String getPartnerEmail() {
        return partnerEmail;
    }

    public void setPartnerEmail(String partnerEmail) {
        this.partnerEmail = partnerEmail;
    }

    @Override
    public String toString() {
        return "Partner{" +
                "partnerId=" + partnerId +
                ", userId=" + userId +
                ", partnerName='" + partnerName + '\'' +
                ", partnerEmail='" + partnerEmail + '\'' +
                '}';
    }
}

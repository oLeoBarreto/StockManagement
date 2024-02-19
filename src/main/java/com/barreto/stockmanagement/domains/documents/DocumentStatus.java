package com.barreto.stockmanagement.domains.documents;

public enum DocumentStatus {
    WAITING("waiting"),
    COMPLETED("completed");

    private String status;

    DocumentStatus(String status) {
        this.status = status;
    }

    public String getStatus() {
        return status;
    }
}

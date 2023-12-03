package com.spm.financeapp.Enums;

public enum TransactionType {
    GELİR,
    GİDER;

    public String getType() {
        return this.name();
    }
}

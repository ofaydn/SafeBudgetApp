package com.spm.financeapp.Enums;

public enum TransactionType {
    INCOME,
    EXPENSE;

    public String getType() {
        return this.name();
    }
}

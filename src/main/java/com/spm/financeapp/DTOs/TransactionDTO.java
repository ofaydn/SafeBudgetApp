package com.spm.financeapp.DTOs;

import lombok.Data;

@Data
public class TransactionDTO {
    private Double price;
    private String isPeriodic;
    private String type;
    private Integer categoryId;
    private Integer periodId;
}

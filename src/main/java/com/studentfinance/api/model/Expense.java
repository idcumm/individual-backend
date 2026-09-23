package com.studentfinance.api.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.LocalDate;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class Expense {

    private Long id;
    private BigDecimal amount;
    private String category;
    private LocalDate date;
    private String description;
}
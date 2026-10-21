package com.studentfinance.api.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;
import java.time.LocalDate;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor // used by the tests
public class Expense {

    private Long id;
    private BigDecimal amount;
    private ExpenseType type;
    private String category;
    private LocalDate date;
    private String description;
}
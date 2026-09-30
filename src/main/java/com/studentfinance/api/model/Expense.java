package com.studentfinance.api.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;
import java.time.LocalDate;

@Getter
@Setter
@NoArgsConstructor // Jackson needs it to read the JSON
@AllArgsConstructor // used by the tests
public class Expense { // no validation yet, accepts negative or null values

    private Long id;
    private BigDecimal amount; // not double, exact decimals for money
    private String category;
    private LocalDate date;
    private String description;
}
package com.studentfinance.api.dto;

import com.studentfinance.api.model.ExpenseType;
import lombok.AllArgsConstructor;
import lombok.Getter;

import java.math.BigDecimal;
import java.time.LocalDate;

@Getter
@AllArgsConstructor
public class ExpenseResponse {
    private Long id;
    private BigDecimal amount;
    private ExpenseType type;
    private String category;
    private LocalDate date;
    private String description;
}

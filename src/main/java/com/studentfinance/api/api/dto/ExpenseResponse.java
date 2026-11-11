package com.studentfinance.api.api.dto;

import com.studentfinance.api.dal.model.ExpenseType;
import lombok.AllArgsConstructor;
import lombok.Getter;
import java.math.BigDecimal;
import java.time.LocalDate;

@Getter
@AllArgsConstructor
public class ExpenseResponse {
    // Attributes
    private Long id;
    private BigDecimal amount;
    private ExpenseType type;
    private String category;
    private LocalDate date;
    private String description;
}

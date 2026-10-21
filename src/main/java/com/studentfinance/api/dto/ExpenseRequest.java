package com.studentfinance.api.dto;

import com.studentfinance.api.model.ExpenseType;
import jakarta.validation.constraints.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;
import java.time.LocalDate;

@Getter
@Setter
@NoArgsConstructor
public class ExpenseRequest {
    @NotNull @PositiveOrZero @Digits(integer = 4, fraction = 2) // max 9999.99
    private BigDecimal amount;
    @NotNull
    private ExpenseType type;
    @NotBlank @Size(max = 30)
    private String category;
    @NotNull
    private LocalDate date;
    @Size(max = 100)
    private String description;

    public void setCategory(String category) {
        this.category = category == null ? null : category.strip();
    }
}
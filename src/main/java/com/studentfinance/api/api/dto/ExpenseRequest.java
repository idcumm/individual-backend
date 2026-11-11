package com.studentfinance.api.api.dto;

import com.studentfinance.api.dal.model.ExpenseType;
import jakarta.validation.constraints.*;
import lombok.Getter;
import lombok.Setter;
import java.math.BigDecimal;
import java.time.LocalDate;

@Getter
@Setter
public class ExpenseRequest { // if some rule fails, service responds 400
    // Attributes
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

    // Setter category
    public void setCategory(String category) {
        if (category == null) {
            this.category = null;
        } else {
            this.category = category.strip();
        }
    }
}

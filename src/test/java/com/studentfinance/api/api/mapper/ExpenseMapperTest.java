package com.studentfinance.api.api.mapper;

import com.studentfinance.api.api.dto.ExpenseRequest;
import com.studentfinance.api.api.dto.ExpenseResponse;
import com.studentfinance.api.dal.model.Expense;
import com.studentfinance.api.dal.model.ExpenseType;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;
import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.*;

// Checks that the mapper copies every field between the DTOs and the model
class ExpenseMapperTest {
    // Attributes
    private final ExpenseMapper mapper = new ExpenseMapper();

    // Test
    @Test
    void toExpenseCopiesAllFieldsAndLeavesIdEmpty() {
        ExpenseRequest request = new ExpenseRequest();
        request.setAmount(new BigDecimal("12.50"));
        request.setType(ExpenseType.EXPENSE);
        request.setCategory("Food");
        request.setDate(LocalDate.of(2026, 9, 23));
        request.setDescription("Supermarket");

        Expense expense = mapper.toExpense(request);

        assertNull(expense.getId()); // the database sets the id later
        assertEquals(new BigDecimal("12.50"), expense.getAmount());
        assertEquals(ExpenseType.EXPENSE, expense.getType());
        assertEquals("Food", expense.getCategory());
        assertEquals(LocalDate.of(2026, 9, 23), expense.getDate());
        assertEquals("Supermarket", expense.getDescription());
    }

    @Test
    void toResponseCopiesAllFields() {
        Expense expense = new Expense(1L, new BigDecimal("12.50"), ExpenseType.INCOME, "Salary", LocalDate.of(2026, 9, 23), "September");

        ExpenseResponse response = mapper.toResponse(expense);

        assertEquals(1L, response.getId());
        assertEquals(new BigDecimal("12.50"), response.getAmount());
        assertEquals(ExpenseType.INCOME, response.getType());
        assertEquals("Salary", response.getCategory());
        assertEquals(LocalDate.of(2026, 9, 23), response.getDate());
        assertEquals("September", response.getDescription());
    }
}

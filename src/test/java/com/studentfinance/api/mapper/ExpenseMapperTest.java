package com.studentfinance.api.mapper;

import com.studentfinance.api.dto.ExpenseRequest;
import com.studentfinance.api.dto.ExpenseResponse;
import com.studentfinance.api.model.Expense;
import com.studentfinance.api.model.ExpenseType;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;
import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.*;

class ExpenseMapperTest {

    private final ExpenseMapper mapper = new ExpenseMapper();

    @Test
    @DisplayName("Maps a request to an expense without id")
    void toExpense() {
        ExpenseRequest request = new ExpenseRequest();
        request.setAmount(new BigDecimal("12.50"));
        request.setType(ExpenseType.EXPENSE);
        request.setCategory("Food");
        request.setDate(LocalDate.of(2026, 9, 23));
        request.setDescription("Supermarket");

        Expense expense = mapper.toExpense(request);

        assertNull(expense.getId()); // the service sets it
        assertEquals(new BigDecimal("12.50"), expense.getAmount());
        assertEquals(ExpenseType.EXPENSE, expense.getType());
        assertEquals("Food", expense.getCategory());
        assertEquals(LocalDate.of(2026, 9, 23), expense.getDate());
        assertEquals("Supermarket", expense.getDescription());
    }

    @Test
    @DisplayName("Maps an expense to a response with all fields")
    void toResponse() {
        Expense expense = new Expense(1L, new BigDecimal("12.50"), ExpenseType.INCOME, "Job", LocalDate.of(2026, 9, 23), "Salary");

        ExpenseResponse response = mapper.toResponse(expense);

        assertEquals(1L, response.getId());
        assertEquals(new BigDecimal("12.50"), response.getAmount());
        assertEquals(ExpenseType.INCOME, response.getType());
        assertEquals("Job", response.getCategory());
        assertEquals(LocalDate.of(2026, 9, 23), response.getDate());
        assertEquals("Salary", response.getDescription());
    }
}

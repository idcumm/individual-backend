package com.studentfinance.api.service;

import com.studentfinance.api.model.Expense;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;
import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.*;

class ExpenseServiceTest {

    @Test
    @DisplayName("Adds an expense and assigns an id")
    void addExpense() {
        ExpenseService service = new ExpenseService();

        Expense expense = new Expense(
                null,
                new BigDecimal("12.50"),
                "Food",
                LocalDate.of(2026, 9, 23),
                "Supermarket"
        );

        Expense created = service.addExpense(expense);

        assertEquals(1L, created.getId()); // Long 1
        assertEquals(1, service.getAllExpenses().size());
    }

    @Test
    @DisplayName("Deletes an existing expense")
    void deleteExpense() {
        ExpenseService service = new ExpenseService();

        Expense expense = new Expense(
                null,
                new BigDecimal("12.50"),
                "Food",
                LocalDate.of(2026, 9, 23),
                "Supermarket"
        );

        Expense created = service.addExpense(expense);

        boolean deleted = service.deleteExpense(created.getId());

        assertTrue(deleted);
        assertTrue(service.getAllExpenses().isEmpty());
    }
}
package com.studentfinance.api.service;

import com.studentfinance.api.model.Expense;
import com.studentfinance.api.model.ExpenseType;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;
import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.*;

class ExpenseServiceTest {

    // JUnit creates a new instance per test, so each test starts with an empty service
    private final ExpenseService service = new ExpenseService();

    private Expense newExpense() {
        return new Expense(null, new BigDecimal("12.50"), ExpenseType.EXPENSE, "Food", LocalDate.of(2026, 9, 23), "Supermarket");
    }

    @Test
    @DisplayName("Adds expenses with incremental ids")
    void addExpense() {
        Expense first = service.addExpense(newExpense());
        Expense second = service.addExpense(newExpense());

        assertEquals(1L, first.getId());
        assertEquals(2L, second.getId());
        assertEquals(2, service.getAllExpenses().size());
    }

    @Test
    @DisplayName("The returned list cannot be modified from outside")
    void getAllExpenses_readOnly() {
        service.addExpense(newExpense());

        assertThrows(UnsupportedOperationException.class, () -> service.getAllExpenses().clear());
        assertEquals(1, service.getAllExpenses().size());
    }

    @Test
    @DisplayName("Deletes only the expense with the given id")
    void deleteExpense() {
        Expense first = service.addExpense(newExpense());
        Expense second = service.addExpense(newExpense());

        assertTrue(service.deleteExpense(first.getId()));
        assertEquals(1, service.getAllExpenses().size());
        assertEquals(second.getId(), service.getAllExpenses().get(0).getId());
    }

    @Test
    @DisplayName("Returns false when deleting an id that does not exist")
    void deleteExpense_notFound() {
        service.addExpense(newExpense());

        assertFalse(service.deleteExpense(99L));
        assertEquals(1, service.getAllExpenses().size());
    }
}

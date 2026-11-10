package com.studentfinance.api.service;

import com.studentfinance.api.model.Expense;
import com.studentfinance.api.model.ExpenseType;
import com.studentfinance.api.repository.ExpenseRepository;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class ExpenseServiceTest {

    @Mock // fake repository: no database, we decide what it answers
    private ExpenseRepository repository;

    @InjectMocks // real service, built with the fake repository inside
    private ExpenseService service;

    private Expense newExpense() {
        return new Expense(null, new BigDecimal("12.50"), ExpenseType.EXPENSE, "Food", LocalDate.of(2026, 9, 23), "Supermarket");
    }

    @Test
    @DisplayName("Adding an expense saves it in the repository")
    void addExpense() {
        Expense expense = newExpense();

        service.addExpense(expense);

        verify(repository).save(expense);
    }

    @Test
    @DisplayName("Returns whatever the repository has")
    void getAllExpenses() {
        when(repository.findAll()).thenReturn(List.of(newExpense()));

        assertEquals(1, service.getAllExpenses().size());
    }

    @Test
    @DisplayName("Deletes an expense that exists")
    void deleteExpense() {
        when(repository.existsById(1L)).thenReturn(true);

        assertTrue(service.deleteExpense(1L));
        verify(repository).deleteById(1L);
    }

    @Test
    @DisplayName("Returns false and deletes nothing when the id does not exist")
    void deleteExpense_notFound() {
        when(repository.existsById(99L)).thenReturn(false);

        assertFalse(service.deleteExpense(99L));
        verify(repository, never()).deleteById(any());
    }
}

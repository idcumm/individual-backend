package com.studentfinance.api.bll.service;

import com.studentfinance.api.dal.model.Expense;
import com.studentfinance.api.dal.repository.ExpenseRepository;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

// Tests the service alone: the repository is a mock (a fake), so no database is used
class ExpenseServiceTest {
    // Attributes
    private final ExpenseRepository repository = mock(ExpenseRepository.class);
    private final ExpenseService service = new ExpenseService(repository);

    // Tests
    @Test
    void getAllExpensesReturnsWhatTheRepositoryHas() {
        List<Expense> expenses = List.of(new Expense(), new Expense());
        when(repository.findAll()).thenReturn(expenses);

        assertEquals(expenses, service.getAllExpenses());
    }

    @Test
    void addExpenseSavesItAndReturnsTheSavedOne() {
        Expense expense = new Expense();
        Expense saved = new Expense();
        when(repository.save(expense)).thenReturn(saved);

        assertSame(saved, service.addExpense(expense));
    }

    @Test
    void deleteExpenseDeletesWhenItExists() {
        when(repository.existsById(1L)).thenReturn(true);

        assertTrue(service.deleteExpense(1L));
        verify(repository).deleteById(1L);
    }

    @Test
    void deleteExpenseDoesNothingWhenItDoesNotExist() {
        when(repository.existsById(99L)).thenReturn(false);

        assertFalse(service.deleteExpense(99L));
        verify(repository, never()).deleteById(99L);
    }

    @Test
    void editExpenseEditsWhenItExists() {
        Expense expense = new Expense();
        Expense saved = new Expense();

        when(repository.existsById(1L)).thenReturn(true);
        when(repository.save(expense)).thenReturn(saved);

        assertSame(saved, service.editExpense(1L, expense));
        assertEquals(1L, expense.getId());
    }

    @Test
    void editExpenseDoesNothingWhenItDoesNotExist() {
        Expense expense = new Expense();

        when(repository.existsById(99L)).thenReturn(false);

        assertNull(service.editExpense(99L, expense));
        verify(repository, never()).save(any());
    }
}

package com.studentfinance.api.dal.repository;

import com.studentfinance.api.dal.model.Expense;
import com.studentfinance.api.dal.model.ExpenseType;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.data.jpa.test.autoconfigure.DataJpaTest;

import java.math.BigDecimal;
import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.*;

// Uses a real database (H2, in memory) to check that Expense is saved and read correctly.
// @DataJpaTest undoes the changes after each test, so tests do not affect each other.
@DataJpaTest
class ExpenseRepositoryTest {
    // Attributes
    @Autowired
    private ExpenseRepository repository;

    // Method newExpense()
    private Expense newExpense() {
        return new Expense(
                null,
                new BigDecimal("12.50"),
                ExpenseType.EXPENSE, "Food",
                LocalDate.of(2026, 9, 23),
                "Supermarket"
        );
    }

    // Tests
    @Test
    void saveGivesTheExpenseAnId() {
        Expense saved = repository.save(newExpense());

        assertNotNull(saved.getId());
    }

    @Test
    void savedExpenseCanBeReadBack() {
        Long id = repository.saveAndFlush(newExpense()).getId();

        Expense found = repository.findById(id).orElseThrow();

        assertEquals(new BigDecimal("12.50"), found.getAmount());
        assertEquals(ExpenseType.EXPENSE, found.getType());
        assertEquals("Food", found.getCategory());
        assertEquals(LocalDate.of(2026, 9, 23), found.getDate());
        assertEquals("Supermarket", found.getDescription());
    }

    @Test
    void deletedExpenseNoLongerExists() {
        Long id = repository.save(newExpense()).getId();

        repository.deleteById(id);

        assertFalse(repository.existsById(id));
    }
}

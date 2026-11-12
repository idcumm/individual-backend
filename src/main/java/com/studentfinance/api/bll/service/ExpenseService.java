package com.studentfinance.api.bll.service;

import com.studentfinance.api.dal.model.Expense;
import com.studentfinance.api.dal.repository.ExpenseRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import java.util.List;

@Service // Spring creates one instance (bean)
@RequiredArgsConstructor
public class ExpenseService {
    // Attributes
    private final ExpenseRepository repository;

    // Method getAllExpenses()
    public List<Expense> getAllExpenses() {
        return repository.findAll();
    }

    // Method addExpense()
    public Expense addExpense(Expense expense) {
        return repository.save(expense);
    }

    // Method deleteExpense()
    public boolean deleteExpense(Long id) {
        if (!repository.existsById(id)) return false;
        repository.deleteById(id);
        return true;
    }

    // Method editExpense()
    public Expense editExpense(Long id, Expense expense) {
        if (!repository.existsById(id)) return null;
        expense.setId(id);
        return repository.save(expense);
    }
}
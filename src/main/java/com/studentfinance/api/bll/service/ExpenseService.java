package com.studentfinance.api.bll.service;

import com.studentfinance.api.dal.model.Expense;
import com.studentfinance.api.dal.repository.ExpenseRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import java.util.List;

@Service // Spring creates one instance (bean)
@RequiredArgsConstructor
public class ExpenseService {

    private final ExpenseRepository repository;

    // Called by GET /expenses
    public List<Expense> getAllExpenses() {
        return repository.findAll();
    }

    // Called by POST /expenses
    public Expense addExpense(Expense expense) {
        return repository.save(expense);
    }

    // Called by DELETE /expenses/{id}
    public boolean deleteExpense(Long id) {
        if (!repository.existsById(id)) return false;
        repository.deleteById(id);
        return true;
    }
}
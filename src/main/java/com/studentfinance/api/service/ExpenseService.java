package com.studentfinance.api.service;

import com.studentfinance.api.model.Expense;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class ExpenseService {

    private final List<Expense> expenses = new ArrayList<>();
    private long nextId = 1;


    // Called by GET /expenses
    public List<Expense> getAllExpenses() {
        return expenses;
    }

    // Called by POST /expenses
    public Expense addExpense(Expense expense) {
        expense.setId(nextId++);
        expenses.add(expense);
        return expense;
    }

    // Called by DELETE /expenses/{id}
    public boolean deleteExpense(Long id) {
        return expenses.removeIf(expense -> expense.getId().equals(id)); //.equals() bc Long is an object
    }
}
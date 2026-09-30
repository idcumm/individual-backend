package com.studentfinance.api.controller;

import com.studentfinance.api.model.Expense;
import com.studentfinance.api.service.ExpenseService;
import org.springframework.web.bind.annotation.*;
import org.springframework.http.ResponseEntity;

import java.util.List;

@RestController
@RequestMapping("/expenses")
public class ExpenseController {
    private final ExpenseService expenseService;
    public ExpenseController(ExpenseService expenseService) { // Spring injects the service
        this.expenseService = expenseService;
    }

    @GetMapping
    public List<Expense> getExpenses() {
        return expenseService.getAllExpenses();
    }

    @PostMapping // returns 200, should be 201 Created
    public Expense addExpense(@RequestBody Expense expense) { // @RequestBody converts JSON to Expense
        return expenseService.addExpense(expense);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteExpense(@PathVariable Long id) { // @PathVariable gets the id from /{id}

        boolean deleted = expenseService.deleteExpense(id);

        if (deleted) {
            return ResponseEntity.noContent().build(); // 204 No Content
        }

        return ResponseEntity.notFound().build(); // 404 Not Found
    }
}
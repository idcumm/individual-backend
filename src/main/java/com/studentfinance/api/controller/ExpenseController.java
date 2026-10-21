package com.studentfinance.api.controller;

import com.studentfinance.api.dto.ExpenseRequest;
import com.studentfinance.api.dto.ExpenseResponse;
import com.studentfinance.api.mapper.ExpenseMapper;
import com.studentfinance.api.model.Expense;
import com.studentfinance.api.service.ExpenseService;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.http.ResponseEntity;
import jakarta.validation.Valid;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/expenses")
public class ExpenseController {
    private final ExpenseService expenseService;
    private final ExpenseMapper expenseMapper;
    public ExpenseController(ExpenseService expenseService, ExpenseMapper expenseMapper) {
        this.expenseService = expenseService;
        this.expenseMapper = expenseMapper;
    }

    @GetMapping
    public List<ExpenseResponse> getExpenses() {
        List<Expense> expenses = expenseService.getAllExpenses();
        List<ExpenseResponse> responses = new ArrayList<>();
        for (Expense expense : expenses) {
            ExpenseResponse r = expenseMapper.toResponse(expense);
            responses.add(r);
        }
        return responses;
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED) // Returns 201 Created
    public ExpenseResponse addExpense(@Valid @RequestBody ExpenseRequest request) {
        Expense expense = expenseMapper.toExpense(request);
        Expense saved = expenseService.addExpense(expense);
        return expenseMapper.toResponse(saved);
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
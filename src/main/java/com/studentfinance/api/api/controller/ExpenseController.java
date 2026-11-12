package com.studentfinance.api.api.controller;

import com.studentfinance.api.api.dto.ExpenseRequest;
import com.studentfinance.api.api.dto.ExpenseResponse;
import com.studentfinance.api.api.mapper.ExpenseMapper;
import com.studentfinance.api.dal.model.Expense;
import com.studentfinance.api.bll.service.ExpenseService;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.http.ResponseEntity;
import jakarta.validation.Valid;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/expenses")
public class ExpenseController {
    // Attributes
    private final ExpenseService service;
    private final ExpenseMapper mapper;

    // Constructor
    public ExpenseController(ExpenseService service, ExpenseMapper mapper) {
        this.service = service;
        this.mapper = mapper;
    }

    // Method getExpenses()
    @GetMapping
    public List<ExpenseResponse> getExpenses() {
        List<Expense> expenses = service.getAllExpenses();
        List<ExpenseResponse> responses = new ArrayList<>();
        for (Expense expense : expenses) {
            ExpenseResponse r = mapper.toResponse(expense);
            responses.add(r);
        }
        return responses;
    }

    // Method addExpense()
    @PostMapping
    @ResponseStatus(HttpStatus.CREATED) // Returns 201 Created
    public ExpenseResponse addExpense(@Valid @RequestBody ExpenseRequest request) {
        Expense expense = mapper.toExpense(request);
        Expense saved = service.addExpense(expense);
        return mapper.toResponse(saved);
    }

    // Method deleteExpense()
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteExpense(@PathVariable Long id) { // @PathVariable gets the id from /{id}
        boolean deleted = service.deleteExpense(id);
        if (deleted) return ResponseEntity.status(204).build(); // No Content
        return ResponseEntity.status(404).build(); // Not Found
    }

    @PutMapping("/{id}")
    public ResponseEntity<ExpenseResponse> editExpense(@PathVariable Long id, @Valid @RequestBody ExpenseRequest request) {
        Expense expense = mapper.toExpense(request);
        Expense edited = service.editExpense(id, expense);
        if (edited != null) return ResponseEntity.ok(mapper.toResponse(edited)); // OK
        return ResponseEntity.status(404).build(); // Not Found
    }

}
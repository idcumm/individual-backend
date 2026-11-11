package com.studentfinance.api.api.mapper;

import com.studentfinance.api.api.dto.ExpenseRequest;
import com.studentfinance.api.api.dto.ExpenseResponse;
import com.studentfinance.api.dal.model.Expense;
import org.springframework.stereotype.Component;

@Component
public class ExpenseMapper {
    // Method toExpense()
    public Expense toExpense(ExpenseRequest request) {
        return new Expense(
                null,
                request.getAmount(),
                request.getType(),
                request.getCategory(),
                request.getDate(),
                request.getDescription());
    }

    // Method toResponse()
    public ExpenseResponse toResponse(Expense expense) {
        return new ExpenseResponse(
                expense.getId(),
                expense.getAmount(),
                expense.getType(),
                expense.getCategory(),
                expense.getDate(),
                expense.getDescription());
    }
}

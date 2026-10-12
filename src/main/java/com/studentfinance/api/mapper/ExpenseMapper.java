package com.studentfinance.api.mapper;

import com.studentfinance.api.dto.ExpenseRequest;
import com.studentfinance.api.dto.ExpenseResponse;
import com.studentfinance.api.model.Expense;
import org.springframework.stereotype.Component;

@Component
public class ExpenseMapper {
    public Expense toExpense(ExpenseRequest request) {
        return new Expense(null, request.getAmount(), request.getCategory(), request.getDate(), request.getDescription());
    }

    public ExpenseResponse toResponse(Expense expense) {
        return new ExpenseResponse(expense.getId(), expense.getAmount(), expense.getCategory(), expense.getDate(), expense.getDescription());
    }
}

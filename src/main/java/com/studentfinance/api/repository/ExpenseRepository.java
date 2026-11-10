package com.studentfinance.api.repository;

import com.studentfinance.api.model.Expense;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ExpenseRepository extends JpaRepository<Expense, Long> { }
// Implements methods like findAll(), save(expense), existsById(id), deleteById(id)...
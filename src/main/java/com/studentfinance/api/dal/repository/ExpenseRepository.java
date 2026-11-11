package com.studentfinance.api.dal.repository;

import com.studentfinance.api.dal.model.Expense;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ExpenseRepository extends JpaRepository<Expense, Long> { }
// Implements methods like findAll(), save(expense), existsById(id), deleteById(id)...
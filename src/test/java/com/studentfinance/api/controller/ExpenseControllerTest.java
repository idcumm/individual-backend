package com.studentfinance.api.controller;

import com.studentfinance.api.mapper.ExpenseMapper;
import com.studentfinance.api.model.Expense;
import com.studentfinance.api.model.ExpenseType;
import com.studentfinance.api.service.ExpenseService;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.webmvc.test.autoconfigure.WebMvcTest;
import org.springframework.context.annotation.Import;
import org.springframework.http.MediaType;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verifyNoInteractions;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(ExpenseController.class)
@Import(ExpenseMapper.class) // real mapper, it has no dependencies so there is nothing to mock
class ExpenseControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockitoBean
    private ExpenseService expenseService;

    @Test
    @DisplayName("GET returns the list of expenses as JSON")
    void getExpenses() throws Exception {
        Expense expense = new Expense(1L, new BigDecimal("12.50"), ExpenseType.EXPENSE, "Food", LocalDate.of(2026, 9, 23), "Supermarket");
        when(expenseService.getAllExpenses()).thenReturn(List.of(expense));

        mockMvc.perform(get("/expenses"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.length()").value(1))
                .andExpect(jsonPath("$[0].id").value(1))
                .andExpect(jsonPath("$[0].amount").value(12.5))
                .andExpect(jsonPath("$[0].type").value("EXPENSE"))
                .andExpect(jsonPath("$[0].category").value("Food"))
                .andExpect(jsonPath("$[0].date").value("2026-09-23"))
                .andExpect(jsonPath("$[0].description").value("Supermarket"));
    }

    @Test
    @DisplayName("POST returns 201 and the created expense with its id")
    void addExpense() throws Exception {
        // The mock behaves like the real service: sets the id and returns the same expense
        when(expenseService.addExpense(any(Expense.class))).thenAnswer(invocation -> {
            Expense expense = invocation.getArgument(0);
            expense.setId(1L);
            return expense;
        });

        String body = """
            {
              "amount": 12.50,
              "type": "EXPENSE",
              "category": "Food",
              "date": "2026-09-23",
              "description": "Supermarket"
            }
            """;

        mockMvc.perform(post("/expenses")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(body))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.id").value(1))
                .andExpect(jsonPath("$.amount").value(12.5))
                .andExpect(jsonPath("$.category").value("Food"));
    }

    // Each validation rule is tested in ExpenseRequestTest, here we only check that @Valid is applied
    @Test
    @DisplayName("POST returns 400 and does not save when the body is invalid")
    void addExpense_invalid() throws Exception {
        String body = """
            {
              "type": "EXPENSE",
              "category": "Food",
              "date": "2026-09-23"
            }
            """;

        mockMvc.perform(post("/expenses")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(body))
                .andExpect(status().isBadRequest());

        verifyNoInteractions(expenseService);
    }

    @Test
    @DisplayName("DELETE returns 204 when the expense exists")
    void deleteExpense_exists() throws Exception {
        when(expenseService.deleteExpense(1L)).thenReturn(true);

        mockMvc.perform(delete("/expenses/1"))
                .andExpect(status().isNoContent());
    }

    @Test
    @DisplayName("DELETE returns 404 when the expense does not exist")
    void deleteExpense_notFound() throws Exception {
        when(expenseService.deleteExpense(99L)).thenReturn(false);

        mockMvc.perform(delete("/expenses/99"))
                .andExpect(status().isNotFound());
    }
}

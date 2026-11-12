package com.studentfinance.api.api.controller;

import com.studentfinance.api.api.mapper.ExpenseMapper;
import com.studentfinance.api.bll.service.ExpenseService;
import com.studentfinance.api.dal.model.Expense;
import com.studentfinance.api.dal.model.ExpenseType;
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
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

// Sends fake HTTP requests to the controller and checks the status code and the JSON.
@WebMvcTest(ExpenseController.class)
@Import(ExpenseMapper.class)
class ExpenseControllerTest {
    // Attributes
    @Autowired
    private MockMvc mockMvc;

    @MockitoBean
    private ExpenseService service;

    private final Expense expense = new Expense(
            1L,
            new BigDecimal("12.50"),
            ExpenseType.EXPENSE,
            "Food",
            LocalDate.of(2026, 9, 23),
            "Supermarket"
    );

    // Tests
    @Test
    void getReturnsTheExpensesAsJson() throws Exception {
        when(service.getAllExpenses()).thenReturn(List.of(expense));

        mockMvc.perform(get("/expenses"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].id").value(1))
                .andExpect(jsonPath("$[0].amount").value(12.5))
                .andExpect(jsonPath("$[0].type").value("EXPENSE"))
                .andExpect(jsonPath("$[0].category").value("Food"))
                .andExpect(jsonPath("$[0].date").value("2026-09-23"))
                .andExpect(jsonPath("$[0].description").value("Supermarket"));
    }

    @Test
    void postReturns201AndTheCreatedExpense() throws Exception {
        when(service.addExpense(any())).thenReturn(expense);

        String body = """
                {"amount": 12.50,
                "type": "EXPENSE",
                "category": "Food",
                "date": "2026-09-23",
                "description": "Supermarket"}
                """;

        mockMvc.perform(post("/expenses").contentType(MediaType.APPLICATION_JSON).content(body))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.id").value(1))
                .andExpect(jsonPath("$.category").value("Food"));
    }

    // Each validation rule is tested in ExpenseRequestTest; here we only check that the controller applies them
    @Test
    void postReturns400AndSavesNothingWhenTheBodyIsInvalid() throws Exception {
        String bodyWithoutAmount = """
                {"type": "EXPENSE", "category": "Food", "date": "2026-09-23"}
                """;

        mockMvc.perform(post("/expenses").contentType(MediaType.APPLICATION_JSON).content(bodyWithoutAmount))
                .andExpect(status().isBadRequest());

        verify(service, never()).addExpense(any());
    }

    @Test
    void deleteReturns204WhenTheExpenseExists() throws Exception {
        when(service.deleteExpense(1L)).thenReturn(true);

        mockMvc.perform(delete("/expenses/1"))
                .andExpect(status().isNoContent());
    }

    @Test
    void deleteReturns404WhenTheExpenseDoesNotExist() throws Exception {
        when(service.deleteExpense(99L)).thenReturn(false);

        mockMvc.perform(delete("/expenses/99"))
                .andExpect(status().isNotFound());
    }

    @Test
    void putReturns200WhenTheExpenseIsEdited() throws Exception {
        String body = """
                {"amount": 12.50,
                "type": "EXPENSE",
                "category": "Food",
                "date": "2026-09-23",
                "description": "Supermarket"}
                """;

        when(service.editExpense(eq(1L), any())).thenReturn(expense);

        mockMvc.perform(put("/expenses/1").contentType(MediaType.APPLICATION_JSON).content(body))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(1))
                .andExpect(jsonPath("$.category").value("Food"));
    }

    @Test
    void putReturns404WhenTheExpenseDoesNotExist() throws Exception {
        String body = """
                {"amount": 12.50,
                "type": "EXPENSE",
                "category": "Food",
                "date": "2026-09-23",
                "description": "Supermarket"}
                """;

        when(service.editExpense(eq(99L), any())).thenReturn(null);

        mockMvc.perform(put("/expenses/99")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(body))
                .andExpect(status().isNotFound());
    }

    @Test
    void putReturns400AndSavesNothingWhenTheBodyIsInvalid() throws Exception {
        String bodyWithoutAmount = """
                {"type": "EXPENSE",
                "category": "Food",
                "date": "2026-09-23"}
                """;

        mockMvc.perform(put("/expenses/1")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(bodyWithoutAmount))
                .andExpect(status().isBadRequest());

        verify(service, never()).editExpense(any(), any());
    }
}

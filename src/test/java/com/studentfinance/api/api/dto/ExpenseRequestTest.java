package com.studentfinance.api.api.dto;

import com.studentfinance.api.dal.model.ExpenseType;
import jakarta.validation.Validation;
import jakarta.validation.Validator;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;
import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.*;

// Checks the validation rules (@NotNull, @Size...) written in ExpenseRequest.
// Every test starts from a valid request and breaks only one field.
class ExpenseRequestTest {
    // Attributes
    private final Validator validator = Validation.buildDefaultValidatorFactory().getValidator();

    // Method validRequest()
    private ExpenseRequest validRequest() {
        ExpenseRequest request = new ExpenseRequest();
        request.setAmount(new BigDecimal("12.50"));
        request.setType(ExpenseType.EXPENSE);
        request.setCategory("Food");
        request.setDate(LocalDate.of(2026, 9, 23));
        request.setDescription("Supermarket");
        return request;
    }

    // Method isValid()
    private boolean isValid(ExpenseRequest request) { // True when the request breaks no rule
        return validator.validate(request).isEmpty();
    }

    // Tests
    @Test
    void validRequestIsAccepted() {
        assertTrue(isValid(validRequest()));
    }

    @Test
    void descriptionIsOptional() {
        ExpenseRequest request = validRequest();
        request.setDescription(null);
        assertTrue(isValid(request));
    }

    @Test
    void amountIsRequired() {
        ExpenseRequest request = validRequest();
        request.setAmount(null);
        assertFalse(isValid(request));
    }

    @Test
    void amountCannotBeNegative() {
        ExpenseRequest request = validRequest();
        request.setAmount(new BigDecimal("-1"));
        assertFalse(isValid(request));
    }

    @Test
    void amountCannotBeOver9999_99() {
        ExpenseRequest request = validRequest();
        request.setAmount(new BigDecimal("10000"));
        assertFalse(isValid(request));
    }

    @Test
    void amountCannotHaveMoreThanTwoDecimals() {
        ExpenseRequest request = validRequest();
        request.setAmount(new BigDecimal("1.234"));
        assertFalse(isValid(request));
    }

    @Test
    void typeIsRequired() {
        ExpenseRequest request = validRequest();
        request.setType(null);
        assertFalse(isValid(request));
    }

    @Test
    void categoryIsRequired() {
        ExpenseRequest request = validRequest();
        request.setCategory(null);
        assertFalse(isValid(request));
    }

    @Test
    void categoryCannotBeBlank() {
        ExpenseRequest request = validRequest();
        request.setCategory("   ");
        assertFalse(isValid(request));
    }

    @Test
    void categoryCannotBeLongerThan30() {
        ExpenseRequest request = validRequest();
        request.setCategory("a".repeat(31));
        assertFalse(isValid(request));
    }

    @Test
    void categoryIsStripped() {
        ExpenseRequest request = validRequest();
        request.setCategory("  Food  ");
        assertEquals("Food", request.getCategory());
    }

    @Test
    void dateIsRequired() {
        ExpenseRequest request = validRequest();
        request.setDate(null);
        assertFalse(isValid(request));
    }

    @Test
    void descriptionCannotBeLongerThan100() {
        ExpenseRequest request = validRequest();
        request.setDescription("a".repeat(101));
        assertFalse(isValid(request));
    }
}

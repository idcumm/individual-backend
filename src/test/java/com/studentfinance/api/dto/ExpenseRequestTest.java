package com.studentfinance.api.dto;

import com.studentfinance.api.model.ExpenseType;
import jakarta.validation.Validation;
import jakarta.validation.Validator;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.Set;
import java.util.stream.Collectors;

import static org.junit.jupiter.api.Assertions.*;

// Checks the validation annotations directly, without starting Spring
class ExpenseRequestTest {

    private static final Validator validator = Validation.buildDefaultValidatorFactory().getValidator();

    private ExpenseRequest validRequest() {
        ExpenseRequest request = new ExpenseRequest();
        request.setAmount(new BigDecimal("12.50"));
        request.setType(ExpenseType.EXPENSE);
        request.setCategory("Food");
        request.setDate(LocalDate.of(2026, 9, 23));
        request.setDescription("Supermarket");
        return request;
    }

    // Names of the fields that failed validation
    private Set<String> invalidFields(ExpenseRequest request) {
        return validator.validate(request).stream()
                .map(violation -> violation.getPropertyPath().toString())
                .collect(Collectors.toSet());
    }

    @Test
    @DisplayName("A complete request is valid")
    void valid() {
        assertTrue(invalidFields(validRequest()).isEmpty());
    }

    @Test
    @DisplayName("Amount, type, category and date are required; description is optional")
    void requiredFields() {
        assertEquals(Set.of("amount", "type", "category", "date"), invalidFields(new ExpenseRequest()));
    }

    @ParameterizedTest
    @ValueSource(strings = {"0", "9999.99"})
    @DisplayName("Amount accepts the limits")
    void amount_valid(String amount) {
        ExpenseRequest request = validRequest();
        request.setAmount(new BigDecimal(amount));

        assertTrue(invalidFields(request).isEmpty());
    }

    @ParameterizedTest
    @ValueSource(strings = {"-0.01", "10000", "1.234"})
    @DisplayName("Amount rejects negatives, more than 4 integer digits and more than 2 decimals")
    void amount_invalid(String amount) {
        ExpenseRequest request = validRequest();
        request.setAmount(new BigDecimal(amount));

        assertEquals(Set.of("amount"), invalidFields(request));
    }

    @ParameterizedTest
    @ValueSource(strings = {"", "   "})
    @DisplayName("Category cannot be blank")
    void category_blank(String category) {
        ExpenseRequest request = validRequest();
        request.setCategory(category);

        assertEquals(Set.of("category"), invalidFields(request));
    }

    @Test
    @DisplayName("Category and description have a max length")
    void maxLength() {
        ExpenseRequest request = validRequest();
        request.setCategory("a".repeat(31));
        request.setDescription("a".repeat(101));

        assertEquals(Set.of("category", "description"), invalidFields(request));
    }

    @Test
    @DisplayName("Category is stripped of surrounding spaces")
    void category_stripped() {
        ExpenseRequest request = new ExpenseRequest();

        request.setCategory("  Food  ");
        assertEquals("Food", request.getCategory());

        request.setCategory(null);
        assertNull(request.getCategory());
    }
}

package com.productvalidation.application.rest;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.productvalidation.ValidationBuilders;
import com.productvalidation.domain.model.Product;
import com.productvalidation.domain.model.ProductStatus;
import com.productvalidation.domain.ports.ProductRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;

import java.util.List;
import java.util.UUID;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(ReviewerController.class)
class ReviewerControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @MockitoBean
    private ProductRepository productRepository;

    @Test
    void getPendingReviews_returnsList() throws Exception {
        Product product = ValidationBuilders.validProduct();
        when(productRepository.findByStatus(ProductStatus.NEEDS_REVIEW))
                .thenReturn(List.of(product));

        mockMvc.perform(get("/reviews/pending"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].id").value(product.getId().toString()));
    }

    @Test
    void getPendingReviews_returnsEmptyListWhenNonePending() throws Exception {
        when(productRepository.findByStatus(ProductStatus.NEEDS_REVIEW))
                .thenReturn(List.of());

        mockMvc.perform(get("/reviews/pending"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$").isEmpty());
    }

    @Test
    void submitDecision_acceptsValidated() throws Exception {
        UUID id = UUID.randomUUID();
        ReviewDecisionDto decision = new ReviewDecisionDto();
        decision.setStatus(ProductStatus.VALIDATED);
        decision.setNotes("Looks good");

        mockMvc.perform(post("/reviews/{id}/decision", id)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(decision)))
                .andExpect(status().isOk());

        verify(productRepository).updateStatus(eq(id), eq(ProductStatus.VALIDATED), any());
    }

    @Test
    void submitDecision_acceptsValidationFailed() throws Exception {
        UUID id = UUID.randomUUID();
        ReviewDecisionDto decision = new ReviewDecisionDto();
        decision.setStatus(ProductStatus.VALIDATION_FAILED);
        decision.setNotes("Missing ISRC");

        mockMvc.perform(post("/reviews/{id}/decision", id)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(decision)))
                .andExpect(status().isOk());

        verify(productRepository).updateStatus(eq(id), eq(ProductStatus.VALIDATION_FAILED), any());
    }

    @Test
    void submitDecision_rejectsMissingStatus() throws Exception {
        UUID id = UUID.randomUUID();
        ReviewDecisionDto decision = new ReviewDecisionDto();
        // status intentionally null

        mockMvc.perform(post("/reviews/{id}/decision", id)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(decision)))
                .andExpect(status().isBadRequest());

        verify(productRepository, never()).updateStatus(any(), any(), any());
    }

    @Test
    void submitDecision_rejectsInvalidStatus() throws Exception {
        UUID id = UUID.randomUUID();
        ReviewDecisionDto decision = new ReviewDecisionDto();
        decision.setStatus(ProductStatus.PUBLISHED);

        mockMvc.perform(post("/reviews/{id}/decision", id)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(decision)))
                .andExpect(status().isBadRequest());

        verify(productRepository, never()).updateStatus(any(), any(), any());
    }
}
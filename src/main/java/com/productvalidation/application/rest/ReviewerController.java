package com.productvalidation.application.rest;

import com.productvalidation.domain.model.Product;
import com.productvalidation.domain.model.ProductStatus;
import com.productvalidation.domain.ports.ProductRepository;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/reviews")
public class ReviewerController {

    private final ProductRepository productRepository;

    public ReviewerController(ProductRepository productRepository) {
        this.productRepository = productRepository;
    }

    @GetMapping("/pending")
    public ResponseEntity<List<Product>> getPendingReviews() {
        List<Product> pending = productRepository.findByStatus(ProductStatus.NEEDS_REVIEW);
        return ResponseEntity.ok(pending);
    }

    @PostMapping("/{id}/decision")
    public ResponseEntity<Void> submitDecision(
            @PathVariable UUID id,
            @RequestBody ReviewDecisionDto decision) {

        if (decision.getStatus() == null) {
            return ResponseEntity.badRequest().build();
        }

        if (decision.getStatus() != ProductStatus.VALIDATED
                && decision.getStatus() != ProductStatus.VALIDATION_FAILED) {
            return ResponseEntity.badRequest().build();
        }

        String notes = "Manual review: " + (decision.getNotes() != null ? decision.getNotes() : "no notes provided");

        productRepository.updateStatus(id, decision.getStatus(), notes);
        return ResponseEntity.ok().build();
    }
}
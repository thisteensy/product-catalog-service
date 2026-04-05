package com.productcatalog.domain.model;

import org.junit.jupiter.api.Test;

import java.util.List;

import static com.productcatalog.ValidationBuilders.validProduct;
import static com.productcatalog.ValidationBuilders.validTrack;
import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

class ProductTest {

    @Test
    void shouldTransitionToAwaitingTrackValidationWhenCurrentStatusIsSubmitted() {
        Product product = validProduct();
        product.transitionTo(ProductStatus.AWAITING_TRACK_VALIDATION, null);
        assertThat(product.getStatus()).isEqualTo(ProductStatus.AWAITING_TRACK_VALIDATION);
    }

    @Test
    void shouldTransitionToValidationFailedWhenCurrentStatusIsSubmitted() {
        Product product = validProduct();
        product.transitionTo(ProductStatus.VALIDATION_FAILED, null);
        assertThat(product.getStatus()).isEqualTo(ProductStatus.VALIDATION_FAILED);
    }

    @Test
    void shouldTransitionToNeedsReviewWhenCurrentStatusIsAwaitingTrackValidation() {
        Product product = validProduct();
        product.transitionTo(ProductStatus.AWAITING_TRACK_VALIDATION, null);
        product.transitionTo(ProductStatus.NEEDS_REVIEW, null);
        assertThat(product.getStatus()).isEqualTo(ProductStatus.NEEDS_REVIEW);
    }

    @Test
    void shouldTransitionToValidatedWhenCurrentStatusIsAwaitingTrackValidation() {
        Product product = validProduct();
        product.transitionTo(ProductStatus.AWAITING_TRACK_VALIDATION, null);
        product.transitionTo(ProductStatus.VALIDATED, null);
        assertThat(product.getStatus()).isEqualTo(ProductStatus.VALIDATED);
    }

    @Test
    void shouldTransitionToValidatedWhenCurrentStatusIsNeedsReview() {
        Product product = validProduct();
        product.transitionTo(ProductStatus.AWAITING_TRACK_VALIDATION, null);
        product.transitionTo(ProductStatus.NEEDS_REVIEW, null);
        product.transitionTo(ProductStatus.VALIDATED, null);
        assertThat(product.getStatus()).isEqualTo(ProductStatus.VALIDATED);
    }

    @Test
    void shouldTransitionToPublishedWhenCurrentStatusIsValidated() {
        Product product = validProduct();
        product.transitionTo(ProductStatus.AWAITING_TRACK_VALIDATION, null);
        product.transitionTo(ProductStatus.VALIDATED, null);
        product.transitionTo(ProductStatus.PUBLISHED, null);
        assertThat(product.getStatus()).isEqualTo(ProductStatus.PUBLISHED);
    }

    @Test
    void shouldTransitionToResubmittedWhenCurrentStatusIsValidationFailed() {
        Product product = validProduct();
        product.transitionTo(ProductStatus.VALIDATION_FAILED, null);
        product.transitionTo(ProductStatus.RESUBMITTED, null);
        assertThat(product.getStatus()).isEqualTo(ProductStatus.RESUBMITTED);
    }

    @Test
    void shouldTransitionToTakenDownWhenCurrentStatusIsPublished() {
        Product product = validProduct();
        product.transitionTo(ProductStatus.AWAITING_TRACK_VALIDATION, null);
        product.transitionTo(ProductStatus.VALIDATED, null);
        product.transitionTo(ProductStatus.PUBLISHED, null);
        product.transitionTo(ProductStatus.TAKEN_DOWN, null);
        assertThat(product.getStatus()).isEqualTo(ProductStatus.TAKEN_DOWN);
    }

    @Test
    void shouldTransitionToRetiredWhenCurrentStatusIsTakenDown() {
        Product product = validProduct();
        product.transitionTo(ProductStatus.AWAITING_TRACK_VALIDATION, null);
        product.transitionTo(ProductStatus.VALIDATED, null);
        product.transitionTo(ProductStatus.PUBLISHED, null);
        product.transitionTo(ProductStatus.TAKEN_DOWN, null);
        product.transitionTo(ProductStatus.RETIRED, null);
        assertThat(product.getStatus()).isEqualTo(ProductStatus.RETIRED);
    }

    @Test
    void shouldCascadePublishedToValidatedTracksWhenTransitioningToPublished() {
        Track validatedTrack = validTrack().toBuilder()
                .status(TrackStatus.VALIDATED)
                .build();
        Product product = validProduct();
        product.transitionTo(ProductStatus.AWAITING_TRACK_VALIDATION, null);
        product.transitionTo(ProductStatus.VALIDATED, null);
        product.transitionTo(ProductStatus.PUBLISHED, List.of(validatedTrack));
        assertThat(validatedTrack.getStatus()).isEqualTo(TrackStatus.PUBLISHED);
    }

    @Test
    void shouldCascadeTakenDownToAllTracksWhenTransitioningToTakenDown() {
        Track publishedTrack = validTrack().toBuilder()
                .status(TrackStatus.PUBLISHED)
                .build();
        Product product = validProduct();
        product.transitionTo(ProductStatus.AWAITING_TRACK_VALIDATION, null);
        product.transitionTo(ProductStatus.VALIDATED, null);
        product.transitionTo(ProductStatus.PUBLISHED, null);
        product.transitionTo(ProductStatus.TAKEN_DOWN, List.of(publishedTrack));
        assertThat(publishedTrack.getStatus()).isEqualTo(TrackStatus.TAKEN_DOWN);
    }

    @Test
    void shouldThrowExceptionWhenTransitionIsInvalidFromSubmitted() {
        Product product = validProduct();
        assertThatThrownBy(() -> product.transitionTo(ProductStatus.PUBLISHED, null))
                .isInstanceOf(IllegalStateException.class)
                .hasMessageContaining("Invalid status transition");
    }

    @Test
    void shouldThrowExceptionWhenTransitionIsInvalidFromValidated() {
        Product product = validProduct();
        product.transitionTo(ProductStatus.AWAITING_TRACK_VALIDATION, null);
        product.transitionTo(ProductStatus.VALIDATED, null);
        assertThatThrownBy(() -> product.transitionTo(ProductStatus.RESUBMITTED, null))
                .isInstanceOf(IllegalStateException.class)
                .hasMessageContaining("Invalid status transition");
    }

    @Test
    void shouldThrowExceptionWhenTransitionIsInvalidFromRetired() {
        Product product = validProduct();
        product.transitionTo(ProductStatus.AWAITING_TRACK_VALIDATION, null);
        product.transitionTo(ProductStatus.VALIDATED, null);
        product.transitionTo(ProductStatus.PUBLISHED, null);
        product.transitionTo(ProductStatus.TAKEN_DOWN, null);
        product.transitionTo(ProductStatus.RETIRED, null);
        assertThatThrownBy(() -> product.transitionTo(ProductStatus.PUBLISHED, null))
                .isInstanceOf(IllegalStateException.class)
                .hasMessageContaining("Invalid status transition");
    }
}
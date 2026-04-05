package com.productcatalog.domain.model;

import org.junit.jupiter.api.Test;

import static com.productcatalog.ValidationBuilders.validTrack;
import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

class TrackTest {

    @Test
    void shouldTransitionToValidatedWhenCurrentStatusIsPending() {
        Track track = validTrack();
        track.transitionTo(TrackStatus.VALIDATED);
        assertThat(track.getStatus()).isEqualTo(TrackStatus.VALIDATED);
    }

    @Test
    void shouldTransitionToValidationFailedWhenCurrentStatusIsPending() {
        Track track = validTrack();
        track.transitionTo(TrackStatus.VALIDATION_FAILED);
        assertThat(track.getStatus()).isEqualTo(TrackStatus.VALIDATION_FAILED);
    }

    @Test
    void shouldTransitionToNeedsReviewWhenCurrentStatusIsPending() {
        Track track = validTrack();
        track.transitionTo(TrackStatus.NEEDS_REVIEW);
        assertThat(track.getStatus()).isEqualTo(TrackStatus.NEEDS_REVIEW);
    }

    @Test
    void shouldTransitionToPendingWhenCurrentStatusIsValidationFailed() {
        Track track = validTrack();
        track.transitionTo(TrackStatus.VALIDATION_FAILED);
        track.transitionTo(TrackStatus.PENDING);
        assertThat(track.getStatus()).isEqualTo(TrackStatus.PENDING);
    }

    @Test
    void shouldTransitionToPendingWhenCurrentStatusIsNeedsReview() {
        Track track = validTrack();
        track.transitionTo(TrackStatus.NEEDS_REVIEW);
        track.transitionTo(TrackStatus.PENDING);
        assertThat(track.getStatus()).isEqualTo(TrackStatus.PENDING);
    }

    @Test
    void shouldTransitionToPublishedWhenCurrentStatusIsValidated() {
        Track track = validTrack();
        track.transitionTo(TrackStatus.VALIDATED);
        track.transitionTo(TrackStatus.PUBLISHED);
        assertThat(track.getStatus()).isEqualTo(TrackStatus.PUBLISHED);
    }

    @Test
    void shouldTransitionToTakenDownWhenCurrentStatusIsPublished() {
        Track track = validTrack();
        track.transitionTo(TrackStatus.VALIDATED);
        track.transitionTo(TrackStatus.PUBLISHED);
        track.transitionTo(TrackStatus.TAKEN_DOWN);
        assertThat(track.getStatus()).isEqualTo(TrackStatus.TAKEN_DOWN);
    }

    @Test
    void shouldTransitionToRetiredWhenCurrentStatusIsTakenDown() {
        Track track = validTrack();
        track.transitionTo(TrackStatus.VALIDATED);
        track.transitionTo(TrackStatus.PUBLISHED);
        track.transitionTo(TrackStatus.TAKEN_DOWN);
        track.transitionTo(TrackStatus.RETIRED);
        assertThat(track.getStatus()).isEqualTo(TrackStatus.RETIRED);
    }

    @Test
    void shouldThrowExceptionWhenTransitionIsInvalidFromPending() {
        Track track = validTrack();
        assertThatThrownBy(() -> track.transitionTo(TrackStatus.PUBLISHED))
                .isInstanceOf(IllegalStateException.class)
                .hasMessageContaining("Invalid track status transition");
    }

    @Test
    void shouldThrowExceptionWhenTransitionIsInvalidFromRetired() {
        Track track = validTrack();
        track.transitionTo(TrackStatus.VALIDATED);
        track.transitionTo(TrackStatus.PUBLISHED);
        track.transitionTo(TrackStatus.TAKEN_DOWN);
        track.transitionTo(TrackStatus.RETIRED);
        assertThatThrownBy(() -> track.transitionTo(TrackStatus.PENDING))
                .isInstanceOf(IllegalStateException.class)
                .hasMessageContaining("Invalid track status transition");
    }

    @Test
    void shouldThrowExceptionWhenTransitionIsInvalidFromValidated() {
        Track track = validTrack();
        track.transitionTo(TrackStatus.VALIDATED);
        assertThatThrownBy(() -> track.transitionTo(TrackStatus.NEEDS_REVIEW))
                .isInstanceOf(IllegalStateException.class)
                .hasMessageContaining("Invalid track status transition");
    }
}
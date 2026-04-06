package com.productcatalog.application.rest.dtos;

import com.productcatalog.domain.model.OwnershipSplit;
import com.productcatalog.domain.model.ProductStatus;
import com.productcatalog.domain.model.Track;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

import java.time.LocalDate;
import java.util.List;
import java.util.UUID;

@Getter
@AllArgsConstructor
@Builder
public class ProductResponseDto {
    private final UUID id;
    private final String upc;
    private final String title;
    private final String artist;
    private final String label;
    private final List<Track> tracks;
    private final LocalDate releaseDate;
    private final String genre;
    private final String language;
    private final List<OwnershipSplit> ownershipSplits;
    private final String artworkUri;
    private final List<String> dspTargets;
    private ProductStatus status;

    public boolean isExplicit() {
        return tracks != null && tracks.stream().anyMatch(Track::isExplicit);
    }
}

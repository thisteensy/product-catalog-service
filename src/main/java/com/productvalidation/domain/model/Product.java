package com.productvalidation.domain.model;

import java.time.LocalDate;
import java.util.List;
import java.util.UUID;

public class Product {

    private final UUID id;
    private final String upc;
    private final String isrc;
    private final String title;
    private final List<ProductContributor> contributors;
    private final LocalDate releaseDate;
    private final String genre;
    private final boolean explicit;
    private final String language;
    private final List<OwnershipSplit> ownershipSplits;
    private final String audioFileUri;
    private final String artworkUri;
    private final List<String> dspTargets;
    private ProductStatus status;

    public Product(UUID id, String upc, String isrc, String title,
                   List<ProductContributor> contributors, LocalDate releaseDate,
                   String genre, boolean explicit, String language,
                   List<OwnershipSplit> ownershipSplits, String audioFileUri,
                   String artworkUri, List<String> dspTargets, ProductStatus status) {
        this.id = id;
        this.upc = upc;
        this.isrc = isrc;
        this.title = title;
        this.contributors = contributors;
        this.releaseDate = releaseDate;
        this.genre = genre;
        this.explicit = explicit;
        this.language = language;
        this.ownershipSplits = ownershipSplits;
        this.audioFileUri = audioFileUri;
        this.artworkUri = artworkUri;
        this.dspTargets = dspTargets;
        this.status = status;
    }

    public void updateStatus(ProductStatus status) {
        this.status = status;
    }

    public UUID getId() { return id; }
    public String getUpc() { return upc; }
    public String getIsrc() { return isrc; }
    public String getTitle() { return title; }
    public List<ProductContributor> getContributors() { return contributors; }
    public LocalDate getReleaseDate() { return releaseDate; }
    public String getGenre() { return genre; }
    public boolean isExplicit() { return explicit; }
    public String getLanguage() { return language; }
    public List<OwnershipSplit> getOwnershipSplits() { return ownershipSplits; }
    public String getAudioFileUri() { return audioFileUri; }
    public String getArtworkUri() { return artworkUri; }
    public List<String> getDspTargets() { return dspTargets; }
    public ProductStatus getStatus() { return status; }
}
package com.productvalidation.infrastructure.persistence;

import jakarta.persistence.*;
import java.time.LocalDate;

@Entity
@Table(name = "products")
public class ProductEntity {

    @Id
    @Column(name = "id", length = 36)
    private String id;

    @Column(name = "upc")
    private String upc;

    @Column(name = "isrc")
    private String isrc;

    @Column(name = "title")
    private String title;

    @Column(name = "contributors", columnDefinition = "JSON")
    private String contributors;

    @Column(name = "release_date")
    private LocalDate releaseDate;

    @Column(name = "genre")
    private String genre;

    @Column(name = "explicit")
    private boolean explicit;

    @Column(name = "language")
    private String language;

    @Column(name = "ownership_splits", columnDefinition = "JSON")
    private String ownershipSplits;

    @Column(name = "audio_file_uri")
    private String audioFileUri;

    @Column(name = "artwork_uri")
    private String artworkUri;

    @Column(name = "dsp_targets", columnDefinition = "JSON")
    private String dspTargets;

    @Column(name = "status")
    private String status;

    protected ProductEntity() {}

    public ProductEntity(String id, String upc, String isrc, String title,
                         String contributors, LocalDate releaseDate, String genre,
                         boolean explicit, String language, String ownershipSplits,
                         String audioFileUri, String artworkUri, String dspTargets,
                         String status) {
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

    public String getId() { return id; }
    public String getUpc() { return upc; }
    public String getIsrc() { return isrc; }
    public String getTitle() { return title; }
    public String getContributors() { return contributors; }
    public LocalDate getReleaseDate() { return releaseDate; }
    public String getGenre() { return genre; }
    public boolean isExplicit() { return explicit; }
    public String getLanguage() { return language; }
    public String getOwnershipSplits() { return ownershipSplits; }
    public String getAudioFileUri() { return audioFileUri; }
    public String getArtworkUri() { return artworkUri; }
    public String getDspTargets() { return dspTargets; }
    public String getStatus() { return status; }

    public void setStatus(String status) { this.status = status; }
}
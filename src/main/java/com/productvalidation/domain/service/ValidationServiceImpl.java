package com.productvalidation.domain.service;

import com.productvalidation.domain.model.ContributorRole;
import com.productvalidation.domain.model.OwnershipSplit;
import com.productvalidation.domain.model.Product;
import com.productvalidation.domain.model.ProductContributor;
import com.productvalidation.domain.model.RuleResult;
import com.productvalidation.domain.model.RuleSeverity;
import com.productvalidation.domain.model.ValidationResult;
import com.productvalidation.domain.ports.RuleEngine;
import com.productvalidation.domain.ports.ValidationService;

import java.util.ArrayList;
import java.util.List;

public class ValidationServiceImpl implements ValidationService {

    private final RuleEngine ruleEngine;

    public ValidationServiceImpl(RuleEngine ruleEngine) {
        this.ruleEngine = ruleEngine;
    }

    @Override
    public ValidationResult validate(Product product) {
        Product sanitized = sanitize(product);

        List<RuleResult> inspectionResults = inspect(sanitized);
        if (inspectionResults.stream().anyMatch(r -> r.getSeverity() == RuleSeverity.BLOCKING)) {
            return new ValidationResult(sanitized, inspectionResults);
        }

        return new ValidationResult(sanitized, ruleEngine.evaluate(sanitized));
    }

    private List<RuleResult> inspect(Product product) {
        List<RuleResult> results = new ArrayList<>();

        // Title not blank
        if (product.getTitle() == null || product.getTitle().isBlank()) {
            results.add(new RuleResult("TitleRule", RuleSeverity.BLOCKING, "Title must not be blank"));
        }

        // ISRC format -- two uppercase letters, three alphanumeric, seven digits
        if (product.getIsrc() == null || !product.getIsrc().matches("[A-Z]{2}[A-Z0-9]{3}[0-9]{7}")) {
            results.add(new RuleResult("IsrcFormatRule", RuleSeverity.BLOCKING, "ISRC must match format: two uppercase letters, three alphanumeric, seven digits"));
        }

        // UPC not blank
        if (product.getUpc() == null || product.getUpc().isBlank()) {
            results.add(new RuleResult("UpcRule", RuleSeverity.BLOCKING, "UPC must not be blank"));
        }

        // At least one contributor with MAIN_ARTIST role
        boolean hasMainArtist = product.getContributors() != null && product.getContributors().stream()
                .anyMatch(c -> c.getRole() == ContributorRole.MAIN_ARTIST);
        if (!hasMainArtist) {
            results.add(new RuleResult("MainArtistRule", RuleSeverity.BLOCKING, "At least one contributor must have the MAIN_ARTIST role"));
        }

        // Ownership splits present and sum to 100%
        boolean splitsValid = product.getOwnershipSplits() != null
                && !product.getOwnershipSplits().isEmpty()
                && Math.abs(product.getOwnershipSplits().stream()
                .mapToDouble(OwnershipSplit::getPercentage)
                .sum() - 100.0) < 0.001;
        if (!splitsValid) {
            results.add(new RuleResult("OwnershipSplitRule", RuleSeverity.BLOCKING, "Ownership splits must be present and sum to 100%"));
        }

        // At least one DSP target
        if (product.getDspTargets() == null || product.getDspTargets().isEmpty()) {
            results.add(new RuleResult("DspTargetRule", RuleSeverity.BLOCKING, "At least one DSP target must be specified"));
        }

        // Audio file URI present
        if (product.getAudioFileUri() == null || product.getAudioFileUri().isBlank()) {
            results.add(new RuleResult("AudioFileRule", RuleSeverity.BLOCKING, "Audio file URI must be present"));
        }

        return results;
    }

    private Product sanitize(Product product) {
        return new Product(
                product.getId(),
                product.getUpc() == null ? null : product.getUpc().strip().replace("-", "").replace(" ", ""),
                product.getIsrc() == null ? null : product.getIsrc().strip().toUpperCase(),
                product.getTitle() == null ? null : product.getTitle().strip(),
                product.getContributors() == null ? null : product.getContributors().stream()
                        .map(c -> new ProductContributor(c.getName() == null ? null : c.getName().strip(), c.getRole()))
                        .toList(),
                product.getReleaseDate(),
                product.getGenre() == null ? null : product.getGenre().strip(),
                product.isExplicit(),
                product.getLanguage() == null ? null : product.getLanguage().strip().toLowerCase(),
                product.getOwnershipSplits() == null ? null : product.getOwnershipSplits().stream()
                        .map(o -> new OwnershipSplit(o.getRightsHolder() == null ? null : o.getRightsHolder().strip(), o.getPercentage()))
                        .toList(),
                product.getAudioFileUri() == null ? null : product.getAudioFileUri().strip(),
                product.getArtworkUri() == null ? null : product.getArtworkUri().strip(),
                product.getDspTargets() == null ? null : product.getDspTargets().stream()
                        .map(t -> t == null ? null : t.strip().toLowerCase())
                        .toList(),
                product.getStatus()
        );
    }
}
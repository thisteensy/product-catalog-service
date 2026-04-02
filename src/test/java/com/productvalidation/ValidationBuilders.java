package com.productvalidation;

import com.productvalidation.domain.model.ContributorRole;
import com.productvalidation.domain.model.OwnershipSplit;
import com.productvalidation.domain.model.Product;
import com.productvalidation.domain.model.ProductContributor;
import com.productvalidation.domain.model.ProductStatus;
import com.productvalidation.domain.model.RuleResult;
import com.productvalidation.domain.model.RuleSeverity;

import java.time.LocalDate;
import java.util.List;
import java.util.UUID;

public class ValidationBuilders {

    public static Product validProduct() {
        return new Product(
                UUID.randomUUID(),
                "012345678905",
                "USRC17607839",
                "Thriller",
                List.of(new ProductContributor("Michael Jackson", ContributorRole.MAIN_ARTIST)),
                LocalDate.of(1982, 11, 30),
                "Pop",
                false,
                "en",
                List.of(new OwnershipSplit("MJ Estate", 100.0)),
                "s3://audio/thriller.wav",
                "s3://artwork/thriller.jpg",
                List.of("spotify", "apple_music"),
                ProductStatus.SUBMITTED
        );
    }

    public static Product invalidProduct() {
        return new Product(
                UUID.randomUUID(),
                "",
                "FAKE12345",
                "",
                List.of(),
                null,
                null,
                false,
                null,
                List.of(),
                null,
                null,
                List.of(),
                ProductStatus.SUBMITTED
        );
    }

    public static List<RuleResult> passingRuleResults() {
        return List.of(
                new RuleResult("IsrcFormatRule", RuleSeverity.PASS, "ISRC format is valid"),
                new RuleResult("TitleRule", RuleSeverity.PASS, "Title is present"),
                new RuleResult("ContributorRule", RuleSeverity.PASS, "At least one contributor present")
        );
    }

    public static List<RuleResult> warningRuleResults() {
        return List.of(
                new RuleResult("IsrcFormatRule", RuleSeverity.PASS, "ISRC format is valid"),
                new RuleResult("ArtworkRule", RuleSeverity.WARNING, "Artwork resolution below recommended 3000x3000"),
                new RuleResult("ContributorRule", RuleSeverity.PASS, "At least one contributor present")
        );
    }

    public static List<RuleResult> failingRuleResults() {
        return List.of(
                new RuleResult("IsrcFormatRule", RuleSeverity.BLOCKING, "ISRC format is invalid"),
                new RuleResult("TitleRule", RuleSeverity.BLOCKING, "Title is missing"),
                new RuleResult("ContributorRule", RuleSeverity.PASS, "At least one contributor present")
        );
    }

}

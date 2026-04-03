package com.productvalidation.infrastructure;

import com.productvalidation.domain.model.RuleResult;
import com.productvalidation.domain.model.RuleSeverity;
import com.productvalidation.infrastructure.rules.SpotifyRules;
import org.junit.jupiter.api.Test;

import java.util.List;

import static com.productvalidation.ValidationBuilders.validProduct;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class SpotifyRulesTest {

    private final SpotifyRules spotifyRules = new SpotifyRules();

    @Test
    void shouldPassWhenProductIsValid() {
        List<RuleResult> results = spotifyRules.evaluate(validProduct());

        assertTrue(results.stream().allMatch(r -> r.getSeverity() == RuleSeverity.PASS));
    }

    @Test
    void shouldFailWhenLanguageIsMissing() {
        List<RuleResult> results = spotifyRules.evaluate(validProduct().toBuilder()
                .language(null)
                .build());

        assertTrue(results.stream()
                .filter(r -> r.getRuleName().equals("SpotifyLanguageRule"))
                .allMatch(r -> r.getSeverity() == RuleSeverity.BLOCKING));
    }
}
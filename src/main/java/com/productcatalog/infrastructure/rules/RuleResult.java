package com.productcatalog.infrastructure.rules;

import com.productcatalog.domain.model.ValidationOutcome;
import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.List;

@Data
@AllArgsConstructor
public class RuleResult {

    private final String ruleName;
    private final RuleSeverity severity;
    private final String message;

    public static ValidationOutcome resolve(List<RuleResult> results) {
        if (results.stream().anyMatch(r -> r.getSeverity() == RuleSeverity.BLOCKING)) {
            return ValidationOutcome.FAILED;
        }
        if (results.stream().anyMatch(r -> r.getSeverity() == RuleSeverity.WARNING)) {
            return ValidationOutcome.NEEDS_REVIEW;
        }
        return ValidationOutcome.PASSED;
    }
}
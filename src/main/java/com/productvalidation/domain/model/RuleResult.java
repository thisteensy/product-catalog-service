package com.productvalidation.domain.model;

public class RuleResult {

    private final String ruleName;
    private final RuleSeverity severity;
    private final String message;

    public RuleResult(String ruleName, RuleSeverity severity, String message) {
        this.ruleName = ruleName;
        this.severity = severity;
        this.message = message;
    }

    public String getRuleName() { return ruleName; }
    public RuleSeverity getSeverity() { return severity; }
    public String getMessage() { return message; }
}
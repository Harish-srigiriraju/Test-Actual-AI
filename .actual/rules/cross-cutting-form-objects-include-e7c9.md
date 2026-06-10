# Enforce Input Validation Using Dedicated Form Objects with Validation Annotations: Form Objects Include

These rules are ALWAYS ACTIVE for all web controller methods, REST API endpoints, form submission handlers, and any component that accepts user input from untrusted external sources.

### Rules

- **R-FORM-001** SHOULD: Form objects SHOULD include custom validation messages that provide clear guidance to users about acceptable input formats.

### Verify

```bash
# Count @Valid and @Validated annotations in controller classes
grep -r '@Valid\|@Validated' --include='*Controller.java' | wc -l

# Find form objects lacking JSR-303 validation annotations
find . -name '*Form.java' -o -name '*Request.java' | xargs grep -L '@NotNull\|@NotEmpty\|@Size\|@Pattern' | wc -l

# Detect controller methods binding request parameters without @Valid or @Validated
grep -r 'public.*(@RequestBody\|@ModelAttribute)' --include='*Controller.java' | grep -v '@Valid' | grep -v '@Validated'
```

**Accept when:**
- All controller methods accepting user input have @Valid or @Validated annotations on form object parameters
- All form objects (*Form.java, *Request.java) contain at least one JSR-303 validation annotation
- No controller methods directly bind request parameters to domain entity classes
- Static analysis tools report zero violations of validation annotation requirements

<enforcement>
Claude Code MUST NOT skip or defer verification. All controller methods accepting form objects MUST be annotated with @Valid or @Validated. All form objects MUST include at least one JSR-303 validation annotation. Violations block pull requests and trigger security team notification.
</enforcement>
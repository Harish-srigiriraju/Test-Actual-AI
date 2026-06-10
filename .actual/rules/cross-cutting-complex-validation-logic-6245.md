# Enforce Input Validation Using Dedicated Form Objects with Validation Annotations: Complex Validation Logic

These rules are ALWAYS ACTIVE for all web controller methods, API endpoints, form processing components, and any code that accepts external user input from untrusted sources.

### Rules

- **R-VALIDATION-001** SHOULD: Complex validation logic that cannot be expressed declaratively SHOULD be implemented as custom validators implementing ConstraintValidator interface.

### Verify

```bash
# Count @Valid and @Validated annotations in controller classes
grep -r '@Valid\|@Validated' --include='*Controller.java' | wc -l

# Find form objects lacking JSR-303 validation annotations
find . -name '*Form.java' -o -name '*Request.java' | xargs grep -L '@NotNull\|@NotEmpty\|@Size\|@Pattern' | wc -l

# Detect controller methods with @RequestBody/@ModelAttribute but missing @Valid/@Validated
grep -r 'public.*(@RequestBody\|@ModelAttribute)' --include='*Controller.java' | grep -v '@Valid' | grep -v '@Validated'
```

**Accept when:**
- All controller methods accepting user input have @Valid or @Validated annotations on form object parameters
- All form objects (*Form.java, *Request.java) contain at least one JSR-303 validation annotation
- No controller methods directly bind request parameters to domain entity classes
- Static analysis tools report zero violations of validation annotation requirements

<enforcement>
Claude Code MUST NOT skip or defer verification. All controller methods accepting external input MUST be validated using dedicated form objects with JSR-303 annotations. Violations block CI pipeline and require security team approval for exceptions.
</enforcement>
# Enforce Input Validation Using Dedicated Form Objects with Validation Annotations: Domain Entities Not

These rules are ALWAYS ACTIVE for all web controllers, API endpoints, form processing components, and any code that accepts external user input from untrusted sources.

### Rules

- **R-VALIDATION-001** MUST_NOT: Domain entities MUST NOT be directly bound to HTTP request parameters without an intermediate validated form object.

### Verify

```bash
# Count @Valid and @Validated annotations in controller files
grep -r '@Valid\|@Validated' --include='*Controller.java' | wc -l

# Find form objects lacking JSR-303 validation annotations
find . -name '*Form.java' -o -name '*Request.java' | xargs grep -L '@NotNull\|@NotEmpty\|@Size\|@Pattern' | wc -l

# Detect controller methods with @RequestBody/@ModelAttribute but no @Valid/@Validated
grep -r 'public.*(@RequestBody\|@ModelAttribute)' --include='*Controller.java' | grep -v '@Valid' | grep -v '@Validated'
```

**Accept when:**
- All controller methods accepting user input have @Valid or @Validated annotations on form object parameters
- All form objects (*Form.java, *Request.java) contain at least one JSR-303 validation annotation (@NotNull, @NotEmpty, @Size, @Pattern, etc.)
- No controller methods directly bind request parameters to domain entity classes
- Static analysis tools report zero violations of validation annotation requirements
- No grep results returned from the third verify command (no unvalidated @RequestBody/@ModelAttribute parameters)

<enforcement>
Claude Code MUST NOT skip or defer verification of these rules. All controller methods accepting external input MUST use dedicated form objects with validation annotations. Violations block CI pipeline and require security team approval for exceptions.
</enforcement>
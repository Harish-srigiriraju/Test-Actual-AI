# Enforce Input Validation Using Dedicated Form Objects with Validation Annotations: Form Objects Declare

These rules are ALWAYS ACTIVE for all web controllers, API endpoints, form processing components, and any code that accepts external user input through HTTP requests, form submissions, or API request bodies.

### Rules

- **R-FORM-001** MUST: Form objects MUST declare validation constraints using JSR-303/JSR-380 annotations (@NotNull, @NotEmpty, @Size, @Pattern, @Email, etc.) on all fields that accept user input.
- **R-FORM-002** MUST: All controller methods accepting user input via @RequestBody or @ModelAttribute MUST apply @Valid or @Validated annotations to form object parameters to enforce framework-level validation.
- **R-FORM-003** MUST: Form objects MUST NOT directly bind request parameters to domain entity classes; dedicated form objects (*Form.java, *Request.java, *DTO.java) MUST be used as intermediaries.
- **R-FORM-004** SHOULD: Form objects SHOULD use validation groups (JSR-303 groups) to handle different validation requirements for create versus update operations on the same form object.
- **R-FORM-005** SHOULD: A global exception handler (@ControllerAdvice) SHOULD be configured to translate MethodArgumentNotValidException into user-friendly error responses with field-level error details.

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
- All form objects (*Form.java, *Request.java) contain at least one JSR-303 validation annotation on fields accepting external input
- No controller methods directly bind request parameters to domain entity classes
- Static analysis tools report zero violations of validation annotation requirements on in-scope files
- Verification commands return zero violations or expected baseline counts

<enforcement>
Claude Code MUST NOT skip or defer verification of these rules. All form objects and controller methods in scope MUST be checked for compliance with R-FORM-001 through R-FORM-005 before code is considered complete.
</enforcement>
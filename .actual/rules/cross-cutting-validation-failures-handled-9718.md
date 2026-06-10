# Enforce Input Validation Using Dedicated Form Objects with Validation Annotations: Validation Failures Handled

These rules are ALWAYS ACTIVE for all web controller methods, REST API endpoints, form submission handlers, and any component that accepts user input from untrusted external sources.

### Rules

- **R-VAL-001** MUST: Validation failures MUST be handled gracefully with appropriate error messages returned to the client without exposing internal system details.
- **R-VAL-002** MUST: All controller methods accepting user input MUST use dedicated form objects (not domain entities) with JSR-303/JSR-380 validation annotations.
- **R-VAL-003** MUST: All controller method parameters accepting form objects MUST be annotated with @Valid or @Validated to enforce framework-level validation.
- **R-VAL-004** MUST: All form objects (*Form.java, *Request.java, *DTO.java) MUST contain at least one JSR-303 validation annotation (@NotNull, @NotEmpty, @Size, @Pattern, etc.).
- **R-VAL-005** MUST: No controller methods SHALL directly bind request parameters to domain entity classes; form objects MUST serve as the validation boundary.
- **R-VAL-006** MUST: A global exception handler (@ControllerAdvice) MUST translate MethodArgumentNotValidException into user-friendly error responses with field-level error details.
- **R-VAL-007** SHOULD: Establish naming conventions for form objects (e.g., *Form, *Request, *DTO) to make them easily identifiable in the codebase.
- **R-VAL-008** SHOULD: Use validation groups (JSR-303 groups) to handle different validation requirements for create vs. update operations on the same form object.

### Verify

```bash
# Count @Valid and @Validated annotations in controller classes
grep -r '@Valid\|@Validated' --include='*Controller.java' | wc -l

# Find form objects lacking validation annotations
find . -name '*Form.java' -o -name '*Request.java' | xargs grep -L '@NotNull\|@NotEmpty\|@Size\|@Pattern' | wc -l

# Detect controller methods with @RequestBody/@ModelAttribute but no @Valid/@Validated
grep -r 'public.*(@RequestBody\|@ModelAttribute)' --include='*Controller.java' | grep -v '@Valid' | grep -v '@Validated'
```

**Accept when:**
- All controller methods accepting user input have @Valid or @Validated annotations on form object parameters
- All form objects (*Form.java, *Request.java) contain at least one JSR-303 validation annotation
- No controller methods directly bind request parameters to domain entity classes
- Static analysis tools report zero violations of validation annotation requirements
- A global @ControllerAdvice exception handler exists to handle MethodArgumentNotValidException
- Error responses do not expose internal system details or stack traces to clients

<enforcement>
Claude Code MUST NOT skip or defer verification. All rules in this file are mandatory for code accepting external user input. Violations block CI pipeline and require security team approval for exceptions.
</enforcement>
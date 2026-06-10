# Enforce Input Validation Using Dedicated Form Objects with Validation Annotations: User Input Web

These rules are ALWAYS ACTIVE for all user input handling in web controllers, API endpoints, and form processing components that accept external input.

### Rules

- **R-INPUT-001** MUST: All user input from web forms, API requests, and external interfaces MUST be captured in dedicated form objects (DTOs) separate from domain entities.
- **R-INPUT-002** MUST: All controller methods accepting user input MUST have @Valid or @Validated annotations on form object parameters.
- **R-INPUT-003** MUST: All form objects (*Form.java, *Request.java, *DTO.java) MUST contain at least one JSR-303/JSR-380 validation annotation (@NotNull, @NotEmpty, @Size, @Pattern, etc.).
- **R-INPUT-004** MUST: No controller methods shall directly bind request parameters to domain entity classes.
- **R-INPUT-005** SHOULD: Use validation groups (JSR-303 groups) to handle different validation requirements for create vs. update operations on the same form object.
- **R-INPUT-006** SHOULD: Configure global exception handler (@ControllerAdvice) to translate MethodArgumentNotValidException into user-friendly error responses with field-level error details.

### Verify

```bash
# Count @Valid and @Validated annotations in controllers
grep -r '@Valid\|@Validated' --include='*Controller.java' | wc -l

# Find form objects lacking validation annotations
find . -name '*Form.java' -o -name '*Request.java' -o -name '*DTO.java' | xargs grep -L '@NotNull\|@NotEmpty\|@Size\|@Pattern\|@Email\|@Min\|@Max' | wc -l

# Detect controller methods with @RequestBody/@ModelAttribute but no @Valid/@Validated
grep -r 'public.*(@RequestBody\|@ModelAttribute)' --include='*Controller.java' | grep -v '@Valid' | grep -v '@Validated'
```

**Accept when:**
- All controller methods accepting user input have @Valid or @Validated annotations on form object parameters
- All form objects (*Form.java, *Request.java, *DTO.java) contain at least one JSR-303/JSR-380 validation annotation
- No controller methods directly bind request parameters to domain entity classes
- Static analysis tools report zero violations of validation annotation requirements
- Grep for form objects without validation annotations returns empty result

<enforcement>
Claude Code MUST NOT skip or defer verification. All controller methods accepting external input MUST be validated using dedicated form objects with JSR-303/JSR-380 annotations before business logic execution.
</enforcement>
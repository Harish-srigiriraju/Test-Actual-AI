# Adopt Validation Group Pattern for Public API Contract Enforcement: Controller Methods Handling

These rules are ALWAYS ACTIVE for all public-facing API endpoints, external integration points, and controller methods handling public API requests.

### Rules

- **R-VALIDATION-001** MUST: Controller methods handling public API requests MUST specify the @Validated annotation with appropriate validation groups.
- **R-VALIDATION-002** MUST: Create validation group interfaces as marker interfaces (empty interfaces) in the validation.groups package following the {Action}{EntityType} naming convention.
- **R-VALIDATION-003** MUST: Annotate controller method parameters with @Validated(ValidationGroup.class) to activate group-specific validation.
- **R-VALIDATION-004** MUST: Use the 'groups' attribute on constraint annotations (@NotNull(groups = CreateCorporateCustomer.class)) to associate validations with specific groups.
- **R-VALIDATION-005** SHOULD: Consider using Default.class group for validations that apply across all operations to avoid repetition.
- **R-VALIDATION-006** SHOULD: Document validation groups in API documentation (Swagger/OpenAPI) to make contracts explicit to consumers.

### Verify

```bash
# Count @Validated usage in controller files
grep -r '@Validated' --include='*Controller.java' | wc -l

# Count validation group interface files
find . -path '*/validation/groups/*.java' -type f | wc -l

# Count constraint annotations with groups attribute
grep -r 'groups\s*=' --include='*.java' | grep -E '@(NotNull|NotBlank|Size|Pattern|Valid)' | wc -l
```

**Accept when:**
- All public API controller methods use @Validated annotation with explicit validation groups
- At least one validation group interface exists per major entity type exposed through public APIs
- Validation constraints on public API DTOs specify appropriate groups attribute
- ArchUnit tests pass verifying validation group usage on public endpoints
- No public API endpoints bypass the validation framework

<enforcement>
Claude Code MUST NOT skip or defer verification. All public API controller methods MUST be checked for @Validated annotation compliance. Violations trigger CI pipeline failure and code review blocking.
</enforcement>
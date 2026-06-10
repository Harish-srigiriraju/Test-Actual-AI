# Adopt Validation Group Pattern for Public API Contract Enforcement: Each Distinct Operation

These rules are ALWAYS ACTIVE for all public-facing API endpoints, external integration points, DTO classes, form objects, and entity classes used in public API request/response payloads.

### Rules

- **R-VAL-001** MUST: Each distinct operation context (e.g., CreateCorporateCustomer, UpdateRetailCustomer) MUST have its own validation group interface.
- **R-VAL-002** MUST: All REST API controllers exposing public endpoints MUST use @Validated annotation with explicit validation groups on method parameters.
- **R-VAL-003** MUST: Validation constraint annotations on fields used in external contracts MUST specify the 'groups' attribute to associate validations with specific operation groups.
- **R-VAL-004** SHOULD: Create validation group interfaces as marker interfaces (empty interfaces) in the validation.groups package following the {Action}{EntityType} naming convention.
- **R-VAL-005** SHOULD: Use the Default.class group for validations that apply across all operations to avoid repetition.
- **R-VAL-006** SHOULD: Document validation groups in API documentation (Swagger/OpenAPI) to make contracts explicit to consumers.

### Verify

```bash
# Count @Validated annotations in controller classes
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
Claude Code MUST NOT skip or defer verification. All public API endpoints MUST be checked for @Validated annotation and appropriate validation group usage before code review approval. Violations trigger CI pipeline failure and security review.
</enforcement>
# Adopt Validation Group Pattern for Public API Contract Enforcement: Public Validation Groups

These rules are ALWAYS ACTIVE for all REST API controllers exposing public endpoints, DTO classes and form objects used in public API request/response payloads, entity classes when directly exposed through public APIs, and validation constraint annotations on fields used in external contracts.

### Rules

- **R-PUB-VAL-001** MUST NOT: Public API validation groups MUST NOT be reused for internal service-to-service validation to maintain boundary separation.
- **R-PUB-VAL-002** MUST: All public API controller methods use @Validated annotation with explicit validation groups.
- **R-PUB-VAL-003** MUST: Create validation group interfaces as marker interfaces (empty interfaces) in the validation.groups package following the {Action}{EntityType} naming convention.
- **R-PUB-VAL-004** MUST: Annotate controller method parameters with @Validated(ValidationGroup.class) to activate group-specific validation.
- **R-PUB-VAL-005** MUST: Use the 'groups' attribute on constraint annotations (@NotNull(groups = CreateCorporateCustomer.class)) to associate validations with specific groups.
- **R-PUB-VAL-006** SHOULD: Consider using Default.class group for validations that apply across all operations to avoid repetition.
- **R-PUB-VAL-007** SHOULD: Document validation groups in API documentation (Swagger/OpenAPI) to make contracts explicit to consumers.
- **R-PUB-VAL-008** SHOULD: Establish naming conventions and periodic review of validation groups to consolidate similar groups, limiting to operation-based groups (Create, Update, Patch) combined with entity types.

### Verify

```bash
# Count @Validated annotations in public API controllers
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
- Invalid requests are rejected with appropriate error messages via integration tests
- No validation annotations without group specifications exist on public API classes

<enforcement>
Claude Code MUST NOT skip or defer verification of these rules. ArchUnit tests in CI pipeline MUST check for @Validated annotation on public API controllers. Code review MUST block merge if validation groups are missing from new public API endpoints. Security review MUST be triggered for any public API endpoint bypassing the validation framework.
</enforcement>
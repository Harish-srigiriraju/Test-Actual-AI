# Adopt Validation Group Pattern for Public API Contract Enforcement: Validation Groups Composed

These rules are ALWAYS ACTIVE for all public-facing API endpoints, external integration points, DTO classes, form objects used in public API request/response payloads, entity classes directly exposed through public APIs, and validation constraint annotations on fields used in external contracts.

### Rules

- **R-VG-001** MAY: Validation groups MAY be composed hierarchically for shared validation rules across related operations.
- **R-VG-002** MUST: All public API controller methods MUST use @Validated annotation with explicit validation groups.
- **R-VG-003** MUST: Validation group interfaces MUST be created as marker interfaces (empty interfaces) in the validation.groups package following the {Action}{EntityType} naming convention.
- **R-VG-004** MUST: Validation constraints on public API DTOs MUST specify appropriate groups attribute.
- **R-VG-005** SHOULD: Use the 'groups' attribute on constraint annotations (@NotNull(groups = CreateCorporateCustomer.class)) to associate validations with specific groups.
- **R-VG-006** SHOULD: Consider using Default.class group for validations that apply across all operations to avoid repetition.
- **R-VG-007** SHOULD: Document validation groups in API documentation (Swagger/OpenAPI) to make contracts explicit to consumers.

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
Claude Code MUST NOT skip or defer verification. All public API endpoints MUST be verified to use @Validated with appropriate validation groups before code review approval. ArchUnit tests in the CI pipeline MUST pass. Violations trigger CI pipeline failure, code review block, security review, and technical debt ticket creation based on API exposure level.
</enforcement>
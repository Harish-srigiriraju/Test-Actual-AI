# Adopt Validation Group Pattern for Public API Contract Enforcement: Public Endpoints Use

These rules are ALWAYS ACTIVE for all public-facing API endpoints, REST controllers, DTO classes, form objects, and entity classes used in external API contracts.

### Rules

- **R-VAL-001** MUST: All public API endpoints MUST use Bean Validation groups to define and enforce API contracts.
- **R-VAL-002** MUST: All public API controller methods MUST be annotated with @Validated with explicit validation groups.
- **R-VAL-003** MUST: Validation constraint annotations on public API DTOs and form objects MUST specify appropriate groups attribute.
- **R-VAL-004** MUST: Validation group interfaces MUST be created as marker interfaces in the validation.groups package following the {Action}{EntityType} naming convention.
- **R-VAL-005** SHOULD: Use the Default.class group for validations that apply across all operations to avoid repetition.
- **R-VAL-006** SHOULD: Document validation groups in API documentation (Swagger/OpenAPI) to make contracts explicit to consumers.
- **R-VAL-007** MAY: Simple CRUD endpoints with identical validation rules across all operations may be exempted (EX-001).
- **R-VAL-008** MAY: Internal admin APIs not exposed to external consumers may be exempted (EX-002).

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
- Invalid requests are rejected with appropriate error messages at the controller boundary
- No public API endpoints bypass the validation framework

<enforcement>
Claude Code MUST NOT skip or defer verification. All public API endpoints MUST be checked for @Validated annotation and explicit validation group usage before code review approval. Violations trigger CI pipeline failure and code review block.
</enforcement>
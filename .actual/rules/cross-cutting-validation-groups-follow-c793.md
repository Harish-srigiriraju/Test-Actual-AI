# Adopt Validation Group Pattern for Public API Contract Enforcement: Validation Groups Follow

These rules are ALWAYS ACTIVE for all public-facing API endpoints, external integration points, DTO classes, form objects, and entity classes used in public API request/response payloads.

### Rules

- **R-VAL-001** SHOULD: Validation groups SHOULD follow the naming convention: {Action}{EntityType} (e.g., CreateCorporateCustomer, UpdateAppointment).
- **R-VAL-002** MUST: All public API controller methods MUST use @Validated annotation with explicit validation groups.
- **R-VAL-003** MUST: Validation constraints on public API DTOs MUST specify appropriate groups attribute.
- **R-VAL-004** SHOULD: Validation group interfaces SHOULD be created as marker interfaces (empty interfaces) in the validation.groups package.
- **R-VAL-005** SHOULD: Controller method parameters SHOULD be annotated with @Validated(ValidationGroup.class) to activate group-specific validation.
- **R-VAL-006** SHOULD: The 'groups' attribute on constraint annotations SHOULD be used to associate validations with specific groups.
- **R-VAL-007** MAY: Default.class group MAY be used for validations that apply across all operations to avoid repetition.

### Verify

```bash
# Count @Validated usage in controllers
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
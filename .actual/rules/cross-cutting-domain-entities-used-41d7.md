# Adopt Validation Group Pattern for Public API Contract Enforcement: Domain Entities Used

These rules are ALWAYS ACTIVE for all REST API controllers exposing public endpoints, DTO classes and form objects used in public API request/response payloads, entity classes when directly exposed through public APIs, and validation constraint annotations on fields used in external contracts.

### Rules

- **R-VGROUP-001** SHOULD: Domain entities used in public APIs SHOULD separate validation constraints by group to avoid over-validation in internal operations.

### Verify

```bash
# Count @Validated annotations in controller classes
grep -r '@Validated' --include='*Controller.java' | wc -l

# Count validation group interface files
find . -path '*/validation/groups/*.java' -type f | wc -l

# Count validation constraints with groups attribute
grep -r 'groups\s*=' --include='*.java' | grep -E '@(NotNull|NotBlank|Size|Pattern|Valid)' | wc -l
```

**Accept when:**
- All public API controller methods use @Validated annotation with explicit validation groups
- At least one validation group interface exists per major entity type exposed through public APIs
- Validation constraints on public API DTOs specify appropriate groups attribute
- ArchUnit tests pass verifying validation group usage on public endpoints

<enforcement>
Claude Code MUST NOT skip or defer verification. All public API endpoints MUST be checked for @Validated annotation with explicit validation groups before approval.
</enforcement>
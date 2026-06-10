# Adopt Validation Group Pattern for Public API Contract Enforcement: Validation Group Interfaces

These rules are ALWAYS ACTIVE for all public-facing API endpoints, external integration points, DTO classes, form objects used in public API request/response payloads, entity classes directly exposed through public APIs, and validation constraint annotations on fields used in external contracts.

### Rules

- **R-VG-001** MUST: Validation group interfaces MUST be defined in a dedicated `validation.groups` package to maintain clear separation from domain entities.
- **R-VG-002** MUST: All public API controller methods MUST use `@Validated` annotation with explicit validation groups.
- **R-VG-003** MUST: Validation group interfaces MUST be created as marker interfaces (empty interfaces) following the `{Action}{EntityType}` naming convention (e.g., `CreateCorporateCustomer`, `UpdateRetailCustomer`).
- **R-VG-004** MUST: Validation constraints on public API DTOs and entities MUST specify appropriate groups attribute (e.g., `@NotNull(groups = CreateCorporateCustomer.class)`).
- **R-VG-005** SHOULD: Use `Default.class` group for validations that apply across all operations to avoid repetition.
- **R-VG-006** SHOULD: Establish naming conventions limiting validation groups to operation-based groups (Create, Update, Patch) combined with entity types to prevent proliferation.
- **R-VG-007** SHOULD: Document validation groups in API documentation (Swagger/OpenAPI) to make contracts explicit to consumers.

### Verify

```bash
# Count @Validated usage in public API controllers
grep -r '@Validated' --include='*Controller.java' | wc -l

# Count validation group interface files
find . -path '*/validation/groups/*.java' -type f | wc -l

# Count constraint annotations with groups attribute
grep -r 'groups\s*=' --include='*.java' | grep -E '@(NotNull|NotBlank|Size|Pattern|Valid)' | wc -l
```

**Accept when:**
- All public API controller methods use `@Validated` annotation with explicit validation groups
- At least one validation group interface exists per major entity type exposed through public APIs
- Validation constraints on public API DTOs specify appropriate groups attribute
- ArchUnit tests pass verifying validation group usage on public endpoints
- Invalid requests are rejected with appropriate error messages at the controller boundary
- No public API endpoints bypass the validation framework

<enforcement>
Claude Code MUST NOT skip or defer verification. ArchUnit tests in the CI pipeline MUST check for @Validated annotation on public API controllers. Code review MUST verify validation groups are present on new public API endpoints. Security review MUST be triggered for any public API endpoint bypassing validation framework. All violations MUST be addressed before merge.
</enforcement>
# Adopt Spring Data JPA Repository Pattern for Data Access Layer: Repository Interfaces Annotated

These rules are ALWAYS ACTIVE for all JPA entity classes requiring database persistence, CRUD operations, custom finder methods and queries, pagination and sorting operations, and transaction-managed data access.

### Rules

- **R-REPO-001** MUST: Repository interfaces MUST be annotated with @Repository or rely on Spring Data's component scanning.

### Verify

```bash
# Verify all repositories extend Spring Data JPA base interfaces
grep -r "extends JpaRepository\|extends CrudRepository\|extends PagingAndSortingRepository" --include="*Repository.java" .

# Count repository files in dao package
find . -name "*Repository.java" -path "*/dao/*" | wc -l

# Verify @Repository annotation usage
grep -r "@Repository" --include="*Repository.java" . | wc -l
```

**Accept when:**
- All entity classes have corresponding Repository interfaces extending Spring Data JPA base interfaces (JpaRepository, CrudRepository, or PagingAndSortingRepository)
- Repository interfaces are located in the dao package structure following naming convention {EntityName}Repository
- No direct EntityManager or JDBC usage exists in service layer for standard CRUD operations
- Custom query methods follow Spring Data naming conventions or use @Query annotations
- Repository interfaces are annotated with @Repository or discoverable through Spring Data's component scanning

<enforcement>
Claude Code MUST NOT skip or defer verification of repository pattern compliance. All violations must be flagged for refactoring before merge approval.
</enforcement>
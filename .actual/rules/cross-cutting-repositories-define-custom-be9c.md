# Adopt Spring Data JPA Repository Pattern for Data Access Layer: Repositories Define Custom

These rules are ALWAYS ACTIVE for all JPA entity classes requiring database persistence, CRUD operations, custom finder methods and queries, pagination and sorting operations, and transaction-managed data access within the application's data access layer.

### Rules

- **R-REPO-001** MAY: Repositories MAY define custom query methods beyond the standard CRUD operations provided by the base interface.

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
- Spring Data JPA repository scanning is enabled via @EnableJpaRepositories in application configuration

<enforcement>
Claude Code MUST NOT skip or defer verification of repository pattern compliance. All pull requests must pass the verify commands and meet accept criteria before approval.
</enforcement>
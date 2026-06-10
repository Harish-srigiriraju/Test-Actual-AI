# Adopt Spring Data JPA Repository Pattern for Data Access Layer: Complex Queries That

These rules are ALWAYS ACTIVE for all JPA entity classes requiring database persistence, CRUD operations, custom finder methods and queries, pagination and sorting operations, and transaction-managed data access.

### Rules

- **R-JPA-001** SHOULD: Complex queries that cannot be derived from method names SHOULD use @Query annotation with JPQL or native SQL.

### Verify

```bash
# Verify all repositories extend Spring Data JPA base interfaces
grep -r "extends JpaRepository\|extends CrudRepository\|extends PagingAndSortingRepository" --include="*Repository.java" .

# Count repository files in dao package
find . -name "*Repository.java" -path "*/dao/*" | wc -l

# Verify @Repository annotation usage
grep -r "@Repository" --include="*Repository.java" . | wc -l

# Check for direct EntityManager usage in service layer (should be minimal/none)
grep -r "EntityManager" --include="*Service.java" . | grep -v "@PersistenceContext" | wc -l
```

**Accept when:**
- All entity classes have corresponding Repository interfaces extending Spring Data JPA base interfaces (JpaRepository, CrudRepository, or PagingAndSortingRepository)
- Repository interfaces are located in the dao package structure following naming convention {EntityName}Repository
- No direct EntityManager or JDBC usage exists in service layer for standard CRUD operations
- Custom query methods follow Spring Data naming conventions or use @Query annotations with JPQL or native SQL
- Complex queries are explicitly marked with @Query annotation rather than relying on method name derivation

<enforcement>
Claude Code MUST NOT skip or defer verification. All repository patterns must be validated against the verify commands before accepting changes to data access layer code.
</enforcement>
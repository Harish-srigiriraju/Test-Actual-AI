# Adopt Spring Data JPA Repository Pattern for Data Access Layer: Custom Query Methods

These rules are ALWAYS ACTIVE for all JPA entity classes requiring database persistence, CRUD operations, custom finder methods and queries, pagination and sorting operations, and transaction-managed data access.

### Rules

- **R-JPADAO-001** SHOULD: Custom query methods SHOULD follow Spring Data JPA naming conventions for automatic query derivation.
- **R-JPADAO-002** MUST: All entity classes requiring database persistence MUST have corresponding Repository interfaces extending Spring Data JPA base interfaces (JpaRepository, CrudRepository, or PagingAndSortingRepository).
- **R-JPADAO-003** MUST: Repository interfaces MUST be located in the dao package structure following the convention com.petreach.appointmentscheduler.dao.
- **R-JPADAO-004** MUST: Repository naming convention MUST follow {EntityName}Repository pattern (e.g., AppointmentRepository for Appointment entity).
- **R-JPADAO-005** SHOULD: For custom queries, method name derivation SHOULD be preferred first, then @Query with JPQL, and finally native SQL only when necessary.
- **R-JPADAO-006** MUST: No direct EntityManager or JDBC usage MUST exist in service layer for standard CRUD operations.
- **R-JPADAO-007** SHOULD: Complex custom query methods SHOULD be documented with JavaDoc explaining the query purpose and any performance considerations.
- **R-JPADAO-008** SHOULD: N+1 query problems SHOULD be mitigated using @EntityGraph, JOIN FETCH in JPQL queries, or appropriate fetch type configuration.
- **R-JPADAO-009** SHOULD: Bulk operations exceeding 1000 records SHOULD consider native queries or batch processing configurations to avoid JPA overhead.

### Verify

```bash
# Verify all repositories extend Spring Data JPA base interfaces
grep -r "extends JpaRepository\|extends CrudRepository\|extends PagingAndSortingRepository" --include="*Repository.java" .

# Count repository files in dao package
find . -name "*Repository.java" -path "*/dao/*" | wc -l

# Verify @Repository annotation usage
grep -r "@Repository" --include="*Repository.java" . | wc -l

# Check for direct EntityManager usage in service layer
grep -r "EntityManager" --include="*Service.java" . | grep -v "import" | grep -v "//"

# Verify no JDBC usage in service layer
grep -r "DriverManager\|Connection\|Statement" --include="*Service.java" . | grep -v "import" | grep -v "//"
```

**Accept when:**
- All entity classes have corresponding Repository interfaces extending Spring Data JPA base interfaces
- Repository interfaces are located in the dao package structure
- No direct EntityManager or JDBC usage exists in service layer for standard CRUD operations
- Custom query methods follow Spring Data naming conventions or use @Query annotations
- Repository naming follows {EntityName}Repository convention
- Spring Data JPA repository scanning is enabled in the main application configuration class using @EnableJpaRepositories

<enforcement>
Claude Code MUST NOT skip or defer verification. All rules in this file are mandatory for code review and pull request approval.
</enforcement>
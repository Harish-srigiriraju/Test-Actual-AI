# Adopt Spring Data JPA Repository Pattern for Data Access Layer: Repository Interfaces Extend

These rules are ALWAYS ACTIVE for all JPA entity classes requiring database persistence and their corresponding Repository interfaces in the data access layer.

### Rules

- **R-REPO-001** MUST: Repository interfaces MUST extend one of the Spring Data repository interfaces (CrudRepository, JpaRepository, or PagingAndSortingRepository).
- **R-REPO-002** MUST: All Repository interfaces MUST be located in the dao package following the existing convention (e.g., com.petreach.appointmentscheduler.dao).
- **R-REPO-003** MUST: Repository interfaces MUST follow the naming convention {EntityName}Repository (e.g., AppointmentRepository for Appointment entity).
- **R-REPO-004** SHOULD: Use JpaRepository as the base interface for most cases as it provides the most comprehensive feature set including batch operations.
- **R-REPO-005** SHOULD: For custom queries, prefer method name derivation first, then @Query with JPQL, and finally native SQL only when necessary.
- **R-REPO-006** SHOULD: Complex custom query methods SHOULD be documented with JavaDoc explaining the query purpose and any performance considerations.
- **R-REPO-007** MAY: Bulk operations requiring native SQL for performance optimization MAY use direct SQL queries (EXC-001).
- **R-REPO-008** MAY: Complex reporting queries with database-specific features not supported by JPQL MAY use native SQL (EXC-002).

### Verify

```bash
# Verify all Repository interfaces extend Spring Data JPA base interfaces
grep -r "extends JpaRepository\|extends CrudRepository\|extends PagingAndSortingRepository" --include="*Repository.java" .

# Count Repository files in dao package
find . -name "*Repository.java" -path "*/dao/*" | wc -l

# Verify @Repository annotation usage
grep -r "@Repository" --include="*Repository.java" . | wc -l

# Check for direct EntityManager usage in service layer
grep -r "EntityManager" --include="*Service.java" . | grep -v "import" | grep -v "//"
```

**Accept when:**
- All entity classes have corresponding Repository interfaces extending Spring Data JPA base interfaces (CrudRepository, JpaRepository, or PagingAndSortingRepository)
- Repository interfaces are located in the dao package structure
- Repository interfaces follow the {EntityName}Repository naming convention
- No direct EntityManager or JDBC usage exists in service layer for standard CRUD operations
- Custom query methods follow Spring Data naming conventions or use @Query annotations
- Spring Data JPA repository scanning is enabled in the main application configuration class using @EnableJpaRepositories

<enforcement>
Claude Code MUST NOT skip or defer verification of Repository pattern compliance. All pull requests with direct database access bypassing repositories MUST be rejected. Code review checklist MUST include Repository pattern verification. Violations require refactoring before merge approval.
</enforcement>
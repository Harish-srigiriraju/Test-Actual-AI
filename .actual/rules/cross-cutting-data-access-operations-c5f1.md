# Adopt Spring Data JPA Repository Pattern for Data Access Layer: Data Access Operations

These rules are ALWAYS ACTIVE for all Java files in the data access layer, service layer, and any code performing database operations through JPA/Hibernate persistence.

### Rules

- **R-DAL-001** MUST: All data access operations MUST be performed through Spring Data JPA Repository interfaces extending JpaRepository, CrudRepository, or PagingAndSortingRepository.
- **R-DAL-002** MUST: All JPA entity classes requiring database persistence MUST have a corresponding Repository interface located in the dao package.
- **R-DAL-003** MUST: Repository interfaces MUST follow the naming convention {EntityName}Repository (e.g., AppointmentRepository for Appointment entity).
- **R-DAL-004** MUST: Service layer classes MUST NOT use direct EntityManager or JDBC operations for standard CRUD operations; all data access MUST be delegated to Repository interfaces.
- **R-DAL-005** SHOULD: Custom query methods SHOULD prefer method name derivation following Spring Data naming conventions before using @Query annotations.
- **R-DAL-006** SHOULD: Complex custom queries SHOULD use @Query with JPQL, and native SQL SHOULD only be used when necessary and documented.
- **R-DAL-007** SHOULD: Complex custom query methods SHOULD be documented with JavaDoc explaining query purpose and any performance considerations.
- **R-DAL-008** MAY: Native SQL queries MAY be used for bulk operations requiring performance optimization or database-specific features not supported by JPQL, provided they are documented with exception justification.

### Verify

```bash
# Verify all Repository interfaces extend Spring Data JPA base interfaces
grep -r "extends JpaRepository\|extends CrudRepository\|extends PagingAndSortingRepository" --include="*Repository.java" .

# Count Repository files in dao package
find . -name "*Repository.java" -path "*/dao/*" | wc -l

# Verify @Repository annotation usage
grep -r "@Repository" --include="*Repository.java" . | wc -l

# Check for direct EntityManager usage in service layer (should return no results)
grep -r "EntityManager" --include="*Service.java" . | grep -v "import" | grep -v "//"

# Check for direct JDBC usage in service layer (should return no results)
grep -r "DriverManager\|Connection\|Statement" --include="*Service.java" . | grep -v "import" | grep -v "//"
```

**Accept when:**
- All entity classes have corresponding Repository interfaces extending Spring Data JPA base interfaces
- Repository interfaces are located in the dao package structure
- No direct EntityManager or JDBC usage exists in service layer for standard CRUD operations
- Custom query methods follow Spring Data naming conventions or use @Query annotations
- Repository naming follows {EntityName}Repository convention consistently
- All CRUD operations are delegated to Repository interfaces from service classes

<enforcement>
Claude Code MUST NOT skip or defer verification. All data access operations MUST be reviewed for Repository pattern compliance before approval.
</enforcement>
# Adopt Spring Data JPA Repository Pattern for Data Access Layer: Each Jpa Entity

These rules are ALWAYS ACTIVE for all JPA entity classes and their corresponding data access layer implementations.

### Rules

- **R-JPA-001** MUST: Each JPA entity MUST have a corresponding Repository interface in the dao package.
- **R-JPA-002** MUST: Repository interfaces MUST extend Spring Data JPA base interfaces (JpaRepository, CrudRepository, or PagingAndSortingRepository).
- **R-JPA-003** MUST: Repository interfaces MUST follow the naming convention {EntityName}Repository (e.g., AppointmentRepository for Appointment entity).
- **R-JPA-004** MUST: Repository interfaces MUST be located in the dao package structure (e.g., com.petreach.appointmentscheduler.dao).
- **R-JPA-005** SHOULD: For custom queries, prefer method name derivation first, then @Query with JPQL, and finally native SQL only when necessary.
- **R-JPA-006** SHOULD: Complex custom query methods SHOULD be documented with JavaDoc explaining the query purpose and any performance considerations.
- **R-JPA-007** MAY: Native SQL queries and bulk operations MAY be used when performance optimization is critical (EXC-001) or for complex reporting queries with database-specific features (EXC-002).

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
```

**Accept when:**
- All entity classes have corresponding Repository interfaces extending Spring Data JPA base interfaces
- Repository interfaces are located in the dao package structure
- No direct EntityManager or JDBC usage exists in service layer for standard CRUD operations
- Custom query methods follow Spring Data naming conventions or use @Query annotations
- Repository naming follows {EntityName}Repository convention consistently
- Spring Data JPA repository scanning is enabled via @EnableJpaRepositories in application configuration

<enforcement>
Clause Code MUST NOT skip or defer verification of repository pattern compliance. All violations require refactoring before merge approval. Exception requests must include performance benchmarks or technical justification and be reviewed by technical lead.
</enforcement>
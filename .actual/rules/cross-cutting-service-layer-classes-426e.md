# Adopt Spring Data JPA Repository Pattern for Data Access Layer: Service Layer Classes

These rules are ALWAYS ACTIVE for all service layer classes and data access implementations in Spring Boot applications using JPA/Hibernate persistence.

### Rules

- **R-JPASP-001** MUST NOT: Service layer classes MUST NOT directly use EntityManager or create manual JDBC connections for standard CRUD operations.

### Verify

```bash
# Verify all repositories extend Spring Data JPA base interfaces
grep -r "extends JpaRepository\|extends CrudRepository\|extends PagingAndSortingRepository" --include="*Repository.java" .

# Count repository files in dao package
find . -name "*Repository.java" -path "*/dao/*" | wc -l

# Verify @Repository annotation usage
grep -r "@Repository" --include="*Repository.java" . | wc -l

# Check for direct EntityManager usage in service layer (should return no results)
grep -r "EntityManager" --include="*Service.java" . | grep -v "import" | grep -v "//"

# Check for direct JDBC connections in service layer (should return no results)
grep -r "DriverManager\|Connection\|Statement" --include="*Service.java" . | grep -v "import" | grep -v "//"
```

**Accept when:**
- All entity classes have corresponding Repository interfaces extending Spring Data JPA base interfaces (JpaRepository, CrudRepository, or PagingAndSortingRepository)
- Repository interfaces are located in the dao package structure following naming convention {EntityName}Repository
- No direct EntityManager or JDBC usage exists in service layer for standard CRUD operations
- Custom query methods follow Spring Data naming conventions or use @Query annotations
- @Repository annotation is present on all repository interfaces
- Spring Data JPA repository scanning is enabled via @EnableJpaRepositories in application configuration

<enforcement>
Claude Code MUST NOT skip or defer verification of these rules. All service layer database access MUST route through Spring Data JPA repositories. Direct EntityManager or JDBC usage in service classes is a violation requiring refactoring before approval.
</enforcement>
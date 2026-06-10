# Adopt JPA Entity Annotations as Standard Data Modeling Approach: Persistent Domain Entities

These rules are ALWAYS ACTIVE for all persistent domain entity classes requiring database persistence across the application.

### Rules

- **R-JPA-001** MUST: All persistent domain entities MUST be annotated with @Entity and mapped using JPA annotations.
- **R-JPA-002** MUST: All entity classes MUST contain @Id annotations for primary key definition.
- **R-JPA-003** MUST: Entity relationship annotations (@OneToMany, @ManyToOne, @OneToOne, @ManyToMany) MUST be present for all inter-entity associations.
- **R-JPA-004** MUST: Entity classes MUST NOT contain direct JDBC code or SQL string literals (except in @Query annotations).
- **R-JPA-005** SHOULD: Use Hibernate as the JPA implementation provider, configured through Spring Boot's spring.jpa.* properties.
- **R-JPA-006** SHOULD: Establish abstract base entity classes for common fields (id, createdAt, updatedAt) to reduce duplication across entities.
- **R-JPA-007** SHOULD: Use constructor-based or builder patterns for entity creation to ensure required fields are populated.
- **R-JPA-008** SHOULD: Enable SQL logging in development (spring.jpa.show-sql=true) to understand generated queries and identify performance issues early.
- **R-JPA-009** SHOULD: Use @EntityGraph or fetch joins to optimize query patterns and prevent N+1 query problems.
- **R-JPA-010** MAY: Use native SQL or database-specific features for read-only reporting queries not supported by JPA (EXC-002).
- **R-JPA-011** MAY: Use direct JDBC access for specific high-performance bulk operations where JPA overhead is demonstrably prohibitive (EXC-001).

### Verify

```bash
# Count @Entity annotations in codebase
grep -r '@Entity' --include='*.java' src/main/java | wc -l

# Count Serializable implementations in entity packages
grep -r 'class.*implements Serializable' --include='*.java' src/main/java/*/entity/ | wc -l

# Find entity classes missing @Entity annotation
find src/main/java -path '*/entity/*.java' -exec grep -L '@Entity' {} \;

# Verify no direct JDBC in entity classes
grep -r 'DriverManager\|Connection\|Statement\|ResultSet' --include='*.java' src/main/java/*/entity/ | grep -v '@Query' | wc -l
```

**Accept when:**
- All classes in entity packages are annotated with @Entity and contain @Id annotations
- No entity classes contain direct JDBC code or SQL string literals (except in @Query annotations)
- Entity relationship annotations (@OneToMany, @ManyToOne, etc.) are present for all inter-entity associations
- CI pipeline integration tests exercise entity persistence and relationship loading successfully

<enforcement>
Claude Code MUST NOT skip or defer verification. Pull requests failing entity convention checks MUST be blocked from merging. Code review feedback MUST require correction of missing or incorrect JPA annotations. Existing violations MUST be tracked as technical debt items and prioritized for remediation.
</enforcement>
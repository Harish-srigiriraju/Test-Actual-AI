# Adopt JPA Entity Annotations as Standard Data Modeling Approach: Cascade Types Fetch

These rules are ALWAYS ACTIVE for all domain entity classes requiring database persistence, entity relationship mappings, and JPA-based data modeling across the application.

### Rules

- **R-JPA-001** SHOULD: Cascade types and fetch strategies SHOULD be explicitly specified on relationship annotations to control persistence behavior and performance characteristics.

### Verify

```bash
# Count total @Entity annotations in codebase
grep -r '@Entity' --include='*.java' src/main/java | wc -l

# Count entity classes implementing Serializable
grep -r 'class.*implements Serializable' --include='*.java' src/main/java/*/entity/ | wc -l

# Find entity classes missing @Entity annotation
find src/main/java -path '*/entity/*.java' -exec grep -L '@Entity' {} \;
```

**Accept when:**
- All classes in entity packages are annotated with @Entity and contain @Id annotations
- No entity classes contain direct JDBC code or SQL string literals (except in @Query annotations)
- Entity relationship annotations (@OneToMany, @ManyToOne, etc.) are present for all inter-entity associations
- Cascade types and fetch strategies are explicitly declared on all relationship annotations

<enforcement>
Claude Code MUST NOT skip or defer verification. All entity relationship annotations must include explicit cascade and fetch configuration before code review approval.
</enforcement>
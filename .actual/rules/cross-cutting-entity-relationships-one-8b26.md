# Adopt JPA Entity Annotations as Standard Data Modeling Approach: Entity Relationships One

These rules are ALWAYS ACTIVE for all domain entity classes requiring database persistence, including entity relationship mappings, primary key strategies, table and column configurations, and entity lifecycle callbacks.

### Rules

- **R-JPA-001** MUST: Entity relationships (one-to-one, one-to-many, many-to-one, many-to-many) MUST be explicitly declared using appropriate JPA relationship annotations (@OneToOne, @OneToMany, @ManyToOne, @ManyToMany).

### Verify

```bash
# Count @Entity annotations in codebase
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

<enforcement>
Claude Code MUST NOT skip or defer verification. Pull requests failing entity convention checks are blocked from merging. Code review feedback requires correction of missing or incorrect JPA annotations.
</enforcement>
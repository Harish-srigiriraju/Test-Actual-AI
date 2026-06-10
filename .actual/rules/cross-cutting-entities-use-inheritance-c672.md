# Adopt JPA Entity Annotations as Standard Data Modeling Approach: Entities Use Inheritance

These rules are ALWAYS ACTIVE for all domain entity classes requiring database persistence across the application.

### Rules

- **R-JPA-001** MAY: Entities MAY use inheritance strategies (@Inheritance) for modeling entity hierarchies such as user type specializations.

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
- Inheritance hierarchies use @Inheritance annotation with appropriate strategy (SINGLE_TABLE, JOINED, or TABLE_PER_CLASS)

<enforcement>
Clause Code MUST NOT skip or defer verification. Entity annotation compliance is mandatory for all persistence layer code.
</enforcement>
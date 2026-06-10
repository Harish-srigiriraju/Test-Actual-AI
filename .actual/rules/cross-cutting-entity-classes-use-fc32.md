# Adopt JPA Entity Annotations as Standard Data Modeling Approach: Entity Classes Use

These rules are ALWAYS ACTIVE for all domain entity classes requiring database persistence across the application.

### Rules

- **R-JPA-001** SHOULD: Entity classes SHOULD use @Table annotation to explicitly map to database tables when table names differ from entity class names.

### Verify

```bash
# Count @Entity annotations in entity classes
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
Clause Code MUST NOT skip or defer verification of JPA entity annotation compliance. Violations block pull request merging and require correction via peer code review.
</enforcement>
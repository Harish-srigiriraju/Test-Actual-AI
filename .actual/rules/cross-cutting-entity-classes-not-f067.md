# Adopt JPA Entity Annotations as Standard Data Modeling Approach: Entity Classes Not

These rules are ALWAYS ACTIVE for all domain entity classes requiring database persistence across the application.

### Rules

- **R-JPA-001** MUST NOT: Entity classes MUST NOT contain business logic beyond basic validation; complex business operations should reside in service layer components.

### Verify

```bash
# Count @Entity annotations in codebase
grep -r '@Entity' --include='*.java' src/main/java | wc -l

# Count Serializable implementations in entity packages
grep -r 'class.*implements Serializable' --include='*.java' src/main/java/*/entity/ | wc -l

# Find entity classes missing @Entity annotation
find src/main/java -path '*/entity/*.java' -exec grep -L '@Entity' {} \;
```

**Accept when:**
- All classes in entity packages are annotated with @Entity and contain @Id annotations
- No entity classes contain direct JDBC code or SQL string literals (except in @Query annotations)
- Entity relationship annotations (@OneToMany, @ManyToOne, etc.) are present for all inter-entity associations

<enforcement>
Clause Code MUST NOT skip or defer verification. All entity classes must comply with R-JPA-001 before merge. Violations are blocking and require remediation or documented exception approval.
</enforcement>
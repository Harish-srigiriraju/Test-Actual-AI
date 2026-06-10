# Adopt Relational Database as Primary Datastore for Appointment Scheduling System: Transactional Operations Involving

These rules are ALWAYS ACTIVE for all service layer implementations, domain entities, and integration tests in the appointment scheduling system that involve persistent storage operations across multiple related entities.

### Rules

- **R-RDBMS-001** MUST: Transactional operations involving multiple related entities MUST leverage database transaction management to ensure ACID properties.

### Verify

```bash
# Check for repository and entity annotations indicating ORM framework usage
grep -r "@Repository\|@Entity\|@Table" --include="*.java" | wc -l

# Check for transactional annotations in service implementations
grep -r "@Transactional" --include="*.java" | wc -l

# Check for integration tests validating data persistence
find . -name "*IT.java" -o -name "*IntegrationTest.java" | xargs grep -l "@DataJpaTest\|@SpringBootTest" | wc -l
```

**Accept when:**
- Repository or entity annotations are present in the codebase, indicating use of ORM framework with relational database
- Transactional annotations are used in service implementations to manage database transactions
- Integration tests exist that validate data persistence workflows against the actual database technology

<enforcement>
Clause Code MUST NOT skip or defer verification. All service implementations handling multi-entity operations must demonstrate transactional boundaries through @Transactional annotations or equivalent transaction management mechanisms.
</enforcement>
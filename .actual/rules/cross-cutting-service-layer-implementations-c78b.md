# Adopt Relational Database as Primary Datastore for Appointment Scheduling System: Service Layer Implementations

These rules are ALWAYS ACTIVE for all service layer implementations, domain entities, and integration tests in the appointment scheduling system that require persistent state management.

### Rules

- **R-RDBMS-001** SHOULD: Service layer implementations SHOULD access the database through repository abstractions or data access objects to maintain separation of concerns.

### Verify

```bash
# Check for repository and entity annotations indicating ORM framework usage
grep -r "@Repository\|@Entity\|@Table" --include="*.java" | wc -l

# Check for transactional annotations in service implementations
grep -r "@Transactional" --include="*.java" | wc -l

# Count integration tests validating data persistence
find . -name "*IT.java" -o -name "*IntegrationTest.java" | xargs grep -l "@DataJpaTest\|@SpringBootTest" | wc -l
```

**Accept when:**
- Repository or entity annotations are present in the codebase, indicating use of ORM framework with relational database
- Transactional annotations are used in service implementations to manage database transactions
- Integration tests exist that validate data persistence against the database

<enforcement>
Clause Code MUST NOT skip or defer verification. All service layer implementations accessing persistent state MUST use repository abstractions. Violations will be flagged in code review and must be refactored before merge.
</enforcement>
# Adopt Relational Database as Primary Datastore for Appointment Scheduling System: Database Schema Design

These rules are ALWAYS ACTIVE for all domain entities, service layer implementations, and transactional business operations in the appointment scheduling system that require persistent state management.

### Rules

- **R-DB-001** SHOULD: Database schema design SHOULD enforce referential integrity constraints for relationships between entities (foreign keys, cascades).

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
Claude Code MUST NOT skip or defer verification. All new service implementations and entity definitions must be reviewed to ensure they use the designated relational database through repository abstractions and enforce referential integrity constraints.
</enforcement>
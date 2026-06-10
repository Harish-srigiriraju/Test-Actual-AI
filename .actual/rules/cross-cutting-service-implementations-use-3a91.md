# Adopt Relational Database as Primary Datastore for Appointment Scheduling System: Service Implementations Use

These rules are ALWAYS ACTIVE for all service layer implementations, domain entities, and integration tests in the appointment scheduling system that require persistent state management.

### Rules

- **R-RDBMS-001** MUST: Service implementations MUST use the relational database for all CRUD operations on domain entities rather than alternative storage mechanisms.

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
- Integration tests exist that validate data persistence against the database

<enforcement>
Clause Code MUST NOT skip or defer verification. All service implementations must demonstrate relational database usage through repository abstractions, transactional boundaries, and integration test validation before acceptance.
</enforcement>
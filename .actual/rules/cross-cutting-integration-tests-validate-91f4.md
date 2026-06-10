# Adopt Relational Database as Primary Datastore for Appointment Scheduling System: Integration Tests Validate

These rules are ALWAYS ACTIVE for all service layer implementations, domain entities, and integration tests in the appointment scheduling system that handle persistent storage of appointments, users, notifications, and authentication tokens.

### Rules

- **R-RDBMS-001** SHOULD: Integration tests SHOULD validate data persistence workflows against the actual database technology to ensure compatibility.

### Verify

```bash
# Check for repository or entity annotations indicating ORM framework usage
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
- Service implementations (EmailServiceImpl, NotificationServiceImpl, JwtTokenServiceImpl) use repository abstractions for data access
- Integration tests (AppointmentServiceIT, LoginPageIT) validate end-to-end persistence workflows

<enforcement>
Clause Code MUST NOT skip or defer verification. All new service implementations handling persistent state MUST use the designated relational database through repository abstractions. Integration tests MUST validate data persistence workflows against the actual database technology.
</enforcement>
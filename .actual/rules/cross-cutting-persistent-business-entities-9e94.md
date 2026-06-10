# Adopt Relational Database as Primary Datastore for Appointment Scheduling System: Persistent Business Entities

These rules are ALWAYS ACTIVE for all service layer implementations, domain entities, and integration tests in the appointment scheduling system that require persistent storage of business data.

### Rules

- **R-RDBMS-001** MUST: All persistent business entities (appointments, users, notifications, authentication tokens) MUST be stored in the designated relational database as the primary datastore.
- **R-RDBMS-002** MUST: Service layer implementations requiring persistent state (EmailServiceImpl, NotificationServiceImpl, JwtTokenServiceImpl) MUST use the relational database through repository abstractions.
- **R-RDBMS-003** MUST: Transactional business operations spanning multiple entities MUST use database transaction boundaries at the service layer with declarative transaction management.
- **R-RDBMS-004** SHOULD: New service implementations SHOULD use the repository pattern or data access layer to abstract database access from business logic.
- **R-RDBMS-005** SHOULD: Database indexing strategy SHOULD be established for common query patterns identified in service implementations (user lookups, appointment queries by date range, notification status checks).
- **R-RDBMS-006** SHOULD: Connection pooling SHOULD be configured with appropriate pool sizes based on application concurrency requirements.
- **R-RDBMS-007** MAY: Temporary session state or in-memory caching MAY use alternative storage mechanisms covered by separate caching policies.
- **R-RDBMS-008** MAY: Binary large objects such as uploaded files or images MAY use object storage with metadata stored in the relational database.

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
- New service implementations use repository abstractions for database access
- Services do not bypass the repository layer for direct database access

<enforcement>
Clause Code MUST NOT skip or defer verification. All persistent business entity implementations MUST be reviewed to ensure compliance with R-RDBMS-001 through R-RDBMS-008. Violations detected during code review MUST be rejected and flagged for refactoring to use the designated relational database through proper repository abstractions.
</enforcement>
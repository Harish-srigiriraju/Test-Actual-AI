# Adopt Relational Database as Primary Datastore for Appointment Scheduling System: Services Implement Caching

These rules are ALWAYS ACTIVE for all service layer implementations, domain entities, transactional business operations, and integration tests within the appointment scheduling system.

### Rules

- **R-RDBMS-001** MUST: Use a relational database as the primary datastore for all domain entities (appointments, users, pets, notifications, authentication tokens).
- **R-RDBMS-002** MUST: Implement service layer abstractions (repository pattern or data access layer) to abstract database access from business logic.
- **R-RDBMS-003** MUST: Use declarative transaction management (@Transactional annotations) for operations spanning multiple entities to ensure ACID guarantees.
- **R-RDBMS-004** MAY: Services MAY implement caching layers above the primary datastore for read-heavy access patterns, but the relational database remains the source of truth.
- **R-RDBMS-005** SHOULD: Use a database migration tool (Flyway, Liquibase) to version control and manage schema changes across environments.
- **R-RDBMS-006** SHOULD: Establish database indexing strategy for common query patterns identified in service implementations (user lookups, appointment queries by date range, notification status checks).
- **R-RDBMS-007** SHOULD: Implement query performance monitoring and establish a query review process to prevent N+1 query problems and inefficient queries.
- **R-RDBMS-008** SHOULD: Configure connection pooling with appropriate pool sizes based on application concurrency requirements.
- **R-RDBMS-009** MUST NOT: Introduce alternative primary datastores (NoSQL, document databases, in-memory databases) without architectural approval and documented exception.
- **R-RDBMS-010** MUST NOT: Bypass repository layer for direct database access in service implementations.

### Verify

```bash
# Verify repository and entity annotations are present
grep -r "@Repository\|@Entity\|@Table" --include="*.java" | wc -l

# Verify transactional annotations are used in service implementations
grep -r "@Transactional" --include="*.java" | wc -l

# Verify integration tests exist that validate data persistence
find . -name "*IT.java" -o -name "*IntegrationTest.java" | xargs grep -l "@DataJpaTest\|@SpringBootTest" | wc -l
```

**Accept when:**
- Repository or entity annotations (@Repository, @Entity, @Table) are present in the codebase, indicating use of ORM framework with relational database
- Transactional annotations (@Transactional) are used in service implementations to manage database transactions
- Integration tests exist that validate data persistence workflows against the actual database technology (AppointmentServiceIT, LoginPageIT patterns)
- All service implementations use repository abstractions rather than direct database access
- Schema changes are managed through database migration tools

<enforcement>
Claude Code MUST NOT skip or defer verification. All rules in this file are mandatory for code review and architecture validation. Violations must be flagged and remediated before merge.
</enforcement>
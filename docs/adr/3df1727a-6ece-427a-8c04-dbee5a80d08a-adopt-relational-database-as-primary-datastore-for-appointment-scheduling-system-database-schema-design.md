# Adopt Relational Database as Primary Datastore for Appointment Scheduling System: Database Schema Design

Status: proposed
Date: 2025-01-17
Deciders: Detection Pipeline (automated)

## Context

- The appointment scheduling system requires persistent storage for entities including appointments, users, notifications, and authentication tokens with complex relational integrity constraints
- Service layer implementations (EmailServiceImpl, NotificationServiceImpl, JwtTokenServiceImpl) demonstrate consistent patterns of data persistence operations across multiple business domains
- Integration tests (AppointmentServiceIT, LoginPageIT) validate end-to-end data persistence workflows, indicating a production-grade datastore is in active use
- The system handles transactional operations involving multiple related entities (appointments with users, notifications with appointments, JWT tokens with user sessions) requiring ACID guarantees
- Pattern detected across 5 files with 91.96% confidence suggests a standardized approach to primary data storage has been established

## Problem Statement

The appointment scheduling system needs a reliable, performant, and maintainable primary datastore that can handle complex relational data models, ensure data integrity through transactions, support concurrent access patterns, and provide query capabilities for business logic across multiple service implementations while maintaining consistency for authentication, notification, and scheduling workflows.

## Decision

1. SHOULD: Database schema design SHOULD enforce referential integrity constraints for relationships between entities (foreign keys, cascades)

## Policy Block

- SHOULD Database schema design SHOULD enforce referential integrity constraints for relationships between entities (foreign keys, cascades)

In scope:
- All domain entities in the appointment scheduling system (appointments, users, pets, notifications, authentication tokens)
- Service layer implementations requiring persistent state (EmailServiceImpl, NotificationServiceImpl, JwtTokenServiceImpl)
- Transactional business operations spanning multiple entities
- Integration tests validating end-to-end persistence workflows

Out of scope:
- Temporary session state or in-memory caching (covered by separate caching policies)
- External system integrations where data is owned by third-party services
- Logging and audit trails (may use specialized time-series or log storage)
- Binary large objects such as uploaded files or images (may use object storage with metadata in relational database)

Exceptions:
- EXC-001: Performance profiling demonstrates that specific read-heavy queries cause unacceptable latency even after query optimization
- EXC-002: Regulatory or compliance requirements mandate data segregation to specialized storage systems

## Rationale

- Pattern detection across 5 service implementation and integration test files with 91.96% confidence indicates a consistent, established practice of using relational database as primary datastore
- Relational databases provide ACID guarantees essential for appointment scheduling operations where data consistency is critical (double-booking prevention, notification delivery tracking, authentication token validity)
- The appointment scheduling domain has inherently relational data (users have appointments, appointments trigger notifications, users have authentication sessions) making relational databases a natural architectural fit
- Integration test patterns (AppointmentServiceIT, LoginPageIT) demonstrate that the system has been designed and validated against relational database semantics from the ground up

## Consequences

Positive:
- Strong data consistency and integrity through ACID transactions prevents data corruption in critical scheduling and authentication workflows
- Mature ecosystem of relational database tools, monitoring solutions, backup strategies, and operational expertise reduces operational risk
- Declarative query capabilities (SQL) enable complex business logic queries without requiring application-level data processing
- Referential integrity constraints at the database level provide an additional safety net beyond application-level validation
- Standardized approach across all service implementations reduces cognitive load and improves maintainability

Negative:
- Relational databases may become a scaling bottleneck for extremely high-throughput scenarios requiring horizontal scaling
- Schema migrations require careful planning and coordination, potentially slowing down development velocity for schema-heavy changes
- Complex queries involving multiple joins may experience performance degradation as data volume grows, requiring query optimization expertise
- Vendor lock-in risk if using database-specific features (stored procedures, proprietary extensions) rather than standard SQL

## Alternatives

- NoSQL document database (e.g., MongoDB) as primary datastore (rejected)
  Rejected because: Appointment scheduling requires strong transactional guarantees and complex relational queries (appointments linked to users, pets, notifications). Document databases sacrifice ACID guarantees and relational query capabilities, increasing application complexity for managing consistency.
  When valid: Could be reconsidered for specific microservices with document-oriented data models and eventual consistency tolerance
- Polyglot persistence with multiple specialized datastores per domain (rejected)
  Rejected because: Adds significant operational complexity (multiple databases to monitor, backup, secure) and makes cross-domain transactions difficult. The current system size does not justify this complexity.
  When valid: Should be reconsidered if the system scales to require specialized storage for specific domains (e.g., time-series for analytics, graph database for recommendation engine)
- In-memory database with periodic persistence snapshots (rejected)
  Rejected because: Appointment scheduling data is too critical to risk data loss from system failures. Snapshot-based persistence introduces potential data loss windows unacceptable for production scheduling systems.
  When valid: Not applicable for primary datastore; may be valid for caching layer or development/testing environments

## Risks

- Database becomes a single point of failure, causing complete system outage if database is unavailable
  Mitigation: Implement database high availability (replication, failover), connection pooling with retry logic, and circuit breakers in service layer. Establish database backup and disaster recovery procedures.
  Owner: Infrastructure and Engineering Team
- N+1 query problems and inefficient queries may cause performance degradation as data volume grows
  Mitigation: Implement query performance monitoring, establish query review process, use ORM features to prevent N+1 queries (eager loading, batch fetching), and create database indexes for common query patterns.
  Owner: Engineering Team
- Schema migrations may cause downtime or data inconsistencies during deployment
  Mitigation: Adopt backward-compatible migration strategies (expand-contract pattern), use migration tools with rollback capabilities, test migrations in staging environment, and implement zero-downtime deployment practices.
  Owner: DevOps and Engineering Team

## Implementation Notes

- Use a database migration tool (e.g., Flyway, Liquibase) to version control and manage schema changes across environments
- Implement repository pattern or data access layer to abstract database access from business logic, enabling easier testing and potential datastore changes
- Configure connection pooling with appropriate pool sizes based on application concurrency requirements to optimize database connection management
- Establish database indexing strategy for common query patterns identified in service implementations (user lookups, appointment queries by date range, notification status checks)
- Implement database transaction boundaries at the service layer for operations spanning multiple entities, using declarative transaction management where possible
- Set up database monitoring for query performance, connection pool utilization, and transaction durations to proactively identify performance issues

## Continuation Context


Verify commands:
- grep -r "@Repository\|@Entity\|@Table" --include="*.java" | wc -l
- grep -r "@Transactional" --include="*.java" | wc -l
- find . -name "*IT.java" -o -name "*IntegrationTest.java" | xargs grep -l "@DataJpaTest\|@SpringBootTest" | wc -l

Accept when:
- Repository or entity annotations are present in the codebase, indicating use of ORM framework with relational database
- Transactional annotations are used in service implementations to manage database transactions
- Integration tests exist that validate data persistence against the database

## Enforcement

- Verified by: Code review process checks that new service implementations use the designated relational database through repository abstractions
- Verified by: Integration test suite validates data persistence workflows against the actual database technology
- Verified by: Architecture review for new features ensures alignment with primary datastore decision
- Violation handling: Code introducing alternative primary datastores without architectural approval will be rejected in code review
- Violation handling: Services bypassing repository layer for direct database access will be flagged for refactoring
- Violation handling: Performance issues caused by inefficient database queries will be prioritized for optimization
- Exception process: Submit architecture decision proposal documenting the specific use case requiring exception
- Exception process: Provide performance benchmarks or technical constraints justifying alternative approach
- Exception process: Obtain approval from architecture review board or technical lead
- Exception process: Document the exception and rationale in system architecture documentation
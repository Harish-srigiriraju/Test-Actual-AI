# Adopt Spring Data JPA Repository Pattern for Data Access Layer: Custom Query Methods

Status: proposed
Date: 2024-01-15
Deciders: Detection Pipeline (automated)

## Context

- The application uses Spring Boot with JPA/Hibernate for persistence, requiring a standardized approach to database operations
- Multiple domain entities (Appointment, ChatMessage, Invoice, WorkingPlan, Customer, Role, etc.) need CRUD operations and custom queries
- The codebase shows consistent use of Repository interfaces extending Spring Data JPA repositories across 23+ files
- The pattern separates data access logic from business logic, following separation of concerns principle
- Spring Data JPA provides automatic implementation of common database operations, reducing boilerplate code

## Problem Statement

Without a standardized data access pattern, the application would face inconsistent database interaction approaches, duplicated CRUD logic across services, tight coupling between business logic and persistence concerns, and increased maintenance burden. A uniform repository abstraction is needed to ensure consistent data access patterns, enable testability through interface-based design, and leverage framework capabilities for automatic query generation.

## Decision

1. SHOULD: Custom query methods SHOULD follow Spring Data JPA naming conventions for automatic query derivation

## Policy Block

- SHOULD Custom query methods SHOULD follow Spring Data JPA naming conventions for automatic query derivation

In scope:
- All JPA entity classes requiring database persistence
- CRUD operations (Create, Read, Update, Delete)
- Custom finder methods and queries
- Pagination and sorting operations
- Transaction-managed data access

Out of scope:
- Non-relational data stores (NoSQL, cache layers)
- Direct JDBC operations for batch processing or performance-critical operations
- Database schema migrations and DDL operations
- Read-only reporting queries that may benefit from direct SQL optimization

Exceptions:
- EXC-001: Bulk operations requiring native SQL for performance optimization
- EXC-002: Complex reporting queries with database-specific features not supported by JPQL

## Rationale

- The pattern is detected across 23 files with 92.50% confidence, indicating strong architectural consistency and intentional design
- Spring Data JPA Repository pattern is an industry-standard approach that reduces boilerplate code by up to 70% through automatic implementation generation
- The separation of Repository interfaces from Entity classes promotes clean architecture and testability through dependency injection
- Consistent repository naming and structure (e.g., AppointmentRepository, ChatMessageRepository) demonstrates deliberate architectural standardization across the domain model

## Consequences

Positive:
- Reduced boilerplate code: Spring Data JPA automatically implements common CRUD operations without manual coding
- Improved testability: Repository interfaces can be easily mocked or stubbed in unit tests
- Consistent data access patterns across the entire application, reducing cognitive load for developers
- Type-safe query methods with compile-time checking through method name derivation
- Built-in support for pagination, sorting, and auditing without custom implementation

Negative:
- Learning curve for developers unfamiliar with Spring Data JPA conventions and query derivation rules
- Potential performance overhead from JPA abstraction layer compared to hand-optimized SQL
- Limited control over generated SQL queries, which may not be optimal for complex scenarios
- Tight coupling to Spring Framework ecosystem, making migration to other frameworks more difficult
- Magic behavior through method name parsing can be confusing and may hide actual query execution

## Alternatives

- Direct EntityManager usage with custom DAO implementations (rejected)
  Rejected because: Requires significantly more boilerplate code, loses automatic query generation benefits, and reduces consistency across the codebase. The detected pattern shows no evidence of manual EntityManager usage.
  When valid: When fine-grained control over persistence context and query optimization is critical for performance
- MyBatis or other SQL-mapping frameworks (rejected)
  Rejected because: Would require complete rewrite of existing data access layer. The codebase has already standardized on JPA entities and Spring Data repositories with high consistency (23 files).
  When valid: For new projects requiring complex SQL queries or working with legacy database schemas
- JOOQ for type-safe SQL generation (rejected)
  Rejected because: Adds complexity and additional dependencies. The current pattern adequately serves the application's needs with simpler abstraction and wider Spring ecosystem integration.
  When valid: When type-safe SQL generation is prioritized over ORM abstraction and complex queries dominate the application

## Risks

- N+1 query problems when lazy-loading relationships without proper fetch strategies
  Mitigation: Use @EntityGraph, JOIN FETCH in JPQL queries, or configure appropriate fetch types. Implement query performance monitoring and logging.
  Owner: Engineering team
- Repository method name parsing failures leading to runtime errors instead of compile-time errors
  Mitigation: Implement comprehensive integration tests for all custom repository methods. Use @Query annotation for complex queries to make intent explicit.
  Owner: Engineering team
- Performance degradation for bulk operations due to JPA overhead
  Mitigation: Monitor query performance metrics. Use batch processing configurations and consider native queries for bulk operations exceeding 1000 records.
  Owner: Engineering team and DevOps

## Implementation Notes

- Place all Repository interfaces in the dao package following the existing convention (e.g., com.petreach.appointmentscheduler.dao)
- Use JpaRepository as the base interface for most cases as it provides the most comprehensive feature set including batch operations
- Follow naming convention: {EntityName}Repository (e.g., AppointmentRepository for Appointment entity)
- Enable Spring Data JPA repository scanning in the main application configuration class using @EnableJpaRepositories
- For custom queries, prefer method name derivation first, then @Query with JPQL, and finally native SQL only when necessary
- Document complex custom query methods with JavaDoc explaining the query purpose and any performance considerations

## Continuation Context


Verify commands:
- grep -r "extends JpaRepository\|extends CrudRepository\|extends PagingAndSortingRepository" --include="*Repository.java" .
- find . -name "*Repository.java" -path "*/dao/*" | wc -l
- grep -r "@Repository" --include="*Repository.java" . | wc -l

Accept when:
- All entity classes have corresponding Repository interfaces extending Spring Data JPA base interfaces
- Repository interfaces are located in the dao package structure
- No direct EntityManager or JDBC usage exists in service layer for standard CRUD operations
- Custom query methods follow Spring Data naming conventions or use @Query annotations

## Enforcement

- Verified by: Code review process checking for Repository pattern compliance
- Verified by: Static analysis tools scanning for direct EntityManager usage in service layer
- Verified by: Integration tests verifying repository functionality
- Verified by: Architecture fitness functions in CI pipeline
- Violation handling: Pull requests with direct database access bypassing repositories are rejected
- Violation handling: Code review checklist includes Repository pattern verification
- Violation handling: Automated linting rules flag EntityManager usage outside repository layer
- Violation handling: Violations require refactoring before merge approval
- Exception process: Developer submits exception request with performance benchmarks or technical justification
- Exception process: Technical lead reviews and approves/rejects based on merit
- Exception process: Approved exceptions are documented in code with comments explaining rationale
- Exception process: Exception cases are reviewed quarterly to determine if pattern should be updated
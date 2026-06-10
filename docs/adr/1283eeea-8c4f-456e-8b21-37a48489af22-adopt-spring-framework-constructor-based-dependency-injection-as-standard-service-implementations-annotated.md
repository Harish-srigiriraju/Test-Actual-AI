# Adopt Spring Framework Constructor-Based Dependency Injection as Standard: Service Implementations Annotated

Status: proposed
Date: 2024-01-15
Deciders: Detection Pipeline (automated)

## Activation

This ADR is ACTIVE for all Java Spring Framework components including services, validators, configuration classes, and security components.

## Context

- The codebase uses Spring Framework as the primary application framework for dependency management and inversion of control
- Multiple service implementations, validators, security components, and configuration classes require dependency injection for repositories, other services, and utilities
- Constructor-based dependency injection pattern has been consistently applied across 20 files including service implementations (UserServiceImpl, AppointmentServiceImpl, InvoiceServiceImpl, etc.), validators (UniqueUsernameValidator), and security components (CustomUserDetailsService)
- The pattern demonstrates high consistency (92.02% confidence) indicating an established architectural standard rather than ad-hoc implementation
- Spring Framework's IoC container manages component lifecycle and dependency resolution, requiring explicit declaration of dependencies

## Problem Statement

Without a standardized dependency injection approach, the codebase risks inconsistent patterns between field injection, setter injection, and constructor injection, leading to difficulties in testing, hidden dependencies, and potential null pointer exceptions. A clear standard is needed to ensure testability, immutability, and explicit dependency declaration across all Spring-managed components.

## Decision

1. MUST: Service implementations MUST be annotated with @Service and follow the naming convention *ServiceImpl

## Policy Block

- MUST Service implementations MUST be annotated with @Service and follow the naming convention *ServiceImpl

In scope:
- All @Service annotated classes
- All @Component annotated classes
- All @Controller and @RestController annotated classes
- All @Configuration annotated classes
- Custom validators implementing Spring Validation interfaces
- Security components including UserDetailsService implementations
- Repository interfaces and custom repository implementations

Out of scope:
- JPA entity classes (which should not have Spring dependencies)
- DTOs and value objects
- Utility classes with only static methods
- Test fixtures and test data builders
- Third-party library integrations that require specific injection patterns

Exceptions:
- EX-001: Integration with legacy Spring XML configuration that requires setter injection
- EX-002: Circular dependency that cannot be resolved through refactoring

## Rationale

- Constructor injection makes dependencies explicit and visible, improving code readability and making it clear what a component needs to function
- Immutable dependencies (final fields) prevent accidental reassignment and make components thread-safe by default
- Constructor injection enables easy unit testing by allowing dependencies to be provided without Spring container, supporting pure POJO testing
- The pattern has been successfully applied across 20 files with 92.02% consistency, demonstrating its viability and team acceptance in this codebase

## Consequences

Positive:
- Improved testability: Components can be instantiated in unit tests without Spring context by providing mock dependencies through constructor
- Compile-time safety: Missing dependencies cause compilation errors rather than runtime failures
- Immutability: Final fields prevent accidental modification and make components inherently thread-safe
- Clear contracts: Constructor signature explicitly declares all required dependencies, serving as self-documentation
- Easier refactoring: IDEs can track constructor usage and help identify all dependency consumers

Negative:
- Verbose constructors: Classes with many dependencies result in long constructor signatures, potentially indicating design issues
- Circular dependency detection: Constructor injection fails fast on circular dependencies, requiring architectural fixes rather than hiding the problem
- Boilerplate code: Requires explicit constructor declaration and field assignment (though Lombok @RequiredArgsConstructor can mitigate this)
- Migration effort: Existing code using field injection requires refactoring to adopt this standard

## Alternatives

- Field injection using @Autowired on private fields (rejected)
  Rejected because: Field injection hides dependencies, makes testing difficult (requires reflection or Spring context), prevents immutability, and allows null dependencies at construction time
  When valid: Never recommended for new code; only acceptable in legacy code during migration period
- Setter injection using @Autowired on setter methods (rejected)
  Rejected because: Setter injection allows mutable dependencies, permits object construction in invalid state, and makes dependencies less explicit than constructor injection
  When valid: Only for truly optional dependencies with reasonable defaults
- Mixed approach allowing any injection style based on developer preference (rejected)
  Rejected because: Inconsistent patterns across codebase increase cognitive load, make code reviews harder, and prevent establishing clear best practices
  When valid: Not valid; consistency is critical for maintainability

## Risks

- Large constructors (>5 parameters) may indicate Single Responsibility Principle violations but could be masked as 'following the standard'
  Mitigation: Establish code review guideline that constructors with >5 dependencies trigger architectural review to consider component decomposition
  Owner: Engineering team and tech leads
- Circular dependencies become harder to work around with constructor injection, potentially blocking development
  Mitigation: Treat circular dependencies as architectural smells requiring refactoring; use @Lazy annotation only as temporary measure with documented technical debt
  Owner: Architecture team
- Team members unfamiliar with constructor injection may continue using field injection out of habit
  Mitigation: Add static analysis rules (Checkstyle/SonarQube) to detect and flag field injection; provide team training and update coding standards documentation
  Owner: Engineering team and DevOps

## Implementation Notes

- Use Lombok's @RequiredArgsConstructor annotation to reduce boilerplate for classes with multiple final dependencies
- When migrating from field injection, use IDE refactoring tools (IntelliJ: 'Convert field injection to constructor injection') to automate the transformation
- For classes with >5 dependencies, consider whether the class has too many responsibilities and should be decomposed into smaller components
- In integration tests, continue using @Autowired field injection as it simplifies test setup and doesn't impact production code quality
- Document any circular dependencies that require @Lazy annotation with a TODO comment and ticket to refactor the architecture

## Continuation Context


Verify commands:
- grep -r '@Autowired' --include='*.java' src/main/java | grep -v 'constructor' | grep 'private' && echo 'Field injection detected' || echo 'No field injection found'
- find src/main/java -name '*ServiceImpl.java' -exec grep -L 'public.*ServiceImpl(' {} \; | wc -l
- ./gradlew test --tests '*ServiceTest' || ./mvnw test -Dtest='*ServiceTest'

Accept when:
- All service implementations use constructor-based dependency injection with final fields
- No @Autowired annotations appear on private fields in production code (src/main/java)
- All unit tests can instantiate service classes without Spring context by providing constructor arguments
- Static analysis tools (SonarQube/Checkstyle) report zero field injection violations

## Enforcement

- Verified by: Static analysis tools (SonarQube rule: squid:S3306 - Dependency injection should be used)
- Verified by: Checkstyle rule configured to detect @Autowired on fields
- Verified by: Code review checklist includes verification of constructor injection pattern
- Verified by: CI pipeline fails on static analysis violations related to dependency injection
- Violation handling: CI build fails if static analysis detects field injection in new or modified files
- Violation handling: Pull requests with field injection violations are blocked from merging
- Violation handling: Existing violations in legacy code are tracked as technical debt with suppression comments and linked tickets
- Violation handling: Quarterly architecture reviews assess progress on eliminating legacy field injection
- Exception process: Developer documents exception reason in code review description and class-level Javadoc
- Exception process: Tech lead reviews and approves exception based on documented criteria (EX-001, EX-002)
- Exception process: Exception is logged in architecture decision log with expiration date for review
- Exception process: Suppression annotation added with ticket reference for future remediation
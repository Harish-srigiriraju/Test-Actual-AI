# Enforce Input Validation Using Dedicated Form Objects with Validation Annotations: Form Objects Immutable

Status: proposed
Date: 2025-01-10
Deciders: Detection Pipeline (automated)

## Activation

This ADR is ACTIVE for all user input handling in web controllers, API endpoints, and form processing components. All code that accepts external input MUST comply with these validation requirements.

## Context

- The application processes sensitive user data including passwords and personal information through web forms and API endpoints
- Direct binding of user input to domain entities creates security vulnerabilities including injection attacks, data corruption, and unauthorized field manipulation
- Pattern detected in ChangePasswordForm.java and UserForm.java demonstrates consistent use of dedicated form objects with validation annotations for input sanitization
- Java Bean Validation (JSR-303/JSR-380) provides declarative validation constraints that can be enforced at the framework level before business logic execution
- Separation of form objects from domain models enables defense-in-depth by creating an explicit validation boundary at the application entry points

## Problem Statement

Without standardized input validation mechanisms, the application is vulnerable to malicious or malformed input that can bypass business logic constraints, corrupt data integrity, or enable injection attacks. Direct binding of HTTP parameters to domain entities exposes internal model structure and allows attackers to manipulate fields that should be immutable or restricted.

## Decision

1. SHOULD: Form objects SHOULD be immutable or use defensive copying when transferring data to domain entities to prevent reference manipulation

## Policy Block

- SHOULD Form objects SHOULD be immutable or use defensive copying when transferring data to domain entities to prevent reference manipulation

In scope:
- All web controller methods accepting user input
- REST API endpoints processing request bodies
- Form submission handlers for authentication, registration, and profile management
- Any component that receives data from untrusted external sources
- Password change forms, user registration forms, and data update operations

Out of scope:
- Internal service-to-service communication within trusted boundaries
- Data transfer between layers within the same application context where validation has already occurred
- System-generated data or configuration loaded from trusted sources
- Read-only operations that do not accept user input

Exceptions:
- EXC-001: Legacy endpoints scheduled for deprecation within 90 days
- EXC-002: High-performance bulk import operations where validation occurs in separate preprocessing stage

## Rationale

- Pattern evidence from ChangePasswordForm.java and UserForm.java shows consistent implementation of validation annotations with 91.70% confidence across 2 files, indicating established architectural practice
- Declarative validation using annotations provides compile-time safety, reduces boilerplate code, and centralizes validation logic in a maintainable location
- Separation of form objects from domain models implements the principle of least privilege by exposing only necessary fields to external input binding
- Framework-level validation enforcement (Spring @Valid) ensures validation cannot be accidentally bypassed by developers, creating a reliable security boundary

## Consequences

Positive:
- Significantly reduces attack surface for injection attacks, mass assignment vulnerabilities, and data corruption
- Provides consistent, predictable validation behavior across all application entry points
- Improves code maintainability by centralizing validation rules in declarative annotations rather than scattered imperative checks
- Enables automatic generation of API documentation and client-side validation from the same validation metadata
- Facilitates security auditing by providing clear, scannable validation rules in form object definitions

Negative:
- Introduces additional classes (form objects) that must be maintained alongside domain entities, increasing codebase size
- Requires mapping logic to transfer data from form objects to domain entities, adding development overhead
- May create confusion for developers about when to use form objects versus domain entities
- Complex validation scenarios may require custom validators, increasing implementation complexity

## Alternatives

- Direct validation on domain entities using the same JSR-303 annotations (rejected)
  Rejected because: Exposes internal domain model structure to external input binding, violates separation of concerns, and creates tight coupling between API contracts and domain model evolution
  When valid: Only acceptable for simple CRUD applications with no security requirements and 1:1 mapping between API and domain
- Imperative validation using manual if-else checks in controller methods (rejected)
  Rejected because: Scatters validation logic across codebase, prone to inconsistency and human error, difficult to audit and maintain, no framework-level enforcement
  When valid: May be necessary for complex business rules that cannot be expressed declaratively, but should supplement rather than replace annotation-based validation
- Schema validation using JSON Schema or OpenAPI specifications at API gateway level (deferred)
  Rejected because: Not rejected but deferred as complementary approach
  When valid: Should be used in addition to application-level validation for defense-in-depth, particularly in microservices architectures with API gateways

## Risks

- Developers may bypass validation by forgetting to add @Valid annotation to controller parameters
  Mitigation: Implement static analysis rules (ArchUnit, custom linters) to detect controller methods accepting form objects without @Valid annotation. Add code review checklist item for validation verification.
  Owner: Engineering team with security team oversight
- Incomplete validation coverage where form objects lack constraints on critical fields
  Mitigation: Establish validation coverage metrics in CI pipeline. Require security review for all new form objects. Create form object templates with common validation patterns.
  Owner: Security team
- Performance degradation from validation overhead on high-throughput endpoints
  Mitigation: Profile validation performance in load testing. Consider caching compiled validation metadata. For extreme cases, use policy exception process to implement optimized validation strategies.
  Owner: Performance engineering team

## Implementation Notes

- Create base form object classes or interfaces that enforce validation best practices and provide common validation utilities
- Establish naming convention for form objects (e.g., *Form, *Request, *DTO) to make them easily identifiable in codebase
- Use validation groups (JSR-303 groups) to handle different validation requirements for create vs. update operations on the same form object
- Configure global exception handler (@ControllerAdvice) to translate MethodArgumentNotValidException into user-friendly error responses with field-level error details
- Document common validation patterns in team wiki with examples from ChangePasswordForm and UserForm as reference implementations

## Continuation Context


Verify commands:
- grep -r '@Valid\|@Validated' --include='*Controller.java' | wc -l
- find . -name '*Form.java' -o -name '*Request.java' | xargs grep -L '@NotNull\|@NotEmpty\|@Size\|@Pattern' | wc -l
- grep -r 'public.*(@RequestBody\|@ModelAttribute)' --include='*Controller.java' | grep -v '@Valid' | grep -v '@Validated'

Accept when:
- All controller methods accepting user input have @Valid or @Validated annotations on form object parameters
- All form objects (*Form.java, *Request.java) contain at least one JSR-303 validation annotation
- No controller methods directly bind request parameters to domain entity classes
- Static analysis tools report zero violations of validation annotation requirements

## Enforcement

- Verified by: Automated static analysis using ArchUnit rules in CI pipeline to detect missing @Valid annotations
- Verified by: Code review checklist requiring validation verification for all controller changes
- Verified by: Security scanning tools (e.g., SpotBugs, SonarQube) configured with rules to detect validation gaps
- Verified by: Periodic security audits reviewing form object validation coverage
- Violation handling: CI pipeline fails if static analysis detects controller methods without @Valid on form parameters
- Violation handling: Pull requests blocked until validation requirements are satisfied
- Violation handling: Security team notified of validation violations detected in production code
- Violation handling: Violations logged in security incident tracking system for trend analysis
- Exception process: Submit exception request to architecture review board with justification and alternative validation strategy
- Exception process: Security team must approve all exceptions with documented risk assessment
- Exception process: Exceptions granted for maximum 90 days with mandatory review before renewal
- Exception process: All exceptions tracked in central registry with sunset dates and mitigation plans
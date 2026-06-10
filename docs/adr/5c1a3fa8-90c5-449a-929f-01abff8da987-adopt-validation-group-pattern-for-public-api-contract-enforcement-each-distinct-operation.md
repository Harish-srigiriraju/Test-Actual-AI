# Adopt Validation Group Pattern for Public API Contract Enforcement: Each Distinct Operation

Status: proposed
Date: 2025-01-10
Deciders: Detection Pipeline (automated)

## Activation

This ADR is ACTIVE for all public-facing API endpoints and external integration points. All validation logic for external API contracts MUST follow the validation group pattern defined herein.

## Context

- The appointment scheduler application exposes public APIs for multiple customer types (retail and corporate) with different validation requirements
- External API consumers require clear, consistent contract validation that differs from internal service-to-service communication
- Bean Validation (JSR-303/JSR-380) validation groups provide a mechanism to apply different validation rules based on operation context
- The codebase demonstrates widespread adoption of validation groups (CreateCorporateCustomer, etc.) across 98 files with consistent patterns
- Public APIs require stricter contract enforcement than internal APIs to prevent malformed requests and ensure data integrity at system boundaries

## Problem Statement

Public-facing APIs require context-specific validation rules that differ based on operation type (create vs update), customer type (retail vs corporate), and API visibility (public vs internal). Without a standardized approach to conditional validation, the system risks inconsistent contract enforcement, duplicated validation logic, and poor separation between public API contracts and internal domain constraints.

## Decision

1. MUST: Each distinct operation context (e.g., CreateCorporateCustomer, UpdateRetailCustomer) MUST have its own validation group interface

## Policy Block

- MUST Each distinct operation context (e.g., CreateCorporateCustomer, UpdateRetailCustomer) MUST have its own validation group interface

In scope:
- All REST API controllers exposing public endpoints
- DTO classes and form objects used in public API request/response payloads
- Entity classes when directly exposed through public APIs
- Validation constraint annotations on fields used in external contracts

Out of scope:
- Internal service-to-service communication
- Database-level constraints and validations
- Business logic validation performed in service layer
- Authentication and authorization logic

Exceptions:
- EX-001: Simple CRUD endpoints with identical validation rules across all operations
- EX-002: Internal admin APIs not exposed to external consumers

## Rationale

- Pattern detected across 98 files with 91.97% confidence indicates this is an established architectural standard in the codebase
- Validation groups provide compile-time safety and clear contract definitions that are self-documenting for API consumers
- Separation of validation concerns allows different validation rules for create vs update operations without code duplication
- The pattern aligns with industry best practices for API contract design and JSR-380 Bean Validation specification

## Consequences

Positive:
- Clear, explicit API contracts that are enforced at the framework level before reaching business logic
- Reduced code duplication by reusing entity classes with context-specific validation
- Improved API documentation through self-describing validation constraints
- Better separation of concerns between public API contracts and internal domain validation
- Easier to evolve API contracts independently for different customer types or operation contexts

Negative:
- Increased complexity in entity classes with multiple validation annotations per field
- Learning curve for developers unfamiliar with Bean Validation groups
- Potential for validation group proliferation if not carefully managed
- Additional maintenance overhead to keep validation groups synchronized with API documentation

## Alternatives

- Use separate DTO classes for each operation type without validation groups (rejected)
  Rejected because: Creates significant code duplication and maintenance burden with separate DTO classes for create, update, and other operations. Validation groups provide the same outcome with less code.
  When valid: For APIs with radically different field sets between operations where DTOs would share less than 50% of fields
- Implement custom validation logic in service layer without Bean Validation (rejected)
  Rejected because: Loses framework-level validation benefits, makes contracts less explicit, and requires manual error handling. Does not fail fast at the controller boundary.
  When valid: For complex cross-field validations that cannot be expressed declaratively
- Use OpenAPI schema validation with separate schema definitions per operation (deferred)
  Rejected because: Could complement Bean Validation for API documentation but does not replace the need for server-side validation enforcement
  When valid: As an additional layer for API documentation and client-side validation generation

## Risks

- Validation group proliferation leading to unmaintainable complexity with dozens of group interfaces
  Mitigation: Establish naming conventions and periodic review of validation groups to consolidate similar groups. Limit to operation-based groups (Create, Update, Patch) combined with entity types.
  Owner: API Platform Team
- Inconsistent application of validation groups across controllers leading to gaps in contract enforcement
  Mitigation: Implement ArchUnit tests to verify all public API endpoints use @Validated with appropriate groups. Include in code review checklist.
  Owner: Engineering Team
- Validation groups becoming out of sync with actual API requirements as APIs evolve
  Mitigation: Maintain integration tests that verify validation behavior for each group. Include validation group review in API change approval process.
  Owner: QA and Engineering Team

## Implementation Notes

- Create validation group interfaces as marker interfaces (empty interfaces) in the validation.groups package following the {Action}{EntityType} naming convention
- Annotate controller method parameters with @Validated(ValidationGroup.class) to activate group-specific validation
- Use the 'groups' attribute on constraint annotations (@NotNull(groups = CreateCorporateCustomer.class)) to associate validations with specific groups
- Consider using Default.class group for validations that apply across all operations to avoid repetition
- Document validation groups in API documentation (Swagger/OpenAPI) to make contracts explicit to consumers

## Continuation Context


Verify commands:
- grep -r '@Validated' --include='*Controller.java' | wc -l
- find . -path '*/validation/groups/*.java' -type f | wc -l
- grep -r 'groups\s*=' --include='*.java' | grep -E '@(NotNull|NotBlank|Size|Pattern|Valid)' | wc -l

Accept when:
- All public API controller methods use @Validated annotation with explicit validation groups
- At least one validation group interface exists per major entity type exposed through public APIs
- Validation constraints on public API DTOs specify appropriate groups attribute
- ArchUnit tests pass verifying validation group usage on public endpoints

## Enforcement

- Verified by: ArchUnit tests in CI pipeline checking for @Validated annotation on public API controllers
- Verified by: Code review checklist requiring validation group verification for new API endpoints
- Verified by: Integration tests validating that invalid requests are rejected with appropriate error messages
- Verified by: Static analysis tools scanning for validation annotations without group specifications on public API classes
- Violation handling: CI pipeline fails if ArchUnit tests detect public API endpoints without @Validated annotation
- Violation handling: Code review blocks merge if validation groups are missing from new public API endpoints
- Violation handling: Security review triggered for any public API endpoint bypassing validation framework
- Violation handling: Technical debt ticket created for existing violations with prioritization based on API exposure level
- Exception process: Developer submits exception request to Tech Lead with justification and alternative validation approach
- Exception process: Tech Lead reviews request and approves/rejects based on technical merit and risk assessment
- Exception process: Approved exceptions must be documented in controller JavaDoc with ADR reference and expiration date
- Exception process: All exceptions reviewed quarterly for potential removal or pattern updates
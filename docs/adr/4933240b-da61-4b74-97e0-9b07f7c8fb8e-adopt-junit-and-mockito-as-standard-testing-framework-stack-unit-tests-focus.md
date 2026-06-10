# Adopt JUnit and Mockito as Standard Testing Framework Stack: Unit Tests Focus

Status: proposed
Date: 2024-01-20
Deciders: Detection Pipeline (automated)

## Context

- The codebase contains 12 test files demonstrating consistent use of JUnit and Mockito frameworks across unit tests, integration tests, and UI tests
- Test files follow a clear naming convention distinguishing integration tests (IT suffix) from unit tests (Test suffix), indicating a mature testing strategy
- The appointment scheduler application requires comprehensive testing coverage across multiple layers: service layer, validation layer, and UI layer
- The pattern shows 91.53% confidence across service tests, validation tests, and integration tests, indicating organization-wide standardization
- Java Spring Boot applications benefit from a unified testing approach that supports both isolated unit testing and full-stack integration testing

## Problem Statement

Without a standardized testing framework, teams may adopt inconsistent testing approaches leading to fragmented test suites, reduced code maintainability, difficulty in onboarding new developers, and challenges in establishing consistent quality gates across the codebase.

## Decision

1. SHOULD: Unit tests SHOULD focus on testing a single class or component in isolation

## Policy Block

- SHOULD Unit tests SHOULD focus on testing a single class or component in isolation

In scope:
- All Java test code in the test/ directory
- Unit tests for service layer components
- Integration tests for multi-component workflows
- Validation layer tests
- UI layer tests
- Factory and utility class tests

Out of scope:
- Performance and load testing (may use specialized tools like JMeter)
- End-to-end system tests that span multiple services
- Manual exploratory testing
- Security penetration testing
- Non-Java test code (e.g., JavaScript frontend tests)

Exceptions:
- EX-001: Legacy test code exists using TestNG or other frameworks
- EX-002: Specialized testing scenarios require framework-specific features not available in JUnit/Mockito

## Rationale

- JUnit is the de facto standard for Java testing with extensive community support, comprehensive documentation, and excellent IDE integration
- Mockito provides intuitive API for creating mocks and stubs, enabling effective isolation of units under test without complex setup
- The pattern evidence shows 91.53% confidence across 12 files, demonstrating this is already the established practice in the codebase
- Standardizing on a single framework stack reduces cognitive load, simplifies CI/CD pipeline configuration, and facilitates knowledge sharing across teams

## Consequences

Positive:
- Consistent testing approach across all Java components improves code maintainability and readability
- New team members can quickly become productive with well-documented, industry-standard frameworks
- Simplified dependency management with fewer testing framework dependencies to maintain
- Enhanced test reliability through mature, battle-tested frameworks with extensive community support
- Easier to establish and enforce quality gates with standardized test execution and reporting

Negative:
- Teams already invested in alternative frameworks (e.g., TestNG, Spock) will face migration costs
- Some advanced testing scenarios may require workarounds if framework-specific features are needed
- Learning curve for developers unfamiliar with JUnit/Mockito, though this is minimal given their popularity
- Potential lock-in to JUnit ecosystem, though this is mitigated by its open-source nature and widespread adoption

## Alternatives

- Use TestNG as the primary testing framework instead of JUnit (rejected)
  Rejected because: JUnit has broader adoption in the Spring Boot ecosystem, better IDE support, and the codebase already demonstrates strong JUnit standardization with 12 files showing consistent usage
  When valid: For projects requiring advanced test configuration features like flexible test suite composition or complex parameterization that JUnit 4 doesn't support well
- Allow teams to choose their preferred testing framework on a per-module basis (rejected)
  Rejected because: Would lead to fragmentation, increased maintenance burden, inconsistent CI/CD pipelines, and knowledge silos across teams
  When valid: In polyglot environments where different languages naturally require different frameworks
- Use Spock framework for more expressive BDD-style tests (rejected)
  Rejected because: Requires Groovy knowledge, adds language complexity to Java codebase, and evidence shows team has already standardized on JUnit/Mockito
  When valid: For teams with strong Groovy expertise who prioritize highly readable specification-style tests

## Risks

- Existing tests using alternative frameworks may be neglected or become inconsistent with new standards
  Mitigation: Create migration plan with prioritized backlog items to convert legacy tests; establish deprecation timeline for non-standard frameworks
  Owner: Engineering Team Lead
- Developers may misuse Mockito leading to brittle tests that are tightly coupled to implementation details
  Mitigation: Provide training on testing best practices; establish code review guidelines emphasizing behavior verification over implementation verification; create test quality checklist
  Owner: Quality Engineering Team
- Over-reliance on mocking may mask integration issues that only surface in production
  Mitigation: Maintain balanced test pyramid with appropriate mix of unit tests (with mocks) and integration tests (with real dependencies); enforce integration test coverage requirements
  Owner: Architecture Team

## Implementation Notes

- Add JUnit and Mockito dependencies to the project's parent POM or build.gradle with specific version management to ensure consistency
- Configure Maven Surefire plugin to recognize *Test.java pattern for unit tests and Maven Failsafe plugin for *IT.java integration tests
- Create test base classes or utility methods for common setup patterns to reduce boilerplate across test files
- Establish test naming conventions: test methods should clearly describe the scenario being tested (e.g., shouldReturnUserWhenValidIdProvided)
- Document common Mockito patterns in team wiki: when/thenReturn for stubbing, verify for behavior verification, @Mock and @InjectMocks annotations
- Set up IDE templates for generating test class skeletons that follow the established patterns

## Continuation Context


Verify commands:
- grep -r "import org.junit" test/java/ | wc -l
- grep -r "import org.mockito" test/java/ | wc -l
- find test/java -name '*IT.java' -o -name '*Test.java' | wc -l
- mvn test -Dtest=**/*Test.java && mvn verify -Dit.test=**/*IT.java

Accept when:
- All test files in test/java directory import org.junit packages
- Test files follow naming convention: unit tests end with 'Test.java' and integration tests end with 'IT.java'
- Mock objects are created using org.mockito framework imports
- Maven/Gradle build successfully executes both unit tests and integration tests with separate phases

## Enforcement

- Verified by: Automated CI pipeline checks that verify JUnit and Mockito are the only testing frameworks in use
- Verified by: Code review checklist includes verification of test naming conventions and framework usage
- Verified by: Static analysis tools (e.g., ArchUnit) validate test package structure and naming patterns
- Verified by: Dependency analysis in build pipeline flags introduction of alternative testing frameworks
- Violation handling: CI build fails if tests are found that don't follow naming conventions (*Test.java or *IT.java)
- Violation handling: Pull requests introducing alternative testing frameworks are automatically flagged for architecture review
- Violation handling: Quarterly audits identify non-compliant tests and create remediation tickets
- Violation handling: Test coverage reports exclude non-standard tests from metrics to incentivize compliance
- Exception process: Submit exception request to Architecture Review Board with justification and impact analysis
- Exception process: Document specific technical requirements that cannot be met with JUnit/Mockito
- Exception process: Obtain approval from Tech Lead and at least one Architecture Review Board member
- Exception process: Record approved exception in ADR supplement with expiration date and re-evaluation criteria
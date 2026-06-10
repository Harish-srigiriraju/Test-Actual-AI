# Adopt Spring Framework Constructor-Based Dependency Injection as Standard: Spring Managed Components

These rules are ALWAYS ACTIVE for all Spring-managed components including services, validators, controllers, configuration classes, and security components in the codebase.

### Rules

- **R-CDI-001** MUST: All Spring-managed components (@Service, @Component, @Controller, @RestController, @Configuration annotated classes) MUST use constructor-based dependency injection for required dependencies.
- **R-CDI-002** MUST: All injected dependencies in Spring-managed components MUST be declared as final fields to ensure immutability and thread-safety.
- **R-CDI-003** MUST: No @Autowired annotations MUST appear on private fields in production code (src/main/java); field injection is prohibited.
- **R-CDI-004** SHOULD: Classes with more than 5 constructor dependencies SHOULD be reviewed for Single Responsibility Principle violations and potential decomposition.
- **R-CDI-005** MAY: Use Lombok's @RequiredArgsConstructor annotation to reduce boilerplate for classes with multiple final dependencies.
- **R-CDI-006** MAY: Use @Lazy annotation only as a temporary measure for circular dependencies with documented technical debt and linked ticket.

### Verify

```bash
# Check for field injection in production code
grep -r '@Autowired' --include='*.java' src/main/java | grep -v 'constructor' | grep 'private' && echo 'Field injection detected' || echo 'No field injection found'

# Check for service implementations without constructors
find src/main/java -name '*ServiceImpl.java' -exec grep -L 'public.*ServiceImpl(' {} \; | wc -l

# Run service unit tests
./gradlew test --tests '*ServiceTest' || ./mvnw test -Dtest='*ServiceTest'
```

**Accept when:**
- All service implementations use constructor-based dependency injection with final fields
- No @Autowired annotations appear on private fields in production code (src/main/java)
- All unit tests can instantiate service classes without Spring context by providing constructor arguments
- Static analysis tools (SonarQube/Checkstyle) report zero field injection violations
- Classes with >5 dependencies have been reviewed and documented in code review or architecture decision log

<enforcement>
Claude Code MUST NOT skip or defer verification. Static analysis violations block CI pipeline and pull request merging. Existing violations in legacy code must be tracked as technical debt with suppression comments and linked tickets.
</enforcement>
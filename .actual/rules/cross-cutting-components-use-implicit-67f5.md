# Adopt Spring Framework Constructor-Based Dependency Injection as Standard: Components Use Implicit

These rules are ALWAYS ACTIVE for all @Service, @Component, @Controller, @RestController, @Configuration annotated classes, custom validators implementing Spring Validation interfaces, security components including UserDetailsService implementations, and repository interfaces in production code.

### Rules

- **R-DI-001** SHOULD: Components SHOULD use implicit constructor injection when there is only one constructor (omitting @Autowired annotation as Spring 4.3+ does this automatically).
- **R-DI-002** MUST: Constructor-injected dependencies MUST be declared as final fields to ensure immutability and thread-safety.
- **R-DI-003** SHOULD: Classes with more than five constructor dependencies SHOULD be reviewed for Single Responsibility Principle violations and potential decomposition.
- **R-DI-004** MUST: Production code (src/main/java) MUST NOT use @Autowired field injection on private fields.
- **R-DI-005** MAY: Integration tests MAY use @Autowired field injection to simplify test setup without impacting production code quality.
- **R-DI-006** SHOULD: Circular dependencies SHOULD be resolved through architectural refactoring rather than @Lazy annotation; @Lazy MAY only be used as a temporary measure with documented technical debt.

### Verify

```bash
# Detect field injection in production code
grep -r '@Autowired' --include='*.java' src/main/java | grep -v 'constructor' | grep 'private' && echo 'Field injection detected' || echo 'No field injection found'

# Check for service implementations without constructors
find src/main/java -name '*ServiceImpl.java' -exec grep -L 'public.*ServiceImpl(' {} \; | wc -l

# Run unit tests to verify components can be instantiated without Spring context
./gradlew test --tests '*ServiceTest' || ./mvnw test -Dtest='*ServiceTest'
```

**Accept when:**
- All service implementations use constructor-based dependency injection with final fields
- No @Autowired annotations appear on private fields in production code (src/main/java)
- All unit tests can instantiate service classes without Spring context by providing constructor arguments
- Static analysis tools (SonarQube/Checkstyle) report zero field injection violations
- Classes with >5 constructor dependencies have been reviewed and documented as necessary or marked for refactoring

<enforcement>
Claude Code MUST NOT skip or defer verification. Static analysis violations block CI pipeline. Pull requests with field injection violations are blocked from merging. Existing violations in legacy code must be tracked as technical debt with suppression comments and linked tickets.
</enforcement>
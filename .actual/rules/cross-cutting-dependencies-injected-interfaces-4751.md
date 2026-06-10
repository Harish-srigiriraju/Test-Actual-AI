# Adopt Spring Framework Constructor-Based Dependency Injection as Standard: Dependencies Injected Interfaces

These rules are ALWAYS ACTIVE for all @Service, @Component, @Controller, @RestController, @Configuration annotated classes, custom validators implementing Spring Validation interfaces, security components including UserDetailsService implementations, and repository interfaces in production code.

### Rules

- **R-CDI-001** SHOULD: Dependencies SHOULD be injected as interfaces rather than concrete implementations to support loose coupling and testability.
- **R-CDI-002** MUST: Constructor-based dependency injection MUST be used for all Spring-managed components instead of field injection or setter injection.
- **R-CDI-003** SHOULD: Dependencies SHOULD be declared as final fields to ensure immutability and thread-safety.
- **R-CDI-004** SHOULD: Classes with more than 5 constructor dependencies SHOULD trigger architectural review to consider component decomposition.
- **R-CDI-005** MAY: Lombok's @RequiredArgsConstructor MAY be used to reduce boilerplate for classes with multiple final dependencies.

### Verify

```bash
# Check for field injection in production code
grep -r '@Autowired' --include='*.java' src/main/java | grep -v 'constructor' | grep 'private' && echo 'Field injection detected' || echo 'No field injection found'

# Count service implementations without constructor injection
find src/main/java -name '*ServiceImpl.java' -exec grep -L 'public.*ServiceImpl(' {} \; | wc -l

# Run service unit tests
./gradlew test --tests '*ServiceTest' || ./mvnw test -Dtest='*ServiceTest'
```

**Accept when:**
- All service implementations use constructor-based dependency injection with final fields
- No @Autowired annotations appear on private fields in production code (src/main/java)
- All unit tests can instantiate service classes without Spring context by providing constructor arguments
- Static analysis tools (SonarQube/Checkstyle) report zero field injection violations
- Classes with constructor dependencies are reviewed for Single Responsibility Principle compliance

<enforcement>
Claude Code MUST NOT skip or defer verification. Static analysis violations related to dependency injection MUST block CI pipeline and pull request merging. Existing violations in legacy code MUST be tracked as technical debt with suppression comments and linked tickets.
</enforcement>
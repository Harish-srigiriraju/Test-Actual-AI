# Adopt Spring Framework Constructor-Based Dependency Injection as Standard: Components Use Setter

These rules are ALWAYS ACTIVE for all Java Spring Framework components including services, validators, configuration classes, and security components in scope.

### Rules

- **R-DI-001** MAY: Components MAY use setter injection only for optional dependencies that have reasonable defaults.
- **R-DI-002** MUST: All @Service annotated classes use constructor-based dependency injection with final fields.
- **R-DI-003** MUST: All @Component annotated classes use constructor-based dependency injection with final fields.
- **R-DI-004** MUST: All @Controller and @RestController annotated classes use constructor-based dependency injection with final fields.
- **R-DI-005** MUST: All @Configuration annotated classes use constructor-based dependency injection with final fields.
- **R-DI-006** MUST: Custom validators implementing Spring Validation interfaces use constructor-based dependency injection with final fields.
- **R-DI-007** MUST: Security components including UserDetailsService implementations use constructor-based dependency injection with final fields.
- **R-DI-008** MUST: No @Autowired annotations appear on private fields in production code (src/main/java).
- **R-DI-009** SHOULD: Use Lombok's @RequiredArgsConstructor annotation to reduce boilerplate for classes with multiple final dependencies.
- **R-DI-010** SHOULD: Classes with >5 constructor dependencies trigger architectural review to consider component decomposition.
- **R-DI-011** SHOULD: Circular dependencies requiring @Lazy annotation must be documented with TODO comments and linked tickets as technical debt.

### Verify

```bash
# Detect field injection in production code
grep -r '@Autowired' --include='*.java' src/main/java | grep -v 'constructor' | grep 'private' && echo 'Field injection detected' || echo 'No field injection found'

# Check for service implementations without constructor injection
find src/main/java -name '*ServiceImpl.java' -exec grep -L 'public.*ServiceImpl(' {} \; | wc -l

# Run unit tests to verify components can be instantiated without Spring context
./gradlew test --tests '*ServiceTest' || ./mvnw test -Dtest='*ServiceTest'
```

**Accept when:**
- All service implementations use constructor-based dependency injection with final fields
- No @Autowired annotations appear on private fields in production code (src/main/java)
- All unit tests can instantiate service classes without Spring context by providing constructor arguments
- Static analysis tools (SonarQube/Checkstyle) report zero field injection violations
- Grep command for field injection returns no matches
- Service implementation files all contain public constructors with dependency parameters

<enforcement>
Claude Code MUST NOT skip or defer verification. Static analysis tools (SonarQube rule: squid:S3306) and Checkstyle rules configured to detect @Autowired on fields MUST pass. CI pipeline MUST fail on violations. Pull requests with field injection violations MUST be blocked from merging.
</enforcement>
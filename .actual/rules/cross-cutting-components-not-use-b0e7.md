# Adopt Spring Framework Constructor-Based Dependency Injection as Standard: Components Not Use

These rules are ALWAYS ACTIVE for all @Service, @Component, @Controller, @RestController, @Configuration annotated classes, custom validators implementing Spring Validation interfaces, security components including UserDetailsService implementations, and repository interfaces in production code.

### Rules

- **R-DI-001** MUST_NOT: Components MUST NOT use field injection (@Autowired on fields) for required dependencies.

### Verify

```bash
# Check for field injection in production code
grep -r '@Autowired' --include='*.java' src/main/java | grep -v 'constructor' | grep 'private' && echo 'Field injection detected' || echo 'No field injection found'

# Check for ServiceImpl classes without constructor injection
find src/main/java -name '*ServiceImpl.java' -exec grep -L 'public.*ServiceImpl(' {} \; | wc -l

# Run service tests to verify constructor-based instantiation
./gradlew test --tests '*ServiceTest' || ./mvnw test -Dtest='*ServiceTest'
```

**Accept when:**
- All service implementations use constructor-based dependency injection with final fields
- No @Autowired annotations appear on private fields in production code (src/main/java)
- All unit tests can instantiate service classes without Spring context by providing constructor arguments
- Static analysis tools (SonarQube/Checkstyle) report zero field injection violations

<enforcement>
Claude Code MUST NOT skip or defer verification. Static analysis tools (SonarQube rule: squid:S3306) and Checkstyle rules configured to detect @Autowired on fields are mandatory. CI pipeline MUST fail on violations. Code review checklist MUST include verification of constructor injection pattern.
</enforcement>
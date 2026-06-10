# Adopt Spring Framework Constructor-Based Dependency Injection as Standard: Injected Dependencies Validated

These rules are ALWAYS ACTIVE for all @Service, @Component, @Controller, @RestController, @Configuration annotated classes, custom validators implementing Spring Validation interfaces, security components including UserDetailsService implementations, and repository interfaces and custom repository implementations.

### Rules

- **R-INJECT-001** MUST: All injected dependencies MUST be validated as non-null either through Spring's required dependency checking or explicit validation in constructor.

### Verify

```bash
# Detect field injection in production code
grep -r '@Autowired' --include='*.java' src/main/java | grep -v 'constructor' | grep 'private' && echo 'Field injection detected' || echo 'No field injection found'

# Count service implementations without constructor injection
find src/main/java -name '*ServiceImpl.java' -exec grep -L 'public.*ServiceImpl(' {} \; | wc -l

# Run unit tests to verify components can be instantiated without Spring context
./gradlew test --tests '*ServiceTest' || ./mvnw test -Dtest='*ServiceTest'
```

**Accept when:**
- All service implementations use constructor-based dependency injection with final fields
- No @Autowired annotations appear on private fields in production code (src/main/java)
- All unit tests can instantiate service classes without Spring context by providing constructor arguments
- Static analysis tools (SonarQube/Checkstyle) report zero field injection violations

<enforcement>
Claude Code MUST NOT skip or defer verification. All injected dependencies in Spring-managed components must be validated as non-null through constructor injection patterns.
</enforcement>
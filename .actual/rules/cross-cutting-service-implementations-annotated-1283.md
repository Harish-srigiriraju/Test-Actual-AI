# Adopt Spring Framework Constructor-Based Dependency Injection as Standard: Service Implementations Annotated

These rules are ALWAYS ACTIVE for all Java Spring Framework components including services, validators, configuration classes, and security components in scope.

### Rules

- **R-SPRING-DI-001** MUST: Service implementations MUST be annotated with @Service and follow the naming convention *ServiceImpl.
- **R-SPRING-DI-002** MUST: All @Service, @Component, @Controller, @RestController, and @Configuration annotated classes MUST use constructor-based dependency injection with final fields.
- **R-SPRING-DI-003** MUST: No @Autowired annotations on private fields are permitted in production code (src/main/java).
- **R-SPRING-DI-004** SHOULD: Classes with more than 5 constructor dependencies SHOULD be reviewed for Single Responsibility Principle violations and potential decomposition.
- **R-SPRING-DI-005** SHOULD: Use Lombok's @RequiredArgsConstructor annotation to reduce boilerplate for classes with multiple final dependencies.
- **R-SPRING-DI-006** MAY: @Lazy annotation may be used as a temporary measure for circular dependencies only with documented technical debt and linked ticket.

### Verify

```bash
# Detect field injection in production code
grep -r '@Autowired' --include='*.java' src/main/java | grep -v 'constructor' | grep 'private' && echo 'Field injection detected' || echo 'No field injection found'

# Find service implementations without constructor
find src/main/java -name '*ServiceImpl.java' -exec grep -L 'public.*ServiceImpl(' {} \; | wc -l

# Run service unit tests
./gradlew test --tests '*ServiceTest' || ./mvnw test -Dtest='*ServiceTest'
```

**Accept when:**
- All service implementations use constructor-based dependency injection with final fields
- No @Autowired annotations appear on private fields in production code (src/main/java)
- All unit tests can instantiate service classes without Spring context by providing constructor arguments
- Static analysis tools (SonarQube/Checkstyle) report zero field injection violations
- Service implementations follow the *ServiceImpl naming convention and are annotated with @Service

<enforcement>
Claude Code MUST NOT skip or defer verification. All rules in this file are mandatory for Spring-managed components in scope. Violations detected by static analysis or manual review MUST be remediated or formally documented as exceptions (EX-001 or EX-002) with tech lead approval.
</enforcement>
# Adopt Spring Framework as Standard Application Framework: Java Based Applications

These rules are ALWAYS ACTIVE for all Java-based application development within the organization.

### Rules

- **R-SPRING-001** MUST: All Java-based applications MUST use Spring Framework as the primary application framework for dependency injection, configuration management, and core infrastructure.
- **R-SPRING-002** MUST: All Java application modules MUST include spring-boot-starter dependencies in their build configuration (pom.xml or build.gradle).
- **R-SPRING-003** MUST: Each Java application module MUST contain at least one @SpringBootApplication annotated main class.
- **R-SPRING-004** SHOULD: Service, repository, and controller layers SHOULD consistently use appropriate Spring stereotype annotations (@Service, @Repository, @Controller, @RestController).
- **R-SPRING-005** SHOULD: New projects SHOULD be bootstrapped using Spring Initializr (start.spring.io) with appropriate starter dependencies.
- **R-SPRING-006** SHOULD: Code SHOULD be organized into clear layers: controllers (@RestController/@Controller), services (@Service), repositories (Spring Data interfaces), and entities (@Entity).
- **R-SPRING-007** SHOULD: Applications SHOULD leverage Spring Boot's application.properties or application.yml for externalized configuration with @ConfigurationProperties for type-safe binding.
- **R-SPRING-008** SHOULD: Dependency injection SHOULD follow constructor-based patterns for required dependencies to improve testability and make dependencies explicit.
- **R-SPRING-009** MAY: Performance-critical microservices MAY request exceptions for minimal framework overhead and startup time requirements (EXC-001).
- **R-SPRING-010** MAY: Integration with third-party systems MAY request exceptions when specific framework compatibility is mandated (EXC-002).

### Verify

```bash
# Count @SpringBootApplication annotated classes
grep -r '@SpringBootApplication' --include='*.java' . | wc -l

# Count Spring Framework imports
grep -r 'import org.springframework' --include='*.java' . | wc -l

# Find pom.xml files with spring-boot-starter dependencies
find . -name 'pom.xml' -exec grep -l 'spring-boot-starter' {} \;

# Count Spring stereotype annotations
grep -r '@Service\|@Repository\|@Controller\|@RestController' --include='*.java' . | wc -l
```

**Accept when:**
- At least one @SpringBootApplication annotated main class exists in each Java application module
- Spring Framework imports are present in more than 80% of Java source files requiring dependency injection or framework features
- All pom.xml or build.gradle files include spring-boot-starter dependencies for required capabilities
- Service, repository, and controller layers consistently use appropriate Spring stereotype annotations (@Service, @Repository, @Controller, @RestController)

<enforcement>
Claude Code MUST NOT skip or defer verification. CI pipeline MUST fail if Spring Boot starter dependencies are missing from new Java application modules. Code review feedback MUST require refactoring of manual dependency management to use Spring dependency injection. Architecture review board MUST evaluate exception requests for non-Spring frameworks with documented justification.
</enforcement>
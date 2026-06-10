# Adopt Spring Framework as Standard Application Framework: Spring Boot Used

These rules are ALWAYS ACTIVE for all Java-based backend services, web MVC controllers, REST API endpoints, service layer components, data access layer repositories, security configurations, and dependency injection patterns within the organization.

### Rules

- **R-SPRING-001** MUST: Spring Boot MUST be used as the application bootstrapping mechanism with @SpringBootApplication annotation on the main application class.
- **R-SPRING-002** MUST: All Java-based backend services and applications MUST use Spring Framework for dependency injection, component lifecycle management, and bean wiring.
- **R-SPRING-003** MUST: Web MVC controllers and REST API endpoints MUST use Spring stereotype annotations (@RestController, @Controller) for proper framework integration.
- **R-SPRING-004** MUST: Service layer business logic components MUST use @Service annotation for Spring component management.
- **R-SPRING-005** MUST: Data access layer repositories MUST use Spring Data interfaces or @Repository annotation for consistent repository patterns.
- **R-SPRING-006** MUST: Security configuration and authentication mechanisms MUST leverage Spring Security framework for battle-tested protection against common vulnerabilities.
- **R-SPRING-007** SHOULD: Constructor-based dependency injection SHOULD be preferred over field injection to improve testability and make dependencies explicit.
- **R-SPRING-008** SHOULD: Application configuration SHOULD use Spring Boot's application.properties or application.yml with @ConfigurationProperties for type-safe configuration binding.
- **R-SPRING-009** SHOULD: Integration tests SHOULD use @SpringBootTest annotation to verify proper Spring context initialization and component wiring.
- **R-SPRING-010** SHOULD: Spring profiles (@Profile) SHOULD be used to manage environment-specific configurations for development, testing, and production deployments.

### Verify

```bash
# Verify @SpringBootApplication presence
grep -r '@SpringBootApplication' --include='*.java' . | wc -l

# Verify Spring Framework imports
grep -r 'import org.springframework' --include='*.java' . | wc -l

# Verify Spring Boot starter dependencies in build files
find . -name 'pom.xml' -exec grep -l 'spring-boot-starter' {} \;

# Verify Spring stereotype annotations usage
grep -r '@Service\|@Repository\|@Controller\|@RestController' --include='*.java' . | wc -l
```

**Accept when:**
- At least one @SpringBootApplication annotated main class exists in each Java application module
- Spring Framework imports are present in more than 80% of Java source files requiring dependency injection or framework features
- All pom.xml or build.gradle files include spring-boot-starter dependencies for required capabilities
- Service, repository, and controller layers consistently use appropriate Spring stereotype annotations (@Service, @Repository, @Controller, @RestController)

<enforcement>
Claude Code MUST NOT skip or defer verification of Spring Boot adoption and proper Spring Framework usage patterns. Violations MUST be flagged during code review and architecture compliance checks.
</enforcement>
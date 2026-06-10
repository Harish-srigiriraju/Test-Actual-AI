# Adopt Spring Framework as Standard Application Framework: Service Layer Components

These rules are ALWAYS ACTIVE for all Java-based backend services, web MVC controllers, REST API endpoints, service layer business logic components, data access layer repositories and entities, security configuration, and application configuration within the organization.

### Rules

- **R-SPRING-001** MUST: Service layer components MUST be annotated with @Service and implement business logic through dependency-injected repositories and other services.

### Verify

```bash
# Verify Spring Boot application entry point exists
grep -r '@SpringBootApplication' --include='*.java' . | wc -l

# Verify Spring Framework imports are present
grep -r 'import org.springframework' --include='*.java' . | wc -l

# Verify Spring Boot starter dependencies in build files
find . -name 'pom.xml' -exec grep -l 'spring-boot-starter' {} \;

# Verify proper use of Spring stereotype annotations
grep -r '@Service\|@Repository\|@Controller\|@RestController' --include='*.java' . | wc -l
```

**Accept when:**
- At least one @SpringBootApplication annotated main class exists in each Java application module
- Spring Framework imports are present in more than 80% of Java source files requiring dependency injection or framework features
- All pom.xml or build.gradle files include spring-boot-starter dependencies for required capabilities
- Service, repository, and controller layers consistently use appropriate Spring stereotype annotations (@Service, @Repository, @Controller, @RestController)

<enforcement>
Claude Code MUST NOT skip or defer verification of Spring Framework adoption and proper service layer annotation usage.
</enforcement>
# Adopt Spring Framework as Standard Application Framework: Custom Security Implementations

These rules are ALWAYS ACTIVE for all Java-based backend services, web MVC controllers, REST API endpoints, service layer components, data access layer repositories, security configurations, and application configuration management.

### Rules

- **R-SPRING-SEC-001** SHOULD: Custom security implementations SHOULD extend Spring Security's UserDetailsService for authentication and authorization.

### Verify

```bash
# Verify Spring Boot application class exists
grep -r '@SpringBootApplication' --include='*.java' . | wc -l

# Verify Spring Framework imports are present
grep -r 'import org.springframework' --include='*.java' . | wc -l

# Verify Spring Boot starter dependencies in build files
find . -name 'pom.xml' -exec grep -l 'spring-boot-starter' {} \;

# Verify Spring stereotype annotations are used
grep -r '@Service\|@Repository\|@Controller\|@RestController' --include='*.java' . | wc -l

# Verify UserDetailsService implementations
grep -r 'implements UserDetailsService\|extends UserDetailsService' --include='*.java' .
```

**Accept when:**
- At least one @SpringBootApplication annotated main class exists in each Java application module
- Spring Framework imports are present in more than 80% of Java source files requiring dependency injection or framework features
- All pom.xml or build.gradle files include spring-boot-starter dependencies for required capabilities
- Service, repository, and controller layers consistently use appropriate Spring stereotype annotations (@Service, @Repository, @Controller, @RestController)
- Custom security implementations extend Spring Security's UserDetailsService interface

<enforcement>
Claude Code MUST NOT skip or defer verification. All rules in this file are mandatory for Java-based applications within scope.
</enforcement>
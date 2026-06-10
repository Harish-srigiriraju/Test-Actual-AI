# Adopt Spring Framework as Standard Application Framework: Custom Validation Annotations

These rules are ALWAYS ACTIVE for all Java-based backend services, web MVC controllers, REST API endpoints, service layer components, data access layer repositories and entities, security configuration, and application configuration requiring dependency injection and bean lifecycle management.

### Rules

- **R-SPRING-VAL-001** SHOULD: Custom validation annotations SHOULD leverage Spring's validation framework with @Constraint annotations and ConstraintValidator implementations.

### Verify

```bash
# Verify Spring Boot application class exists
grep -r '@SpringBootApplication' --include='*.java' . | wc -l

# Verify Spring Framework imports are present
grep -r 'import org.springframework' --include='*.java' . | wc -l

# Verify Spring Boot starter dependencies in build files
find . -name 'pom.xml' -exec grep -l 'spring-boot-starter' {} \;

# Verify Spring stereotype annotations usage
grep -r '@Service\|@Repository\|@Controller\|@RestController' --include='*.java' . | wc -l

# Verify custom validation annotations use @Constraint
grep -r '@Constraint' --include='*.java' . | wc -l

# Verify ConstraintValidator implementations
grep -r 'implements ConstraintValidator' --include='*.java' . | wc -l
```

**Accept when:**
- At least one @SpringBootApplication annotated main class exists in each Java application module
- Spring Framework imports are present in more than 80% of Java source files requiring dependency injection or framework features
- All pom.xml or build.gradle files include spring-boot-starter dependencies for required capabilities
- Service, repository, and controller layers consistently use appropriate Spring stereotype annotations (@Service, @Repository, @Controller, @RestController)
- Custom validation annotations use @Constraint annotation from javax.validation or jakarta.validation
- ConstraintValidator implementations are provided for all custom validation annotations

<enforcement>
Claude Code MUST NOT skip or defer verification of Spring validation framework compliance. All custom validation annotations MUST be reviewed for proper @Constraint and ConstraintValidator usage before acceptance.
</enforcement>
# Adopt Spring Framework as Standard Application Framework: Entity Classes Use

These rules are ALWAYS ACTIVE for all Java-based backend services, web MVC controllers, service layer components, data access layer repositories and entities, security configuration, and application configuration within the organization.

### Rules

- **R-SPRING-001** SHOULD: Entity classes SHOULD use JPA annotations (@Entity, @Table, @Id) for ORM mapping and be managed by Spring Data repositories.

### Verify

```bash
# Verify Spring Boot application main class exists
grep -r '@SpringBootApplication' --include='*.java' . | wc -l

# Verify Spring Framework imports are present
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
Claude Code MUST NOT skip or defer verification. Automated CI pipeline checks MUST verify presence of Spring Boot dependencies in build configuration files. Code review process MUST validate proper use of Spring annotations and dependency injection patterns. Static analysis tools MUST scan for Spring anti-patterns and improper annotation usage.
</enforcement>
# Adopt Spring Framework as Standard Application Framework: Data Access Layer

These rules are ALWAYS ACTIVE for all Java-based backend services, web MVC controllers, service layer components, data access layer repositories and entities, security configuration, and application configuration requiring dependency injection and bean lifecycle management.

### Rules

- **R-DAL-001** MUST: Data access layer MUST use Spring Data repositories extending JpaRepository or CrudRepository interfaces.

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

# Verify repositories extend Spring Data interfaces
grep -r 'extends JpaRepository\|extends CrudRepository' --include='*.java' . | wc -l
```

**Accept when:**
- At least one @SpringBootApplication annotated main class exists in each Java application module
- Spring Framework imports are present in more than 80% of Java source files requiring dependency injection or framework features
- All pom.xml or build.gradle files include spring-boot-starter dependencies for required capabilities
- Service, repository, and controller layers consistently use appropriate Spring stereotype annotations (@Service, @Repository, @Controller, @RestController)
- Data access repositories extend JpaRepository or CrudRepository interfaces

<enforcement>
Claude Code MUST NOT skip or defer verification of Spring Data repository patterns in the data access layer.
</enforcement>
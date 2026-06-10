# Adopt Spring Framework as Standard Application Framework: Web Layer Configuration

These rules are ALWAYS ACTIVE for all Java-based backend services, web MVC controllers, REST API endpoints, service layer components, data access layer repositories, security configurations, and application configuration files.

### Rules

- **R-SPRING-WEB-001** MUST: Web layer configuration MUST extend WebMvcConfigurer for customizing Spring MVC behavior including CORS, interceptors, and resource handlers.

### Verify

```bash
# Verify Spring Boot application entry point exists
grep -r '@SpringBootApplication' --include='*.java' . | wc -l

# Verify Spring Framework imports are present
grep -r 'import org.springframework' --include='*.java' . | wc -l

# Verify Spring Boot starter dependencies in build files
find . -name 'pom.xml' -exec grep -l 'spring-boot-starter' {} \;

# Verify Spring stereotype annotations are used
grep -r '@Service\|@Repository\|@Controller\|@RestController' --include='*.java' . | wc -l

# Verify WebMvcConfigurer implementations
grep -r 'implements WebMvcConfigurer\|extends WebMvcConfigurer' --include='*.java' .
```

**Accept when:**
- At least one @SpringBootApplication annotated main class exists in each Java application module
- Spring Framework imports are present in more than 80% of Java source files requiring dependency injection or framework features
- All pom.xml or build.gradle files include spring-boot-starter dependencies for required capabilities
- Service, repository, and controller layers consistently use appropriate Spring stereotype annotations (@Service, @Repository, @Controller, @RestController)
- Web layer configuration classes extend WebMvcConfigurer for MVC customization

<enforcement>
Claude Code MUST NOT skip or defer verification of Spring Framework adoption and WebMvcConfigurer implementation in web layer configuration.
</enforcement>
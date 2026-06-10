# Adopt Spring @Configuration Classes for Centralized Bean Definition: Configuration Classes Use

These rules are ALWAYS ACTIVE for all Spring bean definition files, configuration classes, and infrastructure component integrations across web MVC, security, and data access layers.

### Rules

- **R-CONFIG-001** MUST: All Spring bean definitions for infrastructure components (web MVC, security, data access) be declared within classes annotated with `@Configuration`.
- **R-CONFIG-002** MUST: Configuration classes defining Spring beans be organized in dedicated packages (e.g., `config/`, `security/`) with descriptive names indicating their purpose (e.g., `WebMvcConfig`, `PasswordEncoderConfig`, `DataSourceConfig`).
- **R-CONFIG-003** MUST: All `@Bean` methods have explicit return types and be contained within `@Configuration` classes.
- **R-CONFIG-004** SHOULD: Configuration classes use `@EnableXxx` annotations to activate specific Spring features (e.g., `@EnableWebMvc`, `@EnableWebSecurity`) when appropriate for the configuration scope.
- **R-CONFIG-005** SHOULD: Complex bean initialization logic be extracted to separate builder or factory classes rather than embedded in `@Bean` methods.
- **R-CONFIG-006** SHOULD: Non-obvious bean configurations be documented with Javadoc explaining the rationale and any special considerations.
- **R-CONFIG-007** SHOULD: Environment-specific bean configurations use `@Profile` annotations on configuration classes or `@Bean` methods to manage development, staging, and production variants.
- **R-CONFIG-008** MAY: Application business logic components (services, repositories, controllers) use `@Component`, `@Service`, or `@Repository` annotations instead of `@Configuration`.
- **R-CONFIG-009** MAY: Test-specific bean configurations use `@TestConfiguration` instead of `@Configuration` to isolate test context from production configuration.

### Verify

```bash
# Count @Configuration annotations in codebase
grep -r '@Configuration' --include='*.java' | wc -l

# Find configuration classes missing @Configuration annotation
find . -name '*Config.java' -type f | xargs grep -L '@Configuration' | wc -l

# Find @Bean methods outside @Configuration classes
grep -r '@Bean' --include='*.java' | grep -v '@Configuration' | wc -l

# Verify no XML-based Spring configuration files exist
find . -name 'applicationContext.xml' -o -name 'spring-*.xml' | wc -l
```

**Accept when:**
- All configuration classes defining Spring beans are annotated with `@Configuration`
- No XML-based Spring configuration files (applicationContext.xml, spring-*.xml) exist in the codebase
- Configuration classes are organized in dedicated config or security packages
- All `@Bean` methods have explicit return types and are contained within `@Configuration` classes
- Configuration class names clearly indicate their purpose and architectural concern
- Complex bean initialization logic is extracted to separate builder or factory classes
- Environment-specific configurations use `@Profile` annotations appropriately
- Spring application context loads successfully in integration tests

<enforcement>
Claude Code MUST NOT skip or defer verification. All configuration classes must be scanned for `@Configuration` annotation presence, package organization, and `@Bean` method containment before accepting changes to infrastructure or cross-cutting concern bean definitions.
</enforcement>
# Adopt Spring @Configuration Classes for Centralized Bean Definition: Configuration Classes Organized

These rules are ALWAYS ACTIVE for all Spring bean definition files and configuration classes across the application's infrastructure, web MVC, security, and data access layers.

### Rules

- **R-CONFIG-001** MUST: All Spring bean definitions for infrastructure components (web MVC, security, data access) SHALL be contained within classes annotated with `@Configuration`.
- **R-CONFIG-002** SHOULD: Configuration classes SHOULD be organized by architectural concern (e.g., WebMvcConfig for web layer, SecurityConfig for security layer).
- **R-CONFIG-003** SHOULD: Configuration classes SHOULD be placed in dedicated packages: `config/` for general configuration, `security/` for security-specific configuration.
- **R-CONFIG-004** SHOULD: Configuration class names SHOULD be descriptive and clearly indicate the configuration purpose (e.g., WebMvcConfig, PasswordEncoderConfig, DataSourceConfig).
- **R-CONFIG-005** MUST: All `@Bean` methods MUST have explicit return types and be contained within `@Configuration` classes.
- **R-CONFIG-006** SHOULD: `@Bean` methods SHOULD be focused and single-purpose; complex initialization logic SHOULD be extracted to separate builder or factory classes.
- **R-CONFIG-007** SHOULD: Non-obvious bean configurations SHOULD be documented with Javadoc explaining the rationale and any special considerations.
- **R-CONFIG-008** MAY: Environment-specific bean configurations MAY use `@Profile` annotations on configuration classes or `@Bean` methods to manage development, staging, and production configurations.
- **R-CONFIG-009** MUST NOT: Application business logic components MUST NOT be defined in configuration classes; use `@Component`, `@Service`, `@Repository` instead.
- **R-CONFIG-010** MUST NOT: XML-based Spring configuration files (applicationContext.xml) MUST NOT exist in the codebase.

### Verify

```bash
# Count @Configuration annotations in the codebase
grep -r '@Configuration' --include='*.java' | wc -l

# Find configuration classes that lack @Configuration annotation
find . -name '*Config.java' -type f | xargs grep -L '@Configuration' | wc -l

# Find @Bean methods outside @Configuration classes
grep -r '@Bean' --include='*.java' | grep -v '@Configuration' | wc -l

# Verify no XML configuration files exist
find . -name 'applicationContext.xml' -o -name 'spring-*.xml' | wc -l
```

**Accept when:**
- All configuration classes defining Spring beans are annotated with `@Configuration`
- No XML-based Spring configuration files (applicationContext.xml) exist in the codebase
- Configuration classes are organized in dedicated config or security packages
- All `@Bean` methods have explicit return types and are contained within `@Configuration` classes
- Configuration class names clearly indicate their purpose and architectural concern
- No `@Bean` methods are found outside `@Configuration` classes

<enforcement>
Claude Code MUST NOT skip or defer verification of these rules. All configuration classes MUST be validated against R-CONFIG-001 through R-CONFIG-010 before code review approval.
</enforcement>
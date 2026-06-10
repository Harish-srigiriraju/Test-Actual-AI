# Adopt Spring @Configuration Classes for Centralized Bean Definition: Configuration Classes Placed

These rules are ALWAYS ACTIVE for all Spring bean definition files and configuration classes across the application's infrastructure, web MVC, security, and cross-cutting concern layers.

### Rules

- **R-CONFIG-001** MUST: All Spring bean definitions for infrastructure components (web MVC, security, data access) SHALL be placed in dedicated @Configuration classes.
- **R-CONFIG-002** SHOULD: Configuration classes SHOULD be placed in dedicated config or security packages to maintain clear separation of concerns.
- **R-CONFIG-003** MUST: Configuration classes defining Spring beans SHALL be annotated with @Configuration.
- **R-CONFIG-004** MUST: All @Bean methods SHALL have explicit return types and be contained within @Configuration classes.
- **R-CONFIG-005** SHOULD: Configuration class names SHOULD be descriptive and clearly indicate the configuration purpose (e.g., WebMvcConfig, PasswordEncoderConfig, DataSourceConfig).
- **R-CONFIG-006** SHOULD: @Bean methods SHOULD be focused and single-purpose; complex initialization logic SHOULD be extracted to separate builder or factory classes.
- **R-CONFIG-007** SHOULD: Non-obvious bean configurations SHOULD be documented with Javadoc explaining the rationale and any special considerations.
- **R-CONFIG-008** MAY: Environment-specific bean configurations MAY use @Profile annotations on configuration classes or @Bean methods.
- **R-CONFIG-009** MUST NOT: XML-based Spring configuration files (applicationContext.xml) SHALL NOT exist in the codebase.
- **R-CONFIG-010** MUST NOT: Application business logic components (use @Component, @Service, @Repository instead) SHALL NOT be defined in @Configuration classes.
- **R-CONFIG-011** MUST NOT: Simple value injection using @Value annotation SHALL NOT require @Configuration classes.
- **R-CONFIG-012** MUST NOT: Auto-configured beans provided by Spring Boot starters SHALL NOT be redefined in @Configuration classes without explicit justification.

### Verify

```bash
# Count total @Configuration annotations in codebase
grep -r '@Configuration' --include='*.java' | wc -l

# Find *Config.java files that lack @Configuration annotation
find . -name '*Config.java' -type f | xargs grep -L '@Configuration' | wc -l

# Find @Bean methods outside @Configuration classes
grep -r '@Bean' --include='*.java' | grep -v '@Configuration' | wc -l

# Verify no XML configuration files exist
find . -name 'applicationContext.xml' -o -name 'spring-*.xml' | wc -l

# Check configuration class package structure
find . -path '*/config/*Config.java' -o -path '*/security/*Config.java' | wc -l
```

**Accept when:**
- All configuration classes defining Spring beans are annotated with @Configuration
- No XML-based Spring configuration files (applicationContext.xml) exist in the codebase
- Configuration classes are organized in dedicated config or security packages
- All @Bean methods have explicit return types and are contained within @Configuration classes
- Configuration class names clearly indicate their purpose
- No @Bean methods exist outside @Configuration classes
- Complex bean initialization logic is extracted to separate builder or factory classes

<enforcement>
Claude Code MUST NOT skip or defer verification of these rules. All configuration classes MUST be scanned for @Configuration annotation presence, package placement, and @Bean method organization before code review approval.
</enforcement>
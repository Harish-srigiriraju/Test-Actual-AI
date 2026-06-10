# Adopt Spring @Configuration Classes for Centralized Bean Definition: Configuration Classes Use

These rules are ALWAYS ACTIVE for all Spring bean definition classes and configuration files across infrastructure components, web MVC, security, and cross-cutting concern configurations.

### Rules

- **R-CONFIG-001** MUST: Configuration classes MUST use @Bean methods to define beans with explicit dependencies rather than XML configuration.

### Verify

```bash
# Count @Configuration annotations in the codebase
grep -r '@Configuration' --include='*.java' | wc -l

# Find configuration classes missing @Configuration annotation
find . -name '*Config.java' -type f | xargs grep -L '@Configuration' | wc -l

# Count @Bean methods outside @Configuration classes
grep -r '@Bean' --include='*.java' | grep -v '@Configuration' | wc -l
```

**Accept when:**
- All configuration classes defining Spring beans are annotated with @Configuration
- No XML-based Spring configuration files (applicationContext.xml) exist in the codebase
- Configuration classes are organized in dedicated config or security packages
- All @Bean methods have explicit return types and are contained within @Configuration classes

<enforcement>
Claude Code MUST NOT skip or defer verification. All configuration classes must be validated against these rules before acceptance.
</enforcement>
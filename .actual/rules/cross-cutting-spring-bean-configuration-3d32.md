# Adopt Spring @Configuration Classes for Centralized Bean Definition: Spring Bean Configuration

These rules are ALWAYS ACTIVE for all Spring bean configuration classes across web MVC, security, and infrastructure components.

### Rules

- **R-SPRING-CONFIG-001** MUST: All Spring bean configuration classes MUST be annotated with @Configuration to enable component scanning and bean registration.

### Verify

```bash
# Count @Configuration annotations in Java files
grep -r '@Configuration' --include='*.java' | wc -l

# Find Config classes without @Configuration annotation
find . -name '*Config.java' -type f | xargs grep -L '@Configuration' | wc -l

# Find @Bean methods outside @Configuration classes
grep -r '@Bean' --include='*.java' | grep -v '@Configuration' | wc -l
```

**Accept when:**
- All configuration classes defining Spring beans are annotated with @Configuration
- No XML-based Spring configuration files (applicationContext.xml) exist in the codebase
- Configuration classes are organized in dedicated config or security packages
- All @Bean methods have explicit return types and are contained within @Configuration classes

<enforcement>
Claude Code MUST NOT skip or defer verification. All configuration classes must pass automated static analysis and code review before merge.
</enforcement>
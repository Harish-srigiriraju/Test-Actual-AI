# Adopt Spring @Configuration Classes for Centralized Bean Definition: Bean Methods Within

These rules are ALWAYS ACTIVE for all Spring bean definitions within @Configuration classes across infrastructure components, web MVC, security, and cross-cutting concern configurations.

### Rules

- **R-CONFIG-001** MUST: Bean methods within @Configuration classes MUST have explicit return types to ensure type safety.

### Verify

```bash
# Count @Configuration classes in the codebase
grep -r '@Configuration' --include='*.java' | wc -l

# Find configuration classes that lack @Configuration annotation
find . -name '*Config.java' -type f | xargs grep -L '@Configuration' | wc -l

# Find @Bean methods defined outside @Configuration classes
grep -r '@Bean' --include='*.java' | grep -v '@Configuration' | wc -l
```

**Accept when:**
- All configuration classes defining Spring beans are annotated with @Configuration
- No XML-based Spring configuration files (applicationContext.xml) exist in the codebase
- Configuration classes are organized in dedicated config or security packages
- All @Bean methods have explicit return types and are contained within @Configuration classes
- No @Bean methods are found outside @Configuration classes

<enforcement>
Claude Code MUST NOT skip or defer verification. All @Bean method definitions MUST be contained within @Configuration-annotated classes with explicit return types.
</enforcement>
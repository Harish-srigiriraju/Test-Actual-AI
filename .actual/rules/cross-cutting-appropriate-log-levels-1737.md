# Adopt SLF4J with Logback as Standard Logging Framework: Appropriate Log Levels

These rules are ALWAYS ACTIVE for all application service classes, security components, scheduled tasks, background jobs, exception handlers, and integration points with external systems.

### Rules

- **R-LOG-001** SHOULD: Appropriate log levels SHOULD be used: ERROR for failures, WARN for recoverable issues, INFO for significant events, DEBUG for detailed diagnostics, TRACE for fine-grained flow.

### Verify

```bash
# Verify SLF4J Logger imports are present
grep -r "import org.slf4j.Logger" --include="*.java" | wc -l

# Verify no System.out.println or System.err.println in main application code
grep -r "System\.out\.println\|System\.err\.println" --include="*.java" src/main/java/ | grep -v "//" | wc -l

# Verify Logback configuration file exists
find . -name "logback*.xml" -o -name "logback*.groovy" | head -1
```

**Accept when:**
- All production Java classes with logging use SLF4J Logger instances (grep shows consistent import statements)
- No System.out.println or System.err.println calls exist in main application code (grep returns 0 matches excluding comments)
- Logback configuration file exists in the classpath (find command returns at least one configuration file)
- Logger instances follow the pattern: `private static final Logger logger = LoggerFactory.getLogger(ClassName.class)`

<enforcement>
Claude Code MUST NOT skip or defer verification. Automated static analysis tools (Checkstyle, PMD, SonarQube) configured to detect System.out/System.err usage. Code review checklist includes verification of proper SLF4J usage and appropriate log levels. CI/CD pipeline includes dependency verification to ensure SLF4J and Logback are present.
</enforcement>
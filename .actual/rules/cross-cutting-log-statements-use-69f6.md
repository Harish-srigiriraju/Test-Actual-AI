# Adopt SLF4J with Logback as Standard Logging Framework: Log Statements Use

These rules are ALWAYS ACTIVE for all application service classes, controllers, services, repositories, security and authentication components, scheduled tasks, background jobs, exception handlers, and integration points with external systems.

### Rules

- **R-LOG-001** SHOULD: Log statements SHOULD use parameterized messages (e.g., `logger.info("User {} logged in", userId)`) to avoid string concatenation overhead and improve performance when log levels are disabled.

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
- All production Java classes with logging use SLF4J Logger instances following the pattern: `private static final Logger logger = LoggerFactory.getLogger(ClassName.class)`
- No System.out.println or System.err.println calls exist in main application code (grep returns 0 matches excluding comments)
- Logback configuration file exists in the classpath (find command returns at least one configuration file)
- Log statements use parameterized messages rather than string concatenation

<enforcement>
Claude Code MUST NOT skip or defer verification. Automated static analysis tools (Checkstyle, PMD, SonarQube) configured to detect System.out/System.err usage and code review must verify proper SLF4J usage and appropriate log levels.
</enforcement>
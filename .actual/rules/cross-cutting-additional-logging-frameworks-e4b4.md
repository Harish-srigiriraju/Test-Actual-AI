# Adopt SLF4J with Logback as Standard Logging Framework: Additional Logging Frameworks

These rules are ALWAYS ACTIVE for all application service classes, security components, scheduled tasks, background jobs, exception handlers, and integration points with external systems.

### Rules

- **R-SLF4J-001** MAY: Additional logging frameworks MAY be integrated through SLF4J bridges for third-party library compatibility.

### Verify

```bash
# Count SLF4J Logger imports in production code
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
Claude Code MUST NOT skip or defer verification of SLF4J bridge integration compliance.
</enforcement>
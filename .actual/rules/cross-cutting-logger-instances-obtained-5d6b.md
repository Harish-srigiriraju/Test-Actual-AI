# Adopt SLF4J with Logback as Standard Logging Framework: Logger Instances Obtained

These rules are ALWAYS ACTIVE for all application service classes (controllers, services, repositories), security and authentication components, scheduled tasks and background jobs, exception handlers and error processing logic, and integration points with external systems.

### Rules

- **R-SLF4J-001** MUST: Logger instances MUST be obtained using `LoggerFactory.getLogger(ClassName.class)` pattern.
- **R-SLF4J-002** MUST: All production Java classes with logging use SLF4J Logger instances, not System.out.println or System.err.println.
- **R-SLF4J-003** MUST: Logger instances follow the pattern: `private static final Logger logger = LoggerFactory.getLogger(ClassName.class)`.
- **R-SLF4J-004** SHOULD: Use parameterized logging to prevent unnecessary string concatenation when log levels are disabled.
- **R-SLF4J-005** SHOULD: Implement a logging utility class for common patterns like sanitizing sensitive data before logging.

### Verify

```bash
# Verify SLF4J Logger imports are present
grep -r "import org.slf4j.Logger" --include="*.java" | wc -l

# Verify no System.out.println or System.err.println in main application code
grep -r "System\\.out\\.println\|System\\.err\\.println" --include="*.java" src/main/java/ | grep -v "//" | wc -l

# Verify Logback configuration file exists
find . -name "logback*.xml" -o -name "logback*.groovy" | head -1

# Verify logger instance pattern in source files
grep -r "private static final Logger logger = LoggerFactory.getLogger" --include="*.java" src/main/java/ | wc -l
```

**Accept when:**
- All production Java classes with logging use SLF4J Logger instances (grep shows consistent import statements)
- No System.out.println or System.err.println calls exist in main application code (grep returns 0 matches excluding comments)
- Logback configuration file exists in the classpath (find command returns at least one configuration file)
- Logger instances follow the pattern: `private static final Logger logger = LoggerFactory.getLogger(ClassName.class)`
- SLF4J API and Logback dependencies are present in build configuration

<enforcement>
Claude Code MUST NOT skip or defer verification. All rules in this file are mandatory for production application code. Exceptions (legacy code in maintenance-only mode, performance-critical hot paths with documented measurements) require explicit approval and must be tracked in the technical debt register.
</enforcement>
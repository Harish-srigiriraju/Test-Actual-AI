# Adopt SLF4J with Logback as Standard Logging Framework: Logback Used Underlying

These rules are ALWAYS ACTIVE for all application service classes (controllers, services, repositories), security and authentication components, scheduled tasks and background jobs, exception handlers and error processing logic, and integration points with external systems.

### Rules

- **R-LOG-001** SHOULD: Logback SHOULD be used as the underlying logging implementation for SLF4J.
- **R-LOG-002** MUST: All production Java classes with logging use SLF4J Logger instances via `LoggerFactory.getLogger(ClassName.class)`.
- **R-LOG-003** MUST: Logger instances follow the pattern: `private static final Logger logger = LoggerFactory.getLogger(ClassName.class)`.
- **R-LOG-004** MUST: No `System.out.println` or `System.err.println` calls exist in main application code (excluding comments and test utilities).
- **R-LOG-005** MUST: Logback configuration file (`logback.xml` or `logback-spring.xml`) exists in the classpath.
- **R-LOG-006** SHOULD: Use parameterized logging to prevent unnecessary string concatenation when log levels are disabled.
- **R-LOG-007** SHOULD: Implement a logging utility class for common patterns like sanitizing sensitive data before logging.
- **R-LOG-008** SHOULD: Configure separate log files or appenders for different concerns (application logs, security audit logs, performance metrics).
- **R-LOG-009** SHOULD: Set up log rotation policies to prevent disk space exhaustion in production environments.

### Verify

```bash
# Count SLF4J Logger imports in production code
grep -r "import org.slf4j.Logger" --include="*.java" src/main/java/ | wc -l

# Check for System.out/System.err usage in main application code (excluding comments)
grep -r "System\.out\.println\|System\.err\.println" --include="*.java" src/main/java/ | grep -v "//" | wc -l

# Verify Logback configuration file exists
find . -name "logback*.xml" -o -name "logback*.groovy" | head -1

# Verify SLF4J and Logback dependencies are present
grep -E "slf4j|logback" pom.xml build.gradle 2>/dev/null | head -5
```

**Accept when:**
- All production Java classes with logging use SLF4J Logger instances (grep shows consistent import statements across application code)
- No `System.out.println` or `System.err.println` calls exist in main application code (grep returns 0 matches excluding comments)
- Logback configuration file exists in the classpath (find command returns at least one configuration file)
- Logger instances follow the pattern: `private static final Logger logger = LoggerFactory.getLogger(ClassName.class)`
- SLF4J and Logback dependencies are declared in build configuration (Maven pom.xml or Gradle build.gradle)

<enforcement>
Claude Code MUST NOT skip or defer verification. All rules in this file are mandatory for production application code. Exceptions (EXC-001 for legacy maintenance-only code, EXC-002 for performance-critical hot paths) require documented justification and Tech Lead approval.
</enforcement>
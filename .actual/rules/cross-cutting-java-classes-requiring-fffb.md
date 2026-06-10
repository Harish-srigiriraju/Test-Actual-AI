# Adopt SLF4J with Logback as Standard Logging Framework: Java Classes Requiring

These rules are ALWAYS ACTIVE for all Java classes in application service layers (controllers, services, repositories), security and authentication components, scheduled tasks, exception handlers, and integration points with external systems.

### Rules

- **R-SLF4J-001** MUST: All Java classes requiring logging capabilities MUST use SLF4J (Simple Logging Facade for Java) as the logging API.
- **R-SLF4J-002** MUST: Logger instances MUST follow the pattern: `private static final Logger logger = LoggerFactory.getLogger(ClassName.class)`.
- **R-SLF4J-003** MUST: No `System.out.println()` or `System.err.println()` calls are permitted in main application code (controllers, services, repositories, security components).
- **R-SLF4J-004** SHOULD: Use parameterized logging to avoid unnecessary string concatenation when log levels are disabled (e.g., `logger.debug("User {} logged in", userId)` instead of `logger.debug("User " + userId + " logged in")`).
- **R-SLF4J-005** SHOULD: Ensure appropriate log levels are used: DEBUG for detailed diagnostic information, INFO for general informational messages, WARN for potentially harmful situations, ERROR for error events.
- **R-SLF4J-006** MUST: A Logback configuration file (logback.xml or logback-spring.xml) MUST exist in the classpath.

### Verify

```bash
# Count SLF4J Logger imports in production code
grep -r "import org.slf4j.Logger" --include="*.java" src/main/java/ | wc -l

# Count System.out/System.err calls in production code (excluding comments)
grep -r "System\.out\.println\|System\.err\.println" --include="*.java" src/main/java/ | grep -v "//" | wc -l

# Verify Logback configuration file exists
find . -name "logback*.xml" -o -name "logback*.groovy" | head -1

# Verify SLF4J and Logback dependencies are present
grep -E "slf4j|logback" pom.xml build.gradle 2>/dev/null | head -5
```

**Accept when:**
- All production Java classes with logging use SLF4J Logger instances (grep shows consistent import statements across service layers)
- No `System.out.println()` or `System.err.println()` calls exist in main application code (grep returns 0 matches excluding comments)
- Logback configuration file exists in the classpath (find command returns at least one configuration file)
- Logger instances follow the pattern: `private static final Logger logger = LoggerFactory.getLogger(ClassName.class)`
- SLF4J and Logback dependencies are declared in build configuration (Maven pom.xml or Gradle build.gradle)

<enforcement>
Claude Code MUST NOT skip or defer verification. All rules R-SLF4J-001 through R-SLF4J-006 must be verified before accepting changes to Java classes in scope. Violations in production code must be flagged for remediation or exception request.
</enforcement>
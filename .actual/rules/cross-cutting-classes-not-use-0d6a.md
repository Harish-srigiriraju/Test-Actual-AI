# Adopt SLF4J with Logback as Standard Logging Framework: Classes Not Use

These rules are ALWAYS ACTIVE for all application service classes, security components, scheduled tasks, exception handlers, and integration points that perform logging.

### Rules

- **R-SLF4J-001** MUST_NOT: Classes MUST NOT use System.out.println() or System.err.println() for application logging.
- **R-SLF4J-002** MUST: All production Java classes with logging MUST use SLF4J Logger instances via LoggerFactory.getLogger().
- **R-SLF4J-003** MUST: Logger instances MUST follow the pattern: `private static final Logger logger = LoggerFactory.getLogger(ClassName.class)`.
- **R-SLF4J-004** SHOULD: Use parameterized logging to avoid unnecessary string concatenation when log levels are disabled.
- **R-SLF4J-005** SHOULD: Implement appropriate log levels (DEBUG, INFO, WARN, ERROR) based on diagnostic value and frequency.

### Verify

```bash
# Count SLF4J Logger imports in production code
grep -r "import org.slf4j.Logger" --include="*.java" src/main/java | wc -l

# Detect System.out/System.err usage in main application code (excluding comments)
grep -r "System\.out\.println\|System\.err\.println" --include="*.java" src/main/java/ | grep -v "//" | wc -l

# Verify Logback configuration file exists
find . -name "logback*.xml" -o -name "logback*.groovy" | head -1

# Verify SLF4J and Logback dependencies are present
grep -E "slf4j|logback" pom.xml build.gradle 2>/dev/null | head -5
```

**Accept when:**
- All production Java classes with logging use SLF4J Logger instances (grep shows consistent import statements)
- No System.out.println or System.err.println calls exist in main application code (grep returns 0 matches excluding comments)
- Logback configuration file exists in the classpath (find command returns at least one configuration file)
- Logger instances follow the pattern: `private static final Logger logger = LoggerFactory.getLogger(ClassName.class)`
- SLF4J API and Logback dependencies are declared in build configuration

<enforcement>
Claude Code MUST NOT skip or defer verification. All rules in this file are mandatory for production application code. Violations must be caught during code review and CI/CD pipeline checks.
</enforcement>
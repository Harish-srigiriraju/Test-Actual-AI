# Adopt SLF4J with Logback as Standard Logging Framework: Logger Instances Declared

These rules are ALWAYS ACTIVE for all application service classes including controllers, services, repositories, security and authentication components, scheduled tasks, exception handlers, and integration points with external systems.

### Rules

- **R-LOG-001** MUST: Logger instances MUST be declared as private static final fields with the naming convention 'logger' or 'log'.
- **R-LOG-002** MUST: All production Java classes with logging MUST use SLF4J Logger instances via LoggerFactory.getLogger().
- **R-LOG-003** MUST: No System.out.println or System.err.println calls SHALL exist in main application code (excluding comments and test utilities).
- **R-LOG-004** MUST: Logger instances MUST follow the pattern: `private static final Logger logger = LoggerFactory.getLogger(ClassName.class)`.
- **R-LOG-005** SHOULD: Use parameterized logging to prevent unnecessary string concatenation when log levels are disabled.
- **R-LOG-006** SHOULD: Implement appropriate log levels (DEBUG, INFO, WARN, ERROR) based on diagnostic value and production noise considerations.

### Verify

```bash
# Verify SLF4J Logger imports are present
grep -r "import org.slf4j.Logger" --include="*.java" | wc -l

# Verify no System.out/System.err in main application code
grep -r "System\.out\.println\|System\.err\.println" --include="*.java" src/main/java/ | grep -v "//" | wc -l

# Verify Logback configuration file exists
find . -name "logback*.xml" -o -name "logback*.groovy" | head -1

# Verify logger declaration pattern
grep -r "private static final Logger" --include="*.java" src/main/java/ | wc -l
```

**Accept when:**
- All production Java classes with logging use SLF4J Logger instances (grep shows consistent import statements)
- No System.out.println or System.err.println calls exist in main application code (grep returns 0 matches excluding comments)
- Logback configuration file exists in the classpath (find command returns at least one configuration file)
- Logger instances follow the pattern: `private static final Logger logger = LoggerFactory.getLogger(ClassName.class)`
- SLF4J API and Logback dependencies are present in build configuration

<enforcement>
Claude Code MUST NOT skip or defer verification. Violations in main application code MUST generate CI build warnings. Code review MUST reject new code not following SLF4J patterns. Existing violations MUST be tracked as technical debt with prioritization based on module criticality. Exceptions require Tech Lead evaluation and documentation in technical debt register.
</enforcement>
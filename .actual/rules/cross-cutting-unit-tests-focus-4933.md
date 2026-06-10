# Adopt JUnit and Mockito as Standard Testing Framework Stack: Unit Tests Focus

These rules are ALWAYS ACTIVE for all Java test code in the `test/` directory, including unit tests for service layer components, integration tests for multi-component workflows, validation layer tests, UI layer tests, and factory and utility class tests.

### Rules

- **R-JUNIT-001** SHOULD: Unit tests SHOULD focus on testing a single class or component in isolation.
- **R-JUNIT-002** MUST: All unit tests MUST follow the naming convention `*Test.java` for unit tests and `*IT.java` for integration tests.
- **R-JUNIT-003** MUST: All test files MUST import and use only `org.junit` packages for test framework functionality.
- **R-JUNIT-004** MUST: Mock objects MUST be created using `org.mockito` framework imports.
- **R-JUNIT-005** SHOULD: Test methods SHOULD use clear naming conventions that describe the scenario being tested (e.g., `shouldReturnUserWhenValidIdProvided`).
- **R-JUNIT-006** SHOULD: Common Mockito patterns SHOULD follow established conventions: `when/thenReturn` for stubbing, `verify` for behavior verification, `@Mock` and `@InjectMocks` annotations for setup.

### Verify

```bash
# Count JUnit imports in test directory
grep -r "import org.junit" test/java/ | wc -l

# Count Mockito imports in test directory
grep -r "import org.mockito" test/java/ | wc -l

# Find all test files following naming convention
find test/java -name '*IT.java' -o -name '*Test.java' | wc -l

# Execute unit tests and integration tests with separate phases
mvn test -Dtest=**/*Test.java && mvn verify -Dit.test=**/*IT.java
```

**Accept when:**
- All test files in `test/java` directory import `org.junit` packages
- Test files follow naming convention: unit tests end with `Test.java` and integration tests end with `IT.java`
- Mock objects are created using `org.mockito` framework imports
- Maven/Gradle build successfully executes both unit tests and integration tests with separate phases
- No alternative testing frameworks (TestNG, Spock, etc.) are detected in test imports

<enforcement>
Claude Code MUST NOT skip or defer verification. Automated CI pipeline checks MUST verify JUnit and Mockito are the only testing frameworks in use. Code review checklists MUST include verification of test naming conventions and framework usage. Static analysis tools (e.g., ArchUnit) MUST validate test package structure and naming patterns. Dependency analysis in build pipeline MUST flag introduction of alternative testing frameworks. CI build MUST fail if tests are found that do not follow naming conventions.
</enforcement>
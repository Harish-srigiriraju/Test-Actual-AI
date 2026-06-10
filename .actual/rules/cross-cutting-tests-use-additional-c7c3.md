# Adopt JUnit and Mockito as Standard Testing Framework Stack: Tests Use Additional

These rules are ALWAYS ACTIVE for all Java test code in the `test/` directory, including unit tests, integration tests, validation layer tests, UI layer tests, and factory/utility class tests.

### Rules

- **R-JUNIT-001** MUST: All Java unit tests use JUnit as the primary testing framework.
- **R-JUNIT-002** MUST: All Java unit tests use Mockito for creating mocks and stubs.
- **R-JUNIT-003** MUST: Unit test files follow the naming convention `*Test.java`.
- **R-JUNIT-004** MUST: Integration test files follow the naming convention `*IT.java`.
- **R-JUNIT-005** MAY: UI tests MAY use additional frameworks (e.g., Selenium) in conjunction with JUnit for browser automation.
- **R-JUNIT-006** SHOULD: Test methods should clearly describe the scenario being tested (e.g., `shouldReturnUserWhenValidIdProvided`).
- **R-JUNIT-007** SHOULD: Maintain a balanced test pyramid with appropriate mix of unit tests (with mocks) and integration tests (with real dependencies).

### Verify

```bash
# Count JUnit imports in test files
grep -r "import org.junit" test/java/ | wc -l

# Count Mockito imports in test files
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
Claude Code MUST NOT skip or defer verification. Automated CI pipeline checks MUST verify JUnit and Mockito are the only testing frameworks in use. Code review checklist MUST include verification of test naming conventions and framework usage. Static analysis tools MUST validate test package structure and naming patterns. Dependency analysis in build pipeline MUST flag introduction of alternative testing frameworks.
</enforcement>
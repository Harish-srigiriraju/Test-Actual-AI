# Adopt JUnit and Mockito as Standard Testing Framework Stack: Test Classes Mirror

These rules are ALWAYS ACTIVE for all Java test code in the `test/` directory, including unit tests, integration tests, validation layer tests, UI layer tests, and factory/utility class tests.

### Rules

- **R-TEST-001** SHOULD: Test classes SHOULD mirror the package structure of the production code they test.
- **R-TEST-002** MUST: Unit test files MUST follow the naming convention `*Test.java`.
- **R-TEST-003** MUST: Integration test files MUST follow the naming convention `*IT.java`.
- **R-TEST-004** MUST: All test files MUST use JUnit as the testing framework.
- **R-TEST-005** MUST: All test files MUST use Mockito for mocking and stubbing.
- **R-TEST-006** SHOULD: Test method names SHOULD clearly describe the scenario being tested (e.g., `shouldReturnUserWhenValidIdProvided`).

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
- Test package structure mirrors production code package structure

<enforcement>
Claude Code MUST NOT skip or defer verification. All rules in this file are mandatory for Java test code within scope. Violations MUST be flagged during code review and CI pipeline checks MUST fail if naming conventions or framework usage do not comply.
</enforcement>
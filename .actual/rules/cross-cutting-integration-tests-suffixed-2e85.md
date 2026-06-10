# Adopt JUnit and Mockito as Standard Testing Framework Stack: Integration Tests Suffixed

These rules are ALWAYS ACTIVE for all Java test code in the `test/` directory, including unit tests, integration tests, validation layer tests, UI layer tests, and factory/utility class tests.

### Rules

- **R-JUNIT-001** MUST: Integration tests MUST be suffixed with 'IT' (e.g., UserServiceIT.java) to distinguish them from unit tests.
- **R-JUNIT-002** MUST: Unit tests MUST be suffixed with 'Test' (e.g., UserServiceTest.java).
- **R-JUNIT-003** MUST: All test files in the test/java directory MUST import org.junit packages.
- **R-JUNIT-004** MUST: Mock objects MUST be created using org.mockito framework imports.
- **R-JUNIT-005** MUST: Test methods SHOULD clearly describe the scenario being tested (e.g., shouldReturnUserWhenValidIdProvided).

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

# Verify no alternative testing frameworks are present
grep -r "import org.testng" test/java/ | wc -l
grep -r "import spock" test/java/ | wc -l
```

**Accept when:**
- All test files in test/java directory import org.junit packages
- Test files follow naming convention: unit tests end with 'Test.java' and integration tests end with 'IT.java'
- Mock objects are created using org.mockito framework imports
- Maven/Gradle build successfully executes both unit tests and integration tests with separate phases
- No alternative testing frameworks (TestNG, Spock) are detected in test imports
- Test method names clearly describe the scenario being tested

<enforcement>
Claude Code MUST NOT skip or defer verification. Automated CI pipeline checks MUST verify JUnit and Mockito are the only testing frameworks in use. Code review checklists MUST include verification of test naming conventions and framework usage. Static analysis tools MUST validate test package structure and naming patterns. Dependency analysis in build pipeline MUST flag introduction of alternative testing frameworks. CI build MUST fail if tests are found that do not follow naming conventions (*Test.java or *IT.java).
</enforcement>
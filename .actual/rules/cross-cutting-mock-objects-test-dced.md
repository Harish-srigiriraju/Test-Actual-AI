# Adopt JUnit and Mockito as Standard Testing Framework Stack: Mock Objects Test

These rules are ALWAYS ACTIVE for all Java test code in the test/ directory, including unit tests for service layer components, integration tests for multi-component workflows, validation layer tests, UI layer tests, and factory and utility class tests.

### Rules

- **R-MOCK-001** MUST: Mock objects and test doubles MUST be created using Mockito framework for dependency isolation in all Java test code.

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
- All test files in test/java directory import org.junit packages
- Test files follow naming convention: unit tests end with 'Test.java' and integration tests end with 'IT.java'
- Mock objects are created using org.mockito framework imports
- Maven/Gradle build successfully executes both unit tests and integration tests with separate phases
- No alternative testing frameworks (TestNG, Spock, etc.) are detected in test imports

<enforcement>
Claude Code MUST NOT skip or defer verification of R-MOCK-001. Automated CI pipeline checks MUST verify JUnit and Mockito are the only testing frameworks in use. Code review checklists MUST include verification of test naming conventions and framework usage. Pull requests introducing alternative testing frameworks MUST be automatically flagged for architecture review.
</enforcement>
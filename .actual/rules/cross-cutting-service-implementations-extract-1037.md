# Adopt JWT-Based Token Authentication for Service Layer Security: Service Implementations Extract

These rules are ALWAYS ACTIVE for all service layer implementations handling user-specific data, REST API endpoints requiring authentication, background jobs operating on behalf of specific users, and inter-service communication requiring authentication.

### Rules

- **R-JWT-001** SHOULD: Service implementations SHOULD extract user identity from JWT tokens rather than accepting user identifiers as direct parameters for authenticated operations.

### Verify

```bash
# Count JwtTokenService references in non-test files
grep -r 'JwtTokenService' --include='*.java' | grep -v 'test' | wc -l

# Count @Autowired JwtTokenService in service implementations
grep -r '@Autowired.*JwtTokenService' --include='*ServiceImpl.java' | wc -l

# Find service implementations without JWT imports (should be 0 for User/Appointment/Invoice/Work services)
find . -name '*ServiceImpl.java' -exec grep -L 'import.*jwt' {} \; | grep -E '(User|Appointment|Invoice|Work)ServiceImpl' | wc -l
```

**Accept when:**
- All service implementations requiring authentication reference JwtTokenService (grep count > 0 for each service)
- No service implementations contain direct JWT parsing logic outside JwtTokenService (custom JWT parsing count = 0)
- Integration tests demonstrate successful token validation across all authenticated service endpoints
- Security review confirms JWT implementation follows OWASP guidelines for token-based authentication

<enforcement>
Claude Code MUST NOT skip or defer verification. Automated code review checks for direct JWT library usage outside JwtTokenService are mandatory. Integration tests validating authentication flow across all service implementations are required. Security scanning tools checking for JWT vulnerabilities and misconfigurations must pass. Pull request reviews require security team approval for authentication-related changes.
</enforcement>
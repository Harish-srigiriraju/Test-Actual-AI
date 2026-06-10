# Adopt JWT-Based Token Authentication for Service Layer Security: Service Implementations Not

These rules are ALWAYS ACTIVE for all service layer implementations handling user-specific data, REST API endpoints requiring authentication, background jobs operating on behalf of specific users, and inter-service communication requiring authentication.

### Rules

- **R-JWT-001** MUST_NOT: Service implementations MUST NOT implement custom token generation or validation logic outside the centralized JwtTokenService.

### Verify

```bash
# Count JwtTokenService references in non-test code
grep -r 'JwtTokenService' --include='*.java' | grep -v 'test' | wc -l

# Count @Autowired JwtTokenService injections in service implementations
grep -r '@Autowired.*JwtTokenService' --include='*ServiceImpl.java' | wc -l

# Find service implementations without JWT imports (should be zero for authenticated services)
find . -name '*ServiceImpl.java' -exec grep -L 'import.*jwt' {} \; | grep -E '(User|Appointment|Invoice|Work)ServiceImpl' | wc -l
```

**Accept when:**
- All service implementations requiring authentication reference JwtTokenService (grep count > 0 for each service)
- No service implementations contain direct JWT parsing logic outside JwtTokenService (custom JWT parsing count = 0)
- Integration tests demonstrate successful token validation across all authenticated service endpoints
- Security review confirms JWT implementation follows OWASP guidelines for token-based authentication

<enforcement>
Claude Code MUST NOT skip or defer verification. Automated code review checks for direct JWT library usage outside JwtTokenService are mandatory. Pull request reviews require security team approval for authentication-related changes. CI pipeline must fail if service implementations contain direct JWT parsing outside JwtTokenService.
</enforcement>
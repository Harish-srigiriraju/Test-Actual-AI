# Adopt JWT-Based Token Authentication for Service Layer Security: Jwt Tokens Contain

These rules are ALWAYS ACTIVE for all service layer implementations handling user-specific data, REST API endpoints requiring authentication, background jobs operating on behalf of specific users, and inter-service communication requiring authentication.

### Rules

- **R-JWT-001** MUST: JWT tokens MUST contain sufficient claims to identify the authenticated user and their authorization scope.

### Verify

```bash
# Verify JwtTokenService is referenced across service implementations
grep -r 'JwtTokenService' --include='*.java' | grep -v 'test' | wc -l

# Verify JwtTokenService is autowired in service implementations
grep -r '@Autowired.*JwtTokenService' --include='*ServiceImpl.java' | wc -l

# Verify no service implementations contain direct JWT parsing outside JwtTokenService
find . -name '*ServiceImpl.java' -exec grep -L 'import.*jwt' {} \; | grep -E '(User|Appointment|Invoice|Work)ServiceImpl' | wc -l
```

**Accept when:**
- All service implementations requiring authentication reference JwtTokenService (grep count > 0 for each service)
- No service implementations contain direct JWT parsing logic outside JwtTokenService (custom JWT parsing count = 0)
- Integration tests demonstrate successful token validation across all authenticated service endpoints
- Security review confirms JWT implementation follows OWASP guidelines for token-based authentication

<enforcement>
Claude Code MUST NOT skip or defer verification. Automated code review checks, integration tests, security scanning tools, and pull request reviews are mandatory before accepting changes to authentication implementations.
</enforcement>
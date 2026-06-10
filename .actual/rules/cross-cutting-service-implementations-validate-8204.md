# Adopt JWT-Based Token Authentication for Service Layer Security: Service Implementations Validate

These rules are ALWAYS ACTIVE for all service layer implementations handling user-specific data, REST API endpoints requiring authentication, background jobs operating on behalf of specific users, and inter-service communication requiring authentication.

### Rules

- **R-JWT-001** MUST: Service implementations MUST validate JWT tokens before processing authenticated requests.

### Verify

```bash
# Count JwtTokenService references in non-test code
grep -r 'JwtTokenService' --include='*.java' | grep -v 'test' | wc -l

# Count @Autowired JwtTokenService injections in service implementations
grep -r '@Autowired.*JwtTokenService' --include='*ServiceImpl.java' | wc -l

# Find service implementations NOT importing JWT libraries (should be 0 for User/Appointment/Invoice/Work services)
find . -name '*ServiceImpl.java' -exec grep -L 'import.*jwt' {} \; | grep -E '(User|Appointment|Invoice|Work)ServiceImpl' | wc -l
```

**Accept when:**
- All service implementations requiring authentication reference JwtTokenService (grep count > 0 for each service)
- No service implementations contain direct JWT parsing logic outside JwtTokenService (custom JWT parsing count = 0)
- Integration tests demonstrate successful token validation across all authenticated service endpoints
- Security review confirms JWT implementation follows OWASP guidelines for token-based authentication

<enforcement>
Claude Code MUST NOT skip or defer verification. All service implementations handling authenticated requests MUST validate JWT tokens through the centralized JwtTokenService. Direct JWT parsing outside the token service is a violation.
</enforcement>
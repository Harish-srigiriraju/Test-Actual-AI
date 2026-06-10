# Adopt JWT-Based Token Authentication for Service Layer Security: Jwt Tokens Have

These rules are ALWAYS ACTIVE for all service layer implementations requiring authentication and authorization controls in the appointment scheduler system, including UserServiceImpl, AppointmentServiceImpl, InvoiceServiceImpl, WorkServiceImpl, and WorkingPlanServiceImpl.

### Rules

- **R-JWT-001** SHOULD: JWT tokens SHOULD have appropriate expiration times to balance security and user experience (recommended: 15-60 minutes for access tokens, 7-30 days for refresh tokens).
- **R-JWT-002** MUST: All service implementations requiring authentication MUST use centralized JwtTokenServiceImpl for token management rather than direct JWT parsing.
- **R-JWT-003** MUST: JWT signing keys MUST be stored in secure key management systems (e.g., HashiCorp Vault, AWS KMS) and never hardcoded or committed to version control.
- **R-JWT-004** SHOULD: JWT tokens SHOULD include minimal necessary claims (user ID, roles, expiration) to keep token size manageable and avoid exposing sensitive information.
- **R-JWT-005** MUST: All service implementations MUST use industry-standard JWT libraries (e.g., jjwt, nimbus-jose-jwt) rather than custom implementations.
- **R-JWT-006** SHOULD: Asymmetric signing (RS256) SHOULD be preferred over symmetric signing (HS256) where possible to limit key distribution.
- **R-JWT-007** MUST: Authentication failures and token validation errors MUST be comprehensively logged for security monitoring and debugging.
- **R-JWT-008** MUST: Token validation MUST occur at the framework level (e.g., Spring Security) before reaching service implementations.

### Verify

```bash
# Count JwtTokenService references in non-test code
grep -r 'JwtTokenService' --include='*.java' | grep -v 'test' | wc -l

# Count @Autowired JwtTokenService injections in service implementations
grep -r '@Autowired.*JwtTokenService' --include='*ServiceImpl.java' | wc -l

# Find service implementations that do NOT import JWT libraries (should be zero for authenticated services)
find . -name '*ServiceImpl.java' -exec grep -L 'import.*jwt' {} \; | grep -E '(User|Appointment|Invoice|Work)ServiceImpl' | wc -l
```

**Accept when:**
- All service implementations requiring authentication reference JwtTokenService (grep count > 0 for each service)
- No service implementations contain direct JWT parsing logic outside JwtTokenService (custom JWT parsing count = 0)
- Integration tests demonstrate successful token validation across all authenticated service endpoints
- Security review confirms JWT implementation follows OWASP guidelines for token-based authentication
- JWT signing keys are stored in secure key management systems and not present in codebase
- Token expiration times are configured within recommended ranges (15-60 minutes for access tokens)

<enforcement>
Claude Code MUST NOT skip or defer verification. All rules in this file are mandatory for service layer authentication implementations. Violations discovered during code review or automated scanning MUST be remediated before merge. Security team approval is required for all authentication-related changes.
</enforcement>
# Adopt JWT-Based Token Authentication for Service Layer Security: Jwt Token Generation

These rules are ALWAYS ACTIVE for all service layer implementations requiring authentication and authorization controls in the appointment scheduler system, including UserServiceImpl, AppointmentServiceImpl, InvoiceServiceImpl, WorkServiceImpl, and WorkingPlanServiceImpl.

### Rules

- **R-JWT-001** MUST: JWT token generation, validation, and parsing MUST be centralized through a dedicated JwtTokenService implementation.
- **R-JWT-002** MUST: All service layer implementations handling user-specific data MUST reference JwtTokenService for token operations rather than implementing direct JWT parsing logic.
- **R-JWT-003** MUST: REST API endpoints requiring authentication MUST validate tokens through the centralized JwtTokenService.
- **R-JWT-004** MUST: JWT signing keys MUST be stored in a secure key management system (e.g., HashiCorp Vault, AWS KMS) rather than hardcoded or in configuration files.
- **R-JWT-005** SHOULD: JWT tokens SHOULD use industry-standard libraries (e.g., jjwt, nimbus-jose-jwt) rather than custom implementations.
- **R-JWT-006** SHOULD: Access token expiration times SHOULD be configured between 15-60 minutes; refresh tokens SHOULD be configured between 7-30 days.
- **R-JWT-007** SHOULD: JWT tokens SHOULD include minimal necessary claims (user ID, roles, expiration) to keep token size manageable and avoid exposing sensitive information.
- **R-JWT-008** SHOULD: Asymmetric signing (RS256) SHOULD be preferred over symmetric signing (HS256) where possible to limit key distribution.
- **R-JWT-009** SHOULD: Comprehensive logging of authentication failures and token validation errors SHOULD be implemented for security monitoring and debugging.
- **R-JWT-010** MAY: Legacy endpoints during migration period MAY temporarily support both JWT and session-based authentication (EXC-001).
- **R-JWT-011** MAY: Administrative or emergency operations MAY bypass standard authentication for system recovery with documented justification (EXC-002).

### Verify

```bash
# Count JwtTokenService references in non-test files
grep -r 'JwtTokenService' --include='*.java' | grep -v 'test' | wc -l

# Count @Autowired JwtTokenService injections in service implementations
grep -r '@Autowired.*JwtTokenService' --include='*ServiceImpl.java' | wc -l

# Find service implementations NOT importing JWT libraries (should be 0 for auth services)
find . -name '*ServiceImpl.java' -exec grep -L 'import.*jwt' {} \; | grep -E '(User|Appointment|Invoice|Work)ServiceImpl' | wc -l
```

**Accept when:**
- All service implementations requiring authentication reference JwtTokenService (grep count > 0 for each service)
- No service implementations contain direct JWT parsing logic outside JwtTokenService (custom JWT parsing count = 0)
- Integration tests demonstrate successful token validation across all authenticated service endpoints
- Security review confirms JWT implementation follows OWASP guidelines for token-based authentication
- JWT signing keys are stored in secure key management system, not in code or configuration files
- Token expiration times are configured within recommended ranges (15-60 minutes for access tokens)

<enforcement>
Claude Code MUST NOT skip or defer verification. All R-JWT rules marked MUST are non-negotiable. Violations discovered in code review or automated scanning MUST trigger immediate remediation. Pull requests affecting authentication MUST include security team approval before merge.
</enforcement>
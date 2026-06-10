# Adopt JWT-Based Token Authentication for Service Layer Security: Service Layer Implementations

These rules are ALWAYS ACTIVE for all service layer implementations requiring authentication and authorization controls in the appointment scheduler system, including UserServiceImpl, AppointmentServiceImpl, InvoiceServiceImpl, WorkServiceImpl, and WorkingPlanServiceImpl.

### Rules

- **R-JWT-001** MUST: All service layer implementations requiring authentication MUST use JWT tokens as the primary authentication mechanism through the centralized JwtTokenServiceImpl.
- **R-JWT-002** MUST: Service implementations MUST NOT contain direct JWT parsing logic outside of JwtTokenServiceImpl; all token validation MUST be delegated to the centralized token service.
- **R-JWT-003** MUST: JWT tokens MUST be validated at the framework level (e.g., Spring Security) before reaching service implementations.
- **R-JWT-004** SHOULD: JWT tokens SHOULD include minimal necessary claims (user ID, roles, expiration) to keep token size manageable and avoid exposing sensitive information.
- **R-JWT-005** SHOULD: Token expiration times SHOULD be configured based on security requirements (recommended: 15-60 minutes for access tokens, 7-30 days for refresh tokens).
- **R-JWT-006** SHOULD: JWT signing keys SHOULD be stored in secure key management systems (e.g., HashiCorp Vault, AWS KMS) with key rotation procedures implemented.
- **R-JWT-007** SHOULD: Asymmetric signing (RS256) SHOULD be preferred over symmetric signing (HS256) where possible to limit key distribution.
- **R-JWT-008** MUST: JwtTokenServiceImpl MUST use industry-standard JWT libraries (e.g., jjwt, nimbus-jose-jwt) rather than custom implementations.
- **R-JWT-009** SHOULD: Comprehensive logging of authentication failures and token validation errors SHOULD be implemented for security monitoring and debugging.
- **R-JWT-010** MAY: Legacy endpoints during migration period MAY temporarily support both JWT and session-based authentication (EXC-001).
- **R-JWT-011** MAY: Administrative or emergency operations MAY bypass standard authentication for system recovery (EXC-002).

### Verify

```bash
# Count JwtTokenService references in non-test service implementations
grep -r 'JwtTokenService' --include='*.java' | grep -v 'test' | wc -l

# Count @Autowired JwtTokenService injections in service implementations
grep -r '@Autowired.*JwtTokenService' --include='*ServiceImpl.java' | wc -l

# Find service implementations without JWT imports (should be 0 for auth-required services)
find . -name '*ServiceImpl.java' -exec grep -L 'import.*jwt' {} \; | grep -E '(User|Appointment|Invoice|Work)ServiceImpl' | wc -l

# Detect direct JWT parsing outside JwtTokenService
grep -r 'Jwts\.' --include='*.java' | grep -v 'JwtTokenService' | grep -v 'test' | wc -l
```

**Accept when:**
- All service implementations requiring authentication reference JwtTokenService (grep count > 0 for each service)
- No service implementations contain direct JWT parsing logic outside JwtTokenService (custom JWT parsing count = 0)
- Integration tests demonstrate successful token validation across all authenticated service endpoints
- Security review confirms JWT implementation follows OWASP guidelines for token-based authentication
- JwtTokenServiceImpl uses only industry-standard JWT libraries
- Token expiration times are configured within recommended ranges
- JWT signing keys are stored in secure key management systems

<enforcement>
Claude Code MUST NOT skip or defer verification. All rules in this file are mandatory for service layer implementations requiring authentication. Violations discovered during code review or automated scanning MUST trigger immediate remediation or exception process review.
</enforcement>
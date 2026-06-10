# Adopt Spring MVC Controller Pattern for Web Request Handling: Web Request Handlers

These rules are ALWAYS ACTIVE for all web request handler classes in the application's controller layer, including HTTP endpoint handlers, RESTful API controllers, AJAX handlers, and form submission processors.

### Rules

- **R-WEB-001** MUST: All web request handlers MUST be implemented as Spring MVC @Controller or @RestController annotated classes.
- **R-WEB-002** MUST: All controller classes MUST be placed in the com.petreach.appointmentscheduler.controller package to maintain consistent organization.
- **R-WEB-003** MUST: All public methods handling HTTP requests MUST include Spring MVC request mapping annotations (@RequestMapping, @GetMapping, @PostMapping, @PutMapping, @DeleteMapping, or equivalent).
- **R-WEB-004** SHOULD: Use constructor-based dependency injection for required service dependencies to improve testability and make dependencies explicit.
- **R-WEB-005** SHOULD: Follow RESTful naming conventions for URL mappings (e.g., /appointments, /customers/{id}) and use appropriate HTTP methods (GET, POST, PUT, DELETE).
- **R-WEB-006** SHOULD: Implement @ControllerAdvice classes for cross-cutting concerns like exception handling, model attributes, and data binding configuration.
- **R-WEB-007** SHOULD: Use @Valid annotation with BindingResult for form validation and return appropriate error responses or views.
- **R-WEB-008** MUST NOT: Place business logic in controller classes; business logic MUST belong in the service layer.
- **R-WEB-009** MUST NOT: Place data access operations in controller classes; data access MUST belong in the repository/DAO layer.

### Verify

```bash
# Count controller classes with Spring MVC annotations
grep -r '@Controller\|@RestController' java/com/petreach/appointmentscheduler/controller/ | wc -l

# Find controller files without Spring MVC annotations
find java/com/petreach/appointmentscheduler/controller/ -name '*Controller.java' -exec grep -L '@Controller\|@RestController' {} \;

# Count request mapping annotations across controllers
grep -r '@RequestMapping\|@GetMapping\|@PostMapping\|@PutMapping\|@DeleteMapping' java/com/petreach/appointmentscheduler/controller/ | wc -l
```

**Accept when:**
- All classes in the controller package are annotated with @Controller or @RestController
- No controller files exist without Spring MVC annotations (@RequestMapping, @GetMapping, etc.)
- Controller classes contain request mapping annotations for all public methods handling HTTP requests
- All controller classes are located in the com.petreach.appointmentscheduler.controller package
- No business logic or data access code is present in controller classes

<enforcement>
Claude Code MUST NOT skip or defer verification of these rules. All controller implementations MUST comply with R-WEB-001 through R-WEB-009 before code review approval.
</enforcement>
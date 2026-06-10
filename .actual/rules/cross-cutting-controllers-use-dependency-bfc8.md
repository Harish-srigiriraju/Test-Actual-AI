# Adopt Spring MVC Controller Pattern for Web Request Handling: Controllers Use Dependency

These rules are ALWAYS ACTIVE for all HTTP request handling in the web application layer, including RESTful API endpoints, traditional MVC view rendering, AJAX request handlers, form submissions, and request routing configuration.

### Rules

- **R-CTRL-001** SHOULD: Controllers SHOULD use dependency injection for service dependencies rather than manual instantiation.
- **R-CTRL-002** MUST: All controller classes MUST be annotated with @Controller or @RestController.
- **R-CTRL-003** MUST: All public methods handling HTTP requests MUST use Spring MVC request mapping annotations (@RequestMapping, @GetMapping, @PostMapping, @PutMapping, @DeleteMapping).
- **R-CTRL-004** SHOULD: Controllers SHOULD use constructor-based dependency injection for required service dependencies to improve testability and make dependencies explicit.
- **R-CTRL-005** SHOULD: Controllers SHOULD follow RESTful naming conventions for URL mappings (e.g., /appointments, /customers/{id}) and use appropriate HTTP methods (GET, POST, PUT, DELETE).
- **R-CTRL-006** SHOULD: Controllers SHOULD implement @ControllerAdvice classes for cross-cutting concerns like exception handling, model attributes, and data binding configuration.
- **R-CTRL-007** SHOULD: Controllers SHOULD use @Valid annotation with BindingResult for form validation and return appropriate error responses or views.
- **R-CTRL-008** MUST: All controller classes MUST be placed in the com.petreach.appointmentscheduler.controller package to maintain consistent organization.

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
- Service dependencies are injected via constructor rather than instantiated manually

<enforcement>
Claude Code MUST NOT skip or defer verification. All rules in this file are mandatory for controller implementations. Violations must be caught during code review and CI pipeline checks before merge.
</enforcement>
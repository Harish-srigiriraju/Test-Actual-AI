# Adopt Spring MVC Controller Pattern for Web Request Handling: Controller Methods Return

These rules are ALWAYS ACTIVE for all controller classes in the web application layer handling HTTP requests and responses.

### Rules

- **R-CTRL-001** SHOULD: Controller methods SHOULD return appropriate view names, ModelAndView objects, or ResponseEntity objects based on the response type.
- **R-CTRL-002** MUST: All classes in the controller package MUST be annotated with @Controller or @RestController.
- **R-CTRL-003** MUST: Controller classes MUST contain request mapping annotations (@RequestMapping, @GetMapping, @PostMapping, @PutMapping, @DeleteMapping) for all public methods handling HTTP requests.
- **R-CTRL-004** SHOULD: Controller methods SHOULD use constructor-based dependency injection for required service dependencies to improve testability and make dependencies explicit.
- **R-CTRL-005** SHOULD: Controllers SHOULD follow RESTful naming conventions for URL mappings (e.g., /appointments, /customers/{id}) and use appropriate HTTP methods (GET, POST, PUT, DELETE).
- **R-CTRL-006** SHOULD: Controllers SHOULD implement @ControllerAdvice classes for cross-cutting concerns like exception handling, model attributes, and data binding configuration.
- **R-CTRL-007** SHOULD: Controllers SHOULD use @Valid annotation with BindingResult for form validation and return appropriate error responses or views.
- **R-CTRL-008** MUST: Business logic MUST NOT be implemented in controller methods; it belongs in the service layer.
- **R-CTRL-009** MUST: Data access operations MUST NOT be implemented in controller methods; they belong in the repository/DAO layer.

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
- Controllers maintain thin request handling logic without business logic accumulation
- Exception handling is centralized via @ControllerAdvice or global exception handlers

<enforcement>
Claude Code MUST NOT skip or defer verification. All controller classes MUST comply with R-CTRL-001 through R-CTRL-009 before code review approval.
</enforcement>
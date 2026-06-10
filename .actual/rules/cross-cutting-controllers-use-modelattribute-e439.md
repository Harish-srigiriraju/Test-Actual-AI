# Adopt Spring MVC Controller Pattern for Web Request Handling: Controllers Use Modelattribute

These rules are ALWAYS ACTIVE for all HTTP request handling in the web application layer, including RESTful API endpoints, traditional MVC view rendering, AJAX request handlers, form submissions, and request routing configuration.

### Rules

- **R-CTRL-001** MAY: Controllers MAY use @ModelAttribute, @RequestParam, @PathVariable, and @RequestBody annotations for parameter binding as appropriate to the use case.
- **R-CTRL-002** MUST: All controller classes in com.petreach.appointmentscheduler.controller package MUST be annotated with @Controller or @RestController.
- **R-CTRL-003** MUST: All public methods handling HTTP requests MUST contain request mapping annotations (@RequestMapping, @GetMapping, @PostMapping, @PutMapping, or @DeleteMapping).
- **R-CTRL-004** SHOULD: Controllers SHOULD use constructor-based dependency injection for required service dependencies to improve testability and make dependencies explicit.
- **R-CTRL-005** SHOULD: Controllers SHOULD follow RESTful naming conventions for URL mappings (e.g., /appointments, /customers/{id}) and use appropriate HTTP methods (GET, POST, PUT, DELETE).
- **R-CTRL-006** SHOULD: Controllers SHOULD implement @ControllerAdvice classes for cross-cutting concerns like exception handling, model attributes, and data binding configuration.
- **R-CTRL-007** SHOULD: Controllers SHOULD use @Valid annotation with BindingResult for form validation and return appropriate error responses or views.
- **R-CTRL-008** MUST NOT: Business logic implementation MUST NOT reside in controller classes; it belongs in the service layer.
- **R-CTRL-009** MUST NOT: Data access operations MUST NOT be performed directly in controllers; they belong in the repository/DAO layer.

### Verify

```bash
# Count controller classes with Spring MVC annotations
grep -r '@Controller\|@RestController' java/com/petreach/appointmentscheduler/controller/ | wc -l

# Find controller files without Spring MVC annotations
find java/com/petreach/appointmentscheduler/controller/ -name '*Controller.java' -exec grep -L '@Controller\|@RestController' {} \;

# Count request mapping annotations
grep -r '@RequestMapping\|@GetMapping\|@PostMapping\|@PutMapping\|@DeleteMapping' java/com/petreach/appointmentscheduler/controller/ | wc -l
```

**Accept when:**
- All classes in the controller package are annotated with @Controller or @RestController
- No controller files exist without Spring MVC annotations (@RequestMapping, @GetMapping, etc.)
- Controller classes contain request mapping annotations for all public methods handling HTTP requests
- CI pipeline verification confirms 100% compliance with Spring MVC pattern requirements

<enforcement>
Claude Code MUST NOT skip or defer verification. All controller classes MUST comply with R-CTRL-002 and R-CTRL-003. Violations MUST be flagged during code review and CI pipeline execution MUST fail if non-compliant controllers are detected.
</enforcement>
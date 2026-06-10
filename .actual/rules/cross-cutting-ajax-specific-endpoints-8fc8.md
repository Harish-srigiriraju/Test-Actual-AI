# Adopt Spring MVC Controller Pattern for Web Request Handling: Ajax Specific Endpoints

These rules are ALWAYS ACTIVE for all HTTP request handling in the web application layer, including RESTful API endpoints, traditional MVC view rendering, AJAX request handlers, form submissions, and request routing configuration.

### Rules

- **R-AJAX-001** SHOULD: AJAX-specific endpoints SHOULD be separated into dedicated controller classes (e.g., AjaxController) or clearly marked with appropriate annotations.
- **R-MVC-001** MUST: All controller classes MUST be annotated with @Controller or @RestController.
- **R-MVC-002** MUST: All public methods handling HTTP requests MUST include request mapping annotations (@RequestMapping, @GetMapping, @PostMapping, @PutMapping, @DeleteMapping).
- **R-MVC-003** SHOULD: Controllers SHOULD use constructor-based dependency injection for required service dependencies.
- **R-MVC-004** SHOULD: Controllers SHOULD follow RESTful naming conventions for URL mappings (e.g., /appointments, /customers/{id}) and use appropriate HTTP methods.
- **R-MVC-005** SHOULD: Cross-cutting concerns like exception handling SHOULD be implemented via @ControllerAdvice classes.
- **R-MVC-006** SHOULD: Form validation SHOULD use @Valid annotation with BindingResult for appropriate error responses.
- **R-MVC-007** MUST: All controller classes MUST be placed in the com.petreach.appointmentscheduler.controller package.
- **R-MVC-008** MUST: Business logic MUST NOT be accumulated in controllers; it belongs in the service layer.

### Verify

```bash
# Count all @Controller and @RestController annotations
grep -r '@Controller\|@RestController' java/com/petreach/appointmentscheduler/controller/ | wc -l

# Find controller files without Spring MVC annotations
find java/com/petreach/appointmentscheduler/controller/ -name '*Controller.java' -exec grep -L '@Controller\|@RestController' {} \;

# Count all request mapping annotations
grep -r '@RequestMapping\|@GetMapping\|@PostMapping\|@PutMapping\|@DeleteMapping' java/com/petreach/appointmentscheduler/controller/ | wc -l
```

**Accept when:**
- All classes in the controller package are annotated with @Controller or @RestController
- No controller files exist without Spring MVC annotations (@RequestMapping, @GetMapping, etc.)
- Controller classes contain request mapping annotations for all public methods handling HTTP requests
- AJAX-specific endpoints are either in dedicated AjaxController class or clearly marked with annotations
- All controllers use constructor-based dependency injection
- Exception handling is centralized via @ControllerAdvice

<enforcement>
Claude Code MUST NOT skip or defer verification. All rules MUST be checked during code review and CI pipeline validation.
</enforcement>
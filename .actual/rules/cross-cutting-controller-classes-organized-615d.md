# Adopt Spring MVC Controller Pattern for Web Request Handling: Controller Classes Organized

These rules are ALWAYS ACTIVE for all controller classes in the web application layer handling HTTP requests and responses.

### Rules

- **R-CTRL-001** MUST: Controller classes MUST be organized by functional domain (e.g., AppointmentController, CustomerController, InvoiceController) rather than by technical layer.
- **R-CTRL-002** MUST: All controller classes MUST be annotated with @Controller or @RestController.
- **R-CTRL-003** MUST: All public methods handling HTTP requests MUST include request mapping annotations (@RequestMapping, @GetMapping, @PostMapping, @PutMapping, @DeleteMapping).
- **R-CTRL-004** MUST: Controller classes MUST use constructor-based dependency injection for required service dependencies.
- **R-CTRL-005** SHOULD: Controllers SHOULD follow RESTful naming conventions for URL mappings (e.g., /appointments, /customers/{id}) and use appropriate HTTP methods (GET, POST, PUT, DELETE).
- **R-CTRL-006** SHOULD: Form validation SHOULD use @Valid annotation with BindingResult and return appropriate error responses or views.
- **R-CTRL-007** SHOULD: Cross-cutting concerns like exception handling SHOULD be implemented via @ControllerAdvice classes.
- **R-CTRL-008** MAY: Simple utility endpoints performing no business logic (health checks, static configuration endpoints) MAY be exempted from standard controller organization (EXC-001).
- **R-CTRL-009** MAY: Legacy integration endpoints maintaining backward compatibility with non-Spring patterns MAY be exempted from standard controller organization (EXC-002).

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
- All controller classes are placed in com.petreach.appointmentscheduler.controller package
- Constructor-based dependency injection is used for service dependencies

<enforcement>
Claude Code MUST NOT skip or defer verification of these rules. All controller classes MUST comply with R-CTRL-001 through R-CTRL-007 unless explicitly documented exceptions (EXC-001, EXC-002) are approved by the Tech Lead and Architecture team.
</enforcement>
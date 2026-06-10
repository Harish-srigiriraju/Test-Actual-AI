# Adopt Spring MVC Controller Pattern for Web Request Handling: Request Mapping Annotations

These rules are ALWAYS ACTIVE for all HTTP request handling in the web application layer, including RESTful API endpoints, traditional MVC view rendering, AJAX request handlers, form submissions, and request routing configuration.

### Rules

- **R-SPRING-MVC-001** MUST: Request mapping annotations (@RequestMapping, @GetMapping, @PostMapping, @PutMapping, @DeleteMapping, etc.) MUST be used to declare URL routes and HTTP methods for all controller request handlers.

### Verify

```bash
# Count controller classes with Spring MVC annotations
grep -r '@Controller\|@RestController' java/com/petreach/appointmentscheduler/controller/ | wc -l

# Find controller files missing Spring MVC annotations
find java/com/petreach/appointmentscheduler/controller/ -name '*Controller.java' -exec grep -L '@Controller\|@RestController' {} \;

# Count request mapping annotations across controllers
grep -r '@RequestMapping\|@GetMapping\|@PostMapping\|@PutMapping\|@DeleteMapping' java/com/petreach/appointmentscheduler/controller/ | wc -l
```

**Accept when:**
- All classes in the controller package are annotated with @Controller or @RestController
- No controller files exist without Spring MVC annotations (@RequestMapping, @GetMapping, etc.)
- Controller classes contain request mapping annotations for all public methods handling HTTP requests

<enforcement>
Claude Code MUST NOT skip or defer verification. All controller classes must comply with R-SPRING-MVC-001 before code review approval.
</enforcement>
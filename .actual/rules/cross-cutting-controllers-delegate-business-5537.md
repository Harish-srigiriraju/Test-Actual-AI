# Adopt Spring MVC Controller Pattern for Web Request Handling: Controllers Delegate Business

These rules are ALWAYS ACTIVE for all controller classes in the web application layer handling HTTP requests and responses.

### Rules

- **R-CTRL-001** MUST: Controllers MUST delegate business logic to service layer components rather than implementing business rules directly.

### Verify

```bash
# Verify all controller classes use Spring MVC annotations
grep -r '@Controller\|@RestController' java/com/petreach/appointmentscheduler/controller/ | wc -l

# Find any controller files missing Spring MVC annotations
find java/com/petreach/appointmentscheduler/controller/ -name '*Controller.java' -exec grep -L '@Controller\|@RestController' {} \;

# Verify request mapping annotations are present
grep -r '@RequestMapping\|@GetMapping\|@PostMapping\|@PutMapping\|@DeleteMapping' java/com/petreach/appointmentscheduler/controller/ | wc -l
```

**Accept when:**
- All classes in the controller package are annotated with @Controller or @RestController
- No controller files exist without Spring MVC annotations (@RequestMapping, @GetMapping, etc.)
- Controller classes contain request mapping annotations for all public methods handling HTTP requests
- Controllers delegate business logic to service layer components and do not contain business rule implementations

<enforcement>
Claude Code MUST NOT skip or defer verification. All controller classes MUST comply with R-CTRL-001 before code is accepted.
</enforcement>
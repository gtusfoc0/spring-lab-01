package kz.iitu.springlab.web;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;

@RestController
@RequestMapping("/api")
public class HelloController {

    @Value("${app.owner:unknown}")
    private String owner;

    @GetMapping("/hello")
    public Greeting hello(@RequestParam(defaultValue = "world") String name) {
        return new Greeting("Hello, " + name + "!", owner, LocalDateTime.now());
    }

    @GetMapping("/info")
    public Info info() {
        return new Info(owner,
                System.getProperty("java.version"),
                Runtime.getRuntime().availableProcessors());
    }

    @GetMapping("/factorial")
    public ResponseEntity<?> factorial(@RequestParam(required = false) Integer n) {
        if (n == null) {
            return ResponseEntity
                    .badRequest()
                    .body(new ErrorResponse("Parameter 'n' is required"));
        }
        if (n < 0 || n > 20) {
            return ResponseEntity
                    .badRequest()
                    .body(new ErrorResponse("Parameter 'n' must be between 0 and 20"));
        }

        long result = 1L;
        for (int i = 2; i <= n; i++) {
            result *= i;
        }

        return ResponseEntity.ok(new FactorialResult(n, result));
    }

    public record FactorialResult(int n, long factorial) { }
    public record ErrorResponse(String error) { }

    public record Greeting(String message, String owner, LocalDateTime timestamp) { }
    public record Info(String owner, String javaVersion, int cpuCores) { }
}


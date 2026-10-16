package kz.iitu.springlab.web;

import kz.iitu.springlab.catalog.Book;
import kz.iitu.springlab.catalog.BookService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.util.List;

@RestController
@RequestMapping("/api/books")
public class BookRestController {

    private final BookService service;

    public BookRestController(BookService service) {
        this.service = service;
    }

    @GetMapping
    public List<Book> list(@RequestParam(required = false) String author,
                           @RequestParam(defaultValue = "10") int limit) {
        return service.findAll(author).stream().limit(limit).toList();
    }

    @GetMapping("/{id}")
    public ResponseEntity<Book> find(@PathVariable long id) {
        return service.findById(id)
                .map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

    @PostMapping
    public ResponseEntity<Book> create(@RequestBody Book book) {
        Book saved = service.create(book);
        return ResponseEntity
                .created(URI.create("/api/books/" + saved.id()))
                .body(saved);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Book> replace(@PathVariable long id, @RequestBody Book book) {
        return service.update(id, book)
                .map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable long id) {
        return service.delete(id)
                ? ResponseEntity.noContent().build()
                : ResponseEntity.notFound().build();
    }

    @GetMapping("/page")
    public ResponseEntity<List<Book>> page(@RequestParam(defaultValue = "0") int page,
                                           @RequestParam(defaultValue = "5") int size) {

        if (page < 0 || size < 1) {
            return ResponseEntity.badRequest().build();
        }

        List<Book> all = service.findAll(null);

        List<Book> content = all.stream()
                .skip((long) page * size)
                .limit(size)
                .toList();

        return ResponseEntity.ok()
                .header("X-Total-Count", String.valueOf(all.size()))
                .body(content);
    }
}
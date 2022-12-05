package com.it.music.api.controller;

import com.it.music.api.entity.Book;
import com.it.music.api.service.BookService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;
import java.util.Optional;

import static com.it.music.api.controller.BookController.URL;

@CrossOrigin(origins = "http://localhost:8080")
@RestController
@RequestMapping(URL)
public class BookController {

    static final String URL = "/books";

    @Autowired
    private BookService service;

    @GetMapping
    public ResponseEntity<List<Book>> getAllBooks(@RequestParam(required = false) Map<String, String> filters) {
        if (!filters.isEmpty()) return new ResponseEntity<>(service.selectGivenBooks(filters), HttpStatus.OK);

        List<Book> books = service.getAllBooks();

        if (!books.isEmpty()) return new ResponseEntity<>(service.getAllBooks(), HttpStatus.OK);
        else return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Book> getBookById(@PathVariable("id") Long id) {
        Optional<Book> book = service.getBook(id);
        return book.map(value -> new ResponseEntity<>(value, HttpStatus.OK)).orElseGet(()
                -> new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    @PutMapping("/{id}")
    public ResponseEntity<Book> editBook(@PathVariable Long id, @RequestBody Book book) {
        Optional<Book> bookData = service.getBook(id);

        if (bookData.isPresent()) {
            Book _book = bookData.get();
            _book.setCategory(book.getCategory());
            _book.setAuthor(book.getAuthor());
            _book.setIsbn(book.getIsbn());
            _book.setLanguage(book.getLanguage());
            _book.setPublicationDate(book.getPublicationDate());
            _book.setTaken(book.isTaken());
            _book.setName(book.getName());
            return new ResponseEntity<>(service.saveBook(_book), HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @PostMapping
    public ResponseEntity<Book> createBook(@RequestBody Book book) {
        try {
            Book _book = service
                    .saveBook(book);
            return new ResponseEntity<>(_book, HttpStatus.CREATED);
        } catch (Exception e) {
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @DeleteMapping
    public ResponseEntity<HttpStatus> deleteBook(@RequestParam Long id) {
        try {
            service.deleteBook(id);
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}

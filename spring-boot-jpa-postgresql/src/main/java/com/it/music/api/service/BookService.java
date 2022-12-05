package com.it.music.api.service;

import com.it.music.api.entity.Book;
import com.it.music.api.repository.BookRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@Service
public class BookService {

    @Autowired
    private BookRepository repository;

    public Book saveBook(Book book) {
        return repository.save(book);
    }

    public Optional<Book> getBook(Long id) {
        return repository.findById(id);
    }

    public List<Book> getAllBooks() {
        return repository.findAll();
    }

    public void deleteBook(Long id) {
        repository.deleteById(id);
    }

    public List<Book> selectGivenBooks(Map<String, String> filters) {

        List<Book> selectedBooks = new ArrayList<>();
        List<Book> allBooks = repository.findAll();

        if (filters.containsKey("author")) {
            allBooks.stream().filter(allBook -> allBook.getAuthor().equals(filters.get("author"))).forEach(selectedBooks::add);
        }
        if (filters.containsKey("category")) {
            allBooks.stream().filter(allBook -> allBook.getCategory().equals(filters.get("category"))).forEach(selectedBooks::add);
        }
        if (filters.containsKey("language")) {
            allBooks.stream().filter(allBook -> allBook.getLanguage().equals(filters.get("language"))).forEach(selectedBooks::add);
        }
        if (filters.containsKey("isbn")) {
            allBooks.stream().filter(allBook -> allBook.getIsbn().equals(filters.get("isbn"))).forEach(selectedBooks::add);
        }
        if (filters.containsKey("name")) {
            allBooks.stream().filter(allBook -> allBook.getName().equals(filters.get("name"))).forEach(selectedBooks::add);
        }
        if (filters.isEmpty()) {
            return allBooks;
        }

        return selectedBooks;
    }
}

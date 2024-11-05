package net.backend.library_management.controller;

import lombok.AllArgsConstructor;
import net.backend.library_management.dto.BookDto;
import net.backend.library_management.service.BookService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@CrossOrigin("*")
@RestController
@RequestMapping("api/books")
@AllArgsConstructor
public class BookController {

    private BookService bookService;


    // Build Add Book REST API
    @PreAuthorize("hasRole('ADMIN')")
    @PostMapping
    public ResponseEntity<BookDto> addBook(@RequestBody BookDto bookDto){
       BookDto savedBook =  bookService.addBook(bookDto);
       return new ResponseEntity<>(savedBook, HttpStatus.CREATED);
    }

    // Build GET Book REST API
    @PreAuthorize("hasAnyRole('ADMIN', 'USER')")
    @GetMapping("{id}")
    public ResponseEntity<BookDto> getBook(@PathVariable("id") Long bookId){
      BookDto bookDto =  bookService.getBook(bookId);
      return new ResponseEntity<>(bookDto, HttpStatus.OK);
    }


    // Build GET all Books REST API
    @PreAuthorize("hasAnyRole('ADMIN', 'USER')")
    @GetMapping
    public ResponseEntity<List<BookDto>> getAllBooks(){
        List<BookDto> books = bookService.getAllBooks();
        return new ResponseEntity<>(books, HttpStatus.OK);
    }

    // Build Update Book REST API
    @PreAuthorize("hasRole('ADMIN')")
    @PutMapping("{id}")
    public ResponseEntity<BookDto> updateBook(@RequestBody BookDto bookDto, @PathVariable("id") Long bookId){
        BookDto updatedBook = bookService.updateBook(bookDto, bookId);
        return ResponseEntity.ok(updatedBook);
    }

    //Build Delete Book REST API
    @PreAuthorize("hasRole('ADMIN')")
    @DeleteMapping("{id}")
    public ResponseEntity<String> deleteBook(@PathVariable("id") Long bookId){
        bookService.deleteTodo(bookId);
        return ResponseEntity.ok("Book Deleted Successfully!");
    }

    @PreAuthorize("hasAnyRole('ADMIN', 'USER')")
    //Build Borrow REST API
    @PatchMapping("{id}/borrow")
    public ResponseEntity<BookDto> borrowBook(@PathVariable("id") Long bookId){
        BookDto updatedBook = bookService.borrowBooks(bookId);
        return ResponseEntity.ok(updatedBook);
    }

    @PreAuthorize("hasAnyRole('ADMIN', 'USER')")
    // Build Return REST API
    @PatchMapping("{id}/return")
    public ResponseEntity<BookDto> returnBook(@PathVariable("id") Long returnBookId){
        BookDto updatedBook = bookService.returnBook(returnBookId);
        return ResponseEntity.ok(updatedBook);
    }




}

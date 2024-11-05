package net.backend.library_management.service;

import net.backend.library_management.dto.BookDto;

import java.util.List;

public interface BookService {

    BookDto addBook(BookDto bookDto);

    BookDto getBook(Long id);

    List<BookDto> getAllBooks();

    BookDto updateBook(BookDto bookDto, Long id);

    void deleteTodo(Long id);

//    Borrowed Books

    BookDto borrowBooks(Long id);

//    Return

    BookDto returnBook(Long id);

}

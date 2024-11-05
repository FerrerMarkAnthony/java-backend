package net.backend.library_management.service.BookServiceImpl;

import lombok.AllArgsConstructor;
import net.backend.library_management.dto.BookDto;
import net.backend.library_management.entity.Book;
import net.backend.library_management.exception.ResourceNotFoundException;
import net.backend.library_management.repository.BookRepository;
import net.backend.library_management.service.BookService;
import org.modelmapper.ModelMapper;
import org.springframework.boot.context.config.ConfigDataResourceNotFoundException;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;


@Service
@AllArgsConstructor

public class BookServiceImpl implements BookService {


    private BookRepository bookRepository;

    private ModelMapper modelMapper;

    @Override
    public BookDto addBook(BookDto bookDto) {

        Book book = modelMapper.map(bookDto, Book.class);

//        Book book = new Book();
//        book.setTitle(bookDto.getTitle());
//        book.setDescription(bookDto.getDescription());
//        book.setAuthor(bookDto.getAuthor());
//        book.setPrice(bookDto.getPrice());
//        book.setBorrowed(bookDto.isBorrowed());

        Book savedBook = bookRepository.save(book);


//        BookDto savedBookDto = new BookDto();
//        savedBookDto.setId(savedBook.getId());
//        savedBookDto.setTitle(savedBook.getTitle());
//        savedBookDto.setDescription(savedBook.getDescription());
//        savedBookDto.setAuthor(savedBook.getAuthor());
//        savedBookDto.setPrice(savedBook.getPrice());
//        savedBookDto.setBorrowed(savedBook.isBorrowed());


        return modelMapper.map(savedBook, BookDto.class);
    }

    @Override
    public BookDto getBook(Long id) {

        Book book = bookRepository.findById(id)
               .orElseThrow(()-> new ResourceNotFoundException("Book not found with id: " + id));

        return modelMapper.map(book, BookDto.class);
    }

    @Override
    public List<BookDto> getAllBooks() {

       List<Book> books = bookRepository.findAll();

       return books.stream().map((book) -> modelMapper.map(book, BookDto.class))
               .collect(Collectors.toList());
    }

    @Override
    public BookDto updateBook(BookDto bookDto,Long id) {
        Book book = bookRepository.findById(id)
                .orElseThrow(()-> new ResourceNotFoundException("Todo not found with id: "+ id));

        book.setTitle(bookDto.getTitle());
        book.setAuthor(bookDto.getAuthor());
        book.setDescription(bookDto.getDescription());
        book.setPrice(bookDto.getPrice());
        book.setBorrowed(bookDto.isBorrowed());

        Book updatedBook = bookRepository.save(book);

        return modelMapper.map(updatedBook, BookDto.class);
    }

    @Override
    public void deleteTodo(Long id) {
        bookRepository.findById(id)
                .orElseThrow(()-> new ResourceNotFoundException("Book not found with id: " + id));

        bookRepository.deleteById(id);

    }

    @Override
    public BookDto borrowBooks(Long id) {
        Book book = bookRepository.findById(id)
                .orElseThrow(()-> new ResourceNotFoundException("Book not found with id: " + id));

        if (book.isBorrowed()) {
            throw new RuntimeException("Book is already borrowed");
        }

        book.setBorrowed(Boolean.TRUE);
        Book updatedBookBorrowed = bookRepository.save(book);
        book.setBorrowDate(LocalDate.now());
        book.setReturnDate(null);
        return modelMapper.map(updatedBookBorrowed, BookDto.class);

    }

    @Override
    public BookDto returnBook(Long id) {
        Book book = bookRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Book not found"));

        if (!book.isBorrowed()) {
            throw new RuntimeException("Book is not borrowed");
        }


        book.setBorrowed(false);
        book.setReturnDate(LocalDate.now());
        Book updatedBook = bookRepository.save(book);

        // Return the mapped DTO
        return modelMapper.map(updatedBook, BookDto.class);
    }

}

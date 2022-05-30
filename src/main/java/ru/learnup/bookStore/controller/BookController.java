package ru.learnup.bookStore.controller;

import com.google.common.base.Joiner;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.modelmapper.ModelMapper;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.annotation.Secured;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import ru.learnup.bookStore.dto.BookDTO;
import ru.learnup.bookStore.entity.Author;
import ru.learnup.bookStore.entity.Book;
import ru.learnup.bookStore.entity.BookWarehouse;
import ru.learnup.bookStore.entity.OrderDetails;
import ru.learnup.bookStore.service.implementation.BookServiceImpl;
import ru.learnup.bookStore.specification.builder.BookSpecificationsBuilder;
import ru.learnup.bookStore.specification.util.SearchOperation;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

@RestController
@RequestMapping("api/v1/books")
@Tag(name="BookController", description="Контроллирует CRUD операции с энтити Book")
public class BookController {

    BookServiceImpl bookServiceImpl;
    ModelMapper modelMapper;

    public BookController(BookServiceImpl bookServiceImpl, ModelMapper modelMapper) {
        this.bookServiceImpl = bookServiceImpl;
        this.modelMapper = modelMapper;
    }

    @GetMapping
    @Operation(
            summary = "Get запрос книг",
            description = "Get запрос книг с реквест параметрами page, size, sort. DefaultValue у pageable: page=0," +
                    "size=5, sort=title"
    )
    public ResponseEntity<List<BookDTO.Response.Public>> getBooks(@RequestParam(value = "page", defaultValue = "0") int page,
                                                                  @RequestParam(value = "size", defaultValue = "5") int size,
                                                                  @RequestParam(value = "sort", defaultValue = "title") String sort,
                                                                  Pageable pageable) {
        List<Book> books = bookServiceImpl.getBooks(pageable);
        List<BookDTO.Response.Public> mappedBooks = new ArrayList<>(books.size());
        for (Book book :
                books) {
            mappedBooks.add(modelMapper.map(book, BookDTO.Response.Public.class));
        }
        return new ResponseEntity<>(mappedBooks, HttpStatus.OK);
    }

    @GetMapping("/{bookId}")
    public ResponseEntity<BookDTO.Response.Public> getBookById(@PathVariable Long bookId) {
        Book book = bookServiceImpl.getById(bookId);
        return new ResponseEntity<>(modelMapper.map(book, BookDTO.Response.Public.class), HttpStatus.OK);
    }

    @PostMapping
    @Secured("ROLE_ADMIN")
    public ResponseEntity<BookDTO.Response.Public> createBook(@RequestBody BookDTO.Request.Public newBook) {
        Book book = modelMapper.map(newBook, Book.class);
        return new ResponseEntity<>(modelMapper.map(bookServiceImpl.createBook(book), BookDTO.Response.Public.class),
                HttpStatus.CREATED);
    }

    @GetMapping("/spec")
    public ResponseEntity<BookDTO.Response.Public> getAllBySpecification(@RequestParam(value = "search") String search){
        BookSpecificationsBuilder builder = new BookSpecificationsBuilder();
        String operationSetExper = Joiner.on("|")
                .join(SearchOperation.SIMPLE_OPERATION_SET);
        Pattern pattern = Pattern.compile("(\\w+?)(" + operationSetExper + ")(\\p{Punct}?)(\\w+?)(\\p{Punct}?),");
        Matcher matcher = pattern.matcher(search + ",");
        while (matcher.find()) {
            builder.with(matcher.group(1), matcher.group(2), matcher.group(4), matcher.group(3), matcher.group(5));
        }

        Specification<Book> spec = builder.build();
        return new ResponseEntity<>(modelMapper.map(bookServiceImpl.getAllBySpec(spec),
                BookDTO.Response.Public.class), HttpStatus.OK);
    }

//    PutMapping
    @PutMapping("/{bookId}")
    @Secured({"ROLE_ADMIN"})
    public BookDTO.Response.Public updateBook(@PathVariable("bookId") Long bookId, @RequestBody BookDTO.Request.Public body) {
        Book book = bookServiceImpl.getById(bookId);
        if (!book.getNumberOfPages().equals(body.getNumberOfPages())) {
            book.setNumberOfPages(body.getNumberOfPages());
        }
        Set<Author> authorEntities = body.getAuthorDTOs().stream()
                .map(v -> modelMapper.map(v, Author.class))
                .collect(Collectors.toCollection(HashSet::new)); // mapping
        if (!book.getAuthors().equals(authorEntities)) {
            book.setAuthors(authorEntities);
        }
        BookWarehouse bookWarehouseEntity = modelMapper.map(body.getBookWarehouse(), BookWarehouse.class);
        if (!book.getWarehouse().equals(bookWarehouseEntity)) {
            book.setWarehouse(bookWarehouseEntity);
        }
        List<OrderDetails> orderDetailsEntities = body.getOrderDetailsDTOList().stream()
                .map(v -> modelMapper.map(v, OrderDetails.class))
                .collect(Collectors.toCollection(ArrayList::new));
        if (!book.getOrderDetailsList().equals(orderDetailsEntities)) {
            book.setOrderDetailsList(orderDetailsEntities);
        }

        Book updatedBook = bookServiceImpl.updateBook(book);

        return modelMapper.map(updatedBook, BookDTO.Response.Public.class);
    }

    @DeleteMapping
    @Secured({"ROLE_ADMIN"})
    public Boolean deleteBook(@PathVariable Long id) {
        return bookServiceImpl.deleteBook(id);
    }

}

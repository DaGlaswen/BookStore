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
import ru.learnup.bookStore.dto.BookWarehouseDTO;
import ru.learnup.bookStore.entity.Book;
import ru.learnup.bookStore.entity.BookWarehouse;
import ru.learnup.bookStore.service.interfaces.BookWarehouseService;
import ru.learnup.bookStore.specification.builder.BookWarehouseSpecificationsBuilder;
import ru.learnup.bookStore.specification.util.SearchOperation;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@RestController
@RequestMapping("api/v1/bookWarehouses")
@Tag(name="BookWarehouseController", description="Контроллирует CRUD операции с энтити BookWarehouse")
public class BookWarehouseController {

    BookWarehouseService bookWarehouseService;
    ModelMapper modelMapper;

    public BookWarehouseController(BookWarehouseService bookWarehouseService, ModelMapper modelMapper) {
        this.bookWarehouseService = bookWarehouseService;
        this.modelMapper = modelMapper;
    }

    @GetMapping
    @Operation(
            summary = "Get запрос складов с книгами",
            description = "Get запрос книг с реквест параметрами page, size, sort. DefaultValue у pageable: page=0," +
                    "size=5, sort=title"
    )
    public ResponseEntity<List<BookWarehouseDTO.Response.Public>> getBookWarehouses(@RequestParam(value = "page", defaultValue = "0") int page,
                                                                           @RequestParam(value = "size", defaultValue = "5") int size,
                                                                           @RequestParam(value = "sort", defaultValue = "title") String sort,
                                                                           Pageable pageable) {
        List<BookWarehouse> bookWarehouses = bookWarehouseService.getBookWarehouses(pageable);
        List<BookWarehouseDTO.Response.Public> mappedBookWarehouses = new ArrayList<>(bookWarehouses.size());
        for (BookWarehouse bookWarehouse :
                bookWarehouses) {
            mappedBookWarehouses.add(modelMapper.map(bookWarehouse, BookWarehouseDTO.Response.Public.class));
        }
        return new ResponseEntity<>(mappedBookWarehouses, HttpStatus.OK);
    }

    @GetMapping("/{bookWarehouseId}")
    public ResponseEntity<BookWarehouseDTO.Response.Public> getBookWarehouseById(@PathVariable Long bookWarehouseId) {
        BookWarehouse bookWarehouse = bookWarehouseService.getById(bookWarehouseId);
        return new ResponseEntity<>(modelMapper.map(bookWarehouse, BookWarehouseDTO.Response.Public.class), HttpStatus.OK);
    }

    @PostMapping
    @Secured("ROLE_ADMIN")
    public ResponseEntity<BookWarehouseDTO.Response.Public> createBookWarehouse(@RequestBody BookWarehouseDTO.Request.Public BookWarehouse) {
        return new ResponseEntity<>(modelMapper
                .map(bookWarehouseService.createBookWarehouse(modelMapper.map(BookWarehouse, BookWarehouse.class))
                        , BookWarehouseDTO.Response.Public.class),
                HttpStatus.CREATED);
    }

    @GetMapping("/spec")
    public ResponseEntity<BookWarehouseDTO.Response.Public> getAllBySpecification(@RequestParam(value = "search") String search){
        BookWarehouseSpecificationsBuilder builder = new BookWarehouseSpecificationsBuilder();
        String operationSetExper = Joiner.on("|")
                .join(SearchOperation.SIMPLE_OPERATION_SET);
        Pattern pattern = Pattern.compile("(\\w+?)(" + operationSetExper + ")(\\p{Punct}?)(\\w+?)(\\p{Punct}?),");
        Matcher matcher = pattern.matcher(search + ",");
        while (matcher.find()) {
            builder.with(matcher.group(1), matcher.group(2), matcher.group(4), matcher.group(3), matcher.group(5));
        }

        Specification<BookWarehouse> spec = builder.build();
        return new ResponseEntity<>(modelMapper.map(bookWarehouseService.getAllBySpec(spec),
                BookWarehouseDTO.Response.Public.class), HttpStatus.OK);
    }

    @PutMapping("/{bookWarehouseId}")
    @Secured({"ROLE_ADMIN"})
    public ResponseEntity<BookWarehouseDTO.Response.Public> updateBookWarehouse(@PathVariable("bookWarehouseId") Long bookWarehouseId, @RequestBody BookWarehouseDTO.Request.Public body) {
        BookWarehouse bookWarehouse = bookWarehouseService.getById(bookWarehouseId);
        Book bookEntity = modelMapper.map(body.getBook(), Book.class);
        if (!bookWarehouse.getBook().equals(bookEntity)) {
            bookWarehouse.setBook(bookEntity);
        }
        if (!bookWarehouse.getBooksLeftInStock().equals(body.getBooksLeftInStore())) {
            bookWarehouse.setBooksLeftInStock(body.getBooksLeftInStore());
        }

        BookWarehouse updatedBookWarehouse = bookWarehouseService.updateBookWarehouse(bookWarehouse);

        return new ResponseEntity<>(modelMapper.map(updatedBookWarehouse, BookWarehouseDTO.Response.Public.class),
                HttpStatus.OK);
    }

    @DeleteMapping("/{bookWarehouseId}")
    @Secured({"ROLE_ADMIN"})
    public Boolean deleteBookWarehouse(@PathVariable Long id) {
        return bookWarehouseService.deleteBookWarehouse(id);
    }

}

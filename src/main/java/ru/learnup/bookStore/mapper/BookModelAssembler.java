package ru.learnup.bookStore.mapper;

import org.modelmapper.ModelMapper;
import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.Link;
import org.springframework.hateoas.server.RepresentationModelAssembler;
import org.springframework.stereotype.Component;
import ru.learnup.bookStore.controller.BookController;
import ru.learnup.bookStore.dto.BookDTO;
import ru.learnup.bookStore.entity.Book;

import java.util.ArrayList;
import java.util.List;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

@Component
public class BookModelAssembler implements RepresentationModelAssembler<Book, BookDTO.Response.Public> {

    private ModelMapper modelMapper;

    public BookModelAssembler(ModelMapper modelMapper) {
        this.modelMapper = modelMapper;
    }

    @Override
    public BookDTO.Response.Public toModel(Book book) {
        BookDTO.Response.Public bookDTO = modelMapper.map(book, BookDTO.Response.Public.class);

        Link selfLink = linkTo(methodOn(BookController.class).getBookById(book.getBookId())).withSelfRel();
        bookDTO.add(selfLink);

        Link booksLink = linkTo(methodOn(BookController.class).getAllBooks()).withRel("allBooks");
        bookDTO.add(booksLink);

        return bookDTO;
    }

    public Book fromModel(BookDTO.Request.Public bookDTO) {
        return modelMapper.map(bookDTO, Book.class);
    }

    @Override
    public CollectionModel<BookDTO.Response.Public> toCollectionModel(Iterable<? extends Book> books) {
        List<BookDTO.Response.Public> bookDTOS = new ArrayList<>();

        for (Book book : books){
            BookDTO.Response.Public bookDTO = modelMapper.map(book, BookDTO.Response.Public.class);
            bookDTO.add(linkTo(methodOn(BookController.class)
                    .getBookById(bookDTO.getBookId())).withSelfRel());
            bookDTOS.add(bookDTO);
        }

        CollectionModel<BookDTO.Response.Public> result = CollectionModel.of(bookDTOS);
        Link booksLink = linkTo(methodOn(BookController.class).getAllBooks()).withSelfRel();
        return result.add(booksLink);
    }
}

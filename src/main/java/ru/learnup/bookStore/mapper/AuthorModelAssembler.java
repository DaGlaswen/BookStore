package ru.learnup.bookStore.mapper;

import org.modelmapper.ModelMapper;
import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.Link;
import org.springframework.hateoas.server.RepresentationModelAssembler;
import org.springframework.stereotype.Component;
import ru.learnup.bookStore.controller.AuthorController;
import ru.learnup.bookStore.dto.AuthorDTO;
import ru.learnup.bookStore.entity.Author;

import java.util.ArrayList;
import java.util.List;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

@Component
public class AuthorModelAssembler implements RepresentationModelAssembler<Author, AuthorDTO.Response.Public> {

    private ModelMapper modelMapper;

    public AuthorModelAssembler(ModelMapper modelMapper) {
        this.modelMapper = modelMapper;
    }

    @Override
    public AuthorDTO.Response.Public toModel(Author author) {
        AuthorDTO.Response.Public authorDTO = modelMapper.map(author, AuthorDTO.Response.Public.class);

        Link selfLink = linkTo(methodOn(AuthorController.class).getAuthorById(author.getAuthorId())).withSelfRel();
        authorDTO.add(selfLink);

        Link authorLink = linkTo(methodOn(AuthorController.class).getAllAuthors()).withRel("allAuthors");
        authorDTO.add(authorsLink);

        return authorDTO;
    }

    public Author fromModel(AuthorDTO.Request.Public authorDTO) {
        return modelMapper.map(authorDTO, Author.class);
    }

    @Override
    public CollectionModel<AuthorDTO.Response.Public> toCollectionModel(Iterable<? extends Author> authors) {
        List<AuthorDTO.Response.Public> authorDTOS = new ArrayList<>();

        for (Author author : authors){
            AuthorDTO.Response.Public authorDTO = modelMapper.map(author, AuthorDTO.Response.Public.class);
            authorDTO.add(linkTo(methodOn(AuthorController.class)
                    .getAuthorById(authorDTO.getAuthorId())).withSelfRel());
            authorDTOS.add(authorDTO);
        }

        CollectionModel<AuthorDTO.Response.Public> result = CollectionModel.of(auhtorDTOS);
        Link authorsLink = linkTo(methodOn(AuthorController.class).getAllAuthors()).withSelfRel();
        return result.add(authorsLink);
    }
}

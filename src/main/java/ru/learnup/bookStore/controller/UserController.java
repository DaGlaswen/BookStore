package ru.learnup.bookStore.controller;

import org.modelmapper.ModelMapper;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.annotation.Secured;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import ru.learnup.bookStore.dto.UserDTO;
import ru.learnup.bookStore.entity.User;
import ru.learnup.bookStore.service.UserService;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("users")
public class UserController {

    private UserService userService;
    private ModelMapper modelMapper;

    public UserController(UserService userService, ModelMapper modelMapper) {
        this.userService = userService;
        this.modelMapper = modelMapper;
    }

    @PostMapping
    public ResponseEntity<UserDTO.Response.Public> createUser(@RequestBody UserDTO.Request.Public userDTO) {
        User createdUser = userService.create(modelMapper.map(userDTO, User.class));
        return new ResponseEntity<>(modelMapper.map(createdUser, UserDTO.Response.Public.class), HttpStatus.CREATED);
    }


    @Secured({"ROLE_ADMIN"})
    @GetMapping
    public ResponseEntity<List<UserDTO.Response.Public>> getActiveUsers() {
        return new ResponseEntity<>(userService.findAll().stream().map(user
                -> modelMapper.map(user, UserDTO.Response.Public.class)).collect(Collectors.toList()), HttpStatus.OK);
    }
}

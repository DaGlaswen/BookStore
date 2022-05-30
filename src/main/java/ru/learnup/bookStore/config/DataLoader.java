package ru.learnup.bookStore.config;

import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.stereotype.Component;
import ru.learnup.bookStore.entity.Role;
import ru.learnup.bookStore.entity.User;
import ru.learnup.bookStore.repository.RoleRepository;
import ru.learnup.bookStore.repository.UserRepository;
import ru.learnup.bookStore.service.UserService;

import java.util.HashSet;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@ConditionalOnProperty(
        prefix = "application.runner", // Set this property to true when running the application for the first time, then delete the property!
        value = "enabled")
@Component
public class DataLoader implements ApplicationRunner {

    private UserService userService;
    private RoleRepository roleRepository;

    public DataLoader(UserService userService, RoleRepository roleRepository) {
        this.userService = userService;
        this.roleRepository = roleRepository;
    }

    public void run(ApplicationArguments args) {
        Role user = roleRepository.save(Role.builder().role("ROLE_USER").build());
        Role admin = roleRepository.save(Role.builder().role("ROLE_ADMIN").build());

        userService.create(User.builder().username("glaswen").password("random password").build().addRole(admin));
        userService.create(User.builder().username("lizz").password("random pasrd").build().addRole(admin).addRole(user));
        userService.create(User.builder().username("cat").password("ndom password").build().addRole(user));
        userService.create(User.builder().username("sam").password("ram password").build().addRole(user));
        userService.create(User.builder().username("josh").password("randsword").build().addRole(user));
        userService.create(User.builder().username("yezzy").password("m password").build().addRole(user));
        userService.create(User.builder().username("jesus").password("rad").build().addRole(user));



    }
}

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
import ru.learnup.bookStore.dto.BookOrderDTO;
import ru.learnup.bookStore.dto.CustomerDTO;
import ru.learnup.bookStore.entity.BookOrder;
import ru.learnup.bookStore.entity.Customer;
import ru.learnup.bookStore.service.interfaces.CustomerService;
import ru.learnup.bookStore.specification.builder.CustomerSpecificationsBuilder;
import ru.learnup.bookStore.specification.util.SearchOperation;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

@RestController
@RequestMapping("api/v1/customers")
@Tag(name="CustomerController", description="Контроллирует CRUD операции с энтити Customer")
public class CustomerController {

    CustomerService customerService;
    ModelMapper modelMapper;

    public CustomerController(CustomerService customerService, ModelMapper modelMapper) {
        this.customerService = customerService;
        this.modelMapper = modelMapper;
    }

    @GetMapping
    @Operation(
            summary = "Get запрос клиентов",
            description = "Get запрос клиентов с реквест параметрами page, size, sort. DefaultValue у pageable: page=0," +
                    "size=5, sort=title"
    )
    public ResponseEntity<List<CustomerDTO.Response.Public>> getCustomers(@RequestParam(value = "page", defaultValue = "0") int page,
                                                                      @RequestParam(value = "size", defaultValue = "5") int size,
                                                                      @RequestParam(value = "sort", defaultValue = "title") String sort,
                                                                      Pageable pageable) {
        List<Customer> customers = customerService.getCustomers(pageable);
        List<CustomerDTO.Response.Public> mappedCustomers = new ArrayList<>(customers.size());
        for (Customer customer :
                customers) {
            mappedCustomers.add(modelMapper.map(customer, CustomerDTO.Response.Public.class));
        }
        return new ResponseEntity<>(mappedCustomers, HttpStatus.OK);
    }

    @GetMapping("/{customerId}")
    public ResponseEntity<CustomerDTO.Response.Public> getCustomerById(@PathVariable Long customerId) {
        Customer customer = customerService.getById(customerId);
        return new ResponseEntity<>(modelMapper.map(customer, CustomerDTO.Response.Public.class), HttpStatus.OK);
    }

    @PostMapping
    @Secured("ROLE_ADMIN")
    public ResponseEntity<CustomerDTO.Response.Public> createCustomer(@RequestBody CustomerDTO.Request.Public customer) {
        return new ResponseEntity<>(modelMapper.map(customerService
                .createCustomer(modelMapper.map(customer, Customer.class)), CustomerDTO.Response.Public.class),
                HttpStatus.CREATED);
    }

    @GetMapping("/spec")
    public ResponseEntity<CustomerDTO.Response.Public> getAllBySpecification(@RequestParam(value = "search") String search){
        CustomerSpecificationsBuilder builder = new CustomerSpecificationsBuilder();
        String operationSetExper = Joiner.on("|")
                .join(SearchOperation.SIMPLE_OPERATION_SET);
        Pattern pattern = Pattern.compile("(\\w+?)(" + operationSetExper + ")(\\p{Punct}?)(\\w+?)(\\p{Punct}?),");
        Matcher matcher = pattern.matcher(search + ",");
        while (matcher.find()) {
            builder.with(matcher.group(1), matcher.group(2), matcher.group(4), matcher.group(3), matcher.group(5));
        }

        Specification<Customer> spec = builder.build();
        return new ResponseEntity<>(modelMapper.map(customerService.getAllBySpec(spec),
                CustomerDTO.Response.Public.class), HttpStatus.OK);
    }

    @PutMapping("/{customerId}")
    @Secured({"ROLE_ADMIN"})
    public ResponseEntity<CustomerDTO.Response.Public> updateCustomer(@PathVariable("customerId") Long customerId, @RequestBody CustomerDTO.Request.Public body) {
        Customer customer = customerService.getById(customerId);
        if (!customer.getBirthDate().equals(body.getBirthDate())) {
            customer.setBirthDate(body.getBirthDate());
        }
        if (!customer.getFirstName().equals(body.getFirstName())) {
            customer.setFirstName(body.getFirstName());
        }
        if (!customer.getLastName().equals(body.getLastName())) {
            customer.setLastName(body.getLastName());
        }
        List<BookOrder> bookOrderEntities = body.getBookOrders().stream()
                .map(v -> modelMapper.map(v, BookOrder.class)).collect(Collectors.toCollection(ArrayList::new));
        if (!customer.getBookOrders().equals(bookOrderEntities)) {
            customer.setBookOrders(bookOrderEntities);
        }

        Customer updatedCustomer = customerService.updateCustomer(customer);

        return new ResponseEntity<>(modelMapper.map(updatedCustomer, CustomerDTO.Response.Public.class), HttpStatus.OK);
    }

    @DeleteMapping
    @Secured({"ROLE_ADMIN"})
    public Boolean deleteCustomer(@PathVariable Long id) {
        return customerService.deleteCustomer(id);
    }

}
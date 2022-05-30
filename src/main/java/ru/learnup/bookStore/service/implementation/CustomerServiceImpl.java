package ru.learnup.bookStore.service.implementation;

import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.jpa.repository.Lock;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.learnup.bookStore.entity.Customer;
import ru.learnup.bookStore.repository.CustomerRepository;
import ru.learnup.bookStore.service.interfaces.CustomerService;

import javax.persistence.LockModeType;
import javax.persistence.OptimisticLockException;
import java.util.List;
import java.util.stream.Collectors;

@Slf4j
@Service
public class CustomerServiceImpl implements CustomerService {

    CustomerRepository customerRepository;

    public CustomerServiceImpl(CustomerRepository customerRepository) {
        this.customerRepository = customerRepository;
    }

    @Override
    @Transactional
    public Customer createCustomer(Customer customer) {
        return customerRepository.save(customer);
    }

    @Override
    @Transactional
    @Lock(value = LockModeType.READ)
    public Customer updateCustomer(Customer customer) {
        try {
            return customerRepository.save(customer);
        } catch (OptimisticLockException e) {
            log.warn("Optimistic log exception for customer {}", customer.getCustomerId());
            throw e;
        }
    }

    @Override
    @Transactional
    public Boolean deleteCustomer(Long id) {
        customerRepository.delete(customerRepository.getById(id));
        return true;
    }

    @Override
    public Customer getById(Long id) {
        return customerRepository.getById(id);
    }

    @Override
    public List<Customer> getCustomers(Pageable pageable) {
        return customerRepository.findAll(pageable).stream().collect(Collectors.toList());
    }

    @Override
    public List<Customer> getAllBySpec(Specification<Customer> spec) {
        return customerRepository.findAll(spec);
    }
}

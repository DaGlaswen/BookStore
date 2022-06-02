package ru.learnup.bookStore.service.interfaces;

import ru.learnup.bookStore.entity.BookOrder;

import java.util.HashMap;
import java.util.HashSet;

public interface PurchaseService {

    BookOrder buyBook(Long customerId, HashMap<Long, Integer> bookMap);
}

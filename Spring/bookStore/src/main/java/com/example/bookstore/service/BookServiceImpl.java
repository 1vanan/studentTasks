package com.example.bookstore.service;

import com.example.bookstore.dto.Book;
import com.example.bookstore.exceptions.BookIdMismatchException;
import com.example.bookstore.exceptions.BookNotFoundException;
import com.example.bookstore.repository.BookRepository;
import java.util.Optional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Slf4j
@Service
@RequiredArgsConstructor
public class BookServiceImpl implements BookService{
    private final BookRepository bookRepository;

    @Override public String getBookInfo(Long id) {
        Optional<Book> bookOpt = bookRepository.findById(id);
        String res = "";
        if(bookOpt.isPresent()){
            Book book = bookOpt.get();
            res =  "The book is found! Author: " + book.getAuthor();
        }
        else {
            throw new BookNotFoundException();
        }
        return res;
    }
}

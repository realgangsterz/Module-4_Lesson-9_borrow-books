package com.codegym.cms.service;

import com.codegym.cms.model.Book;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;


public interface BookService {
    Page<Book> findAll(Pageable pageable);

    Book findById(Long id);

    void save(Book book);

    void remove(Long id);

    Page<Book> findAllByNameContaining(String name, Pageable pageable);

    void borrowBook(Book book);
}
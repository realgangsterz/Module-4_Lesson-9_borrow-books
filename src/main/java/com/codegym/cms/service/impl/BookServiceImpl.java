package com.codegym.cms.service.impl;

import com.codegym.cms.model.Book;
import com.codegym.cms.repository.BookRepository;
import com.codegym.cms.service.BookService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public class BookServiceImpl implements BookService {

    @Autowired
    private BookRepository bookRepository;

    @Override
    public Page<Book> findAll(Pageable pageable) {
        return bookRepository.findAll(pageable);
    }

    @Override
    public Book findById(Long id) {
        return bookRepository.findOne( id );
    }

    @Override
    public void save(Book book) {
        bookRepository.save( book );
    }

    @Override
    public void remove(Long id) {
        bookRepository.delete( id );
    }

    @Override
    public Page<Book> findAllByNameContaining(String name, Pageable pageable) {
        return bookRepository.findAllByNameContaining(name, pageable);

    }


    @Override
    public void borrowBook(Book book){
        Book borBook = bookRepository.findOne(book.getId());
        int quan = borBook.getQuantity();
        if (quan > 0) {
            borBook.setQuantity(quan - 1);
        } else if (quan == 0){
            borBook.setQuantity(0);
        }else borBook.setQuantity(0);
        bookRepository.save(borBook);
    }

}

package com.codegym.cms.controller;

import com.codegym.cms.model.Book;

import com.codegym.cms.service.BookService;

import com.codegym.cms.service.impl.BookServiceImpl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import java.util.Optional;

@Controller
public class BookController {
    @Autowired
    private BookService bookService = new BookServiceImpl();





    @GetMapping("/books")
    public ModelAndView listBooks(@RequestParam("s") Optional<String> s, Pageable pageable, @RequestParam("page") Optional<String> page) {
        Page<Book> books;
        int t = 0;
        if (page.isPresent()) {
            t = Integer.parseInt( page.get() );
        }
        pageable = new PageRequest( t, 10 );
        if (s.isPresent()) {
            books = bookService.findAllByNameContaining( s.get(), pageable );
        } else {
            books = bookService.findAll( pageable );
        }
        ModelAndView modelAndView = new ModelAndView( "/book/list" );
        modelAndView.addObject( "books", books );
        return modelAndView;
    }

    @GetMapping("/create-book")
    public ModelAndView showCreateForm() {
        ModelAndView modelAndView = new ModelAndView( "/book/create" );
        modelAndView.addObject( "book", new Book() );
        return modelAndView;
    }

    @PostMapping("/create-book")
    public ModelAndView saveCustomer(@ModelAttribute("book") Book book) {
        bookService.save( book );
        ModelAndView modelAndView = new ModelAndView( "/book/create" );
        modelAndView.addObject( "book", new Book() );
        modelAndView.addObject( "message", "New customer created successfully" );
        return modelAndView;
    }

    @GetMapping("/edit-book/{id}")
    public ModelAndView showEditForm(@PathVariable Long id) {
        Book book = bookService.findById( id );
        if (book != null) {
            ModelAndView modelAndView = new ModelAndView( "/book/edit" );
            modelAndView.addObject( "book", book );
            return modelAndView;

        } else {
            ModelAndView modelAndView = new ModelAndView( "/error.404" );
            return modelAndView;
        }
    }

    @PostMapping("/edit-book")
    public ModelAndView updateCustomer(@ModelAttribute("book") Book book) {
        bookService.save( book );
        ModelAndView modelAndView = new ModelAndView( "/book/edit" );
        modelAndView.addObject( "book", book );
        modelAndView.addObject( "message", "Book updated successfully" );
        return modelAndView;
    }

    @GetMapping("/delete-book/{id}")
    public ModelAndView showDeleteForm(@PathVariable Long id) {
        Book book = bookService.findById( id );
        if (book != null) {
            ModelAndView modelAndView = new ModelAndView( "/book/delete" );
            modelAndView.addObject( "book", book );
            return modelAndView;

        } else {
            ModelAndView modelAndView = new ModelAndView( "/error.404" );
            return modelAndView;
        }
    }

    @PostMapping("/delete-book")
    public String deleteCustomer(@ModelAttribute("book") Book book) {
        bookService.remove( book.getId() );
        return "redirect:books";
    }

    @GetMapping("/infor-book/{id}")
    public ModelAndView showInforBook(@PathVariable Long id){
        Book book = bookService.findById(id);
        ModelAndView modelAndView = new ModelAndView("/book/infor");
        modelAndView.addObject("book",book);
        return modelAndView;
    }

    @PostMapping("/borrow")
    public ModelAndView borrowBook(@ModelAttribute("book") Book book){
        bookService.borrowBook(book);
        Book updateBook = bookService.findById(book.getId());
        ModelAndView modelAndView = new ModelAndView("/book/infor");
        modelAndView.addObject("book",updateBook);
        if (updateBook.getQuantity() == 0) modelAndView.addObject("message","No books for you, bitch !");
        else modelAndView.addObject("message","One book was borrowed !");
        return modelAndView;
    }

}


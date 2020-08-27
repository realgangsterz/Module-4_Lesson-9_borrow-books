package com.codegym.cms.service;

import com.codegym.cms.model.Guess;
import org.springframework.data.domain.Page;


public interface GuessService {
    Page<Guess> findAll();

    Guess findById(Long id);

    void save(Guess guess);

    void remove(Long id);
}

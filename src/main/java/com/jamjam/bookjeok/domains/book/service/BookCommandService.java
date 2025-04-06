package com.jamjam.bookjeok.domains.book.service;

import com.jamjam.bookjeok.domains.book.dto.BookDTO;
import com.jamjam.bookjeok.domains.book.entity.Book;
import com.jamjam.bookjeok.domains.book.repository.BookRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class BookCommandService {

    private final BookRepository bookRepository;
    private final ModelMapper modelMapper;

    @Transactional
    public void registBook(BookDTO book) {

        Book findBook = bookRepository.findBookByIsbn(book.getIsbn());

        if(findBook == null) {
            Book newBook = modelMapper.map(book, Book.class);
            bookRepository.save(newBook);
        }
    }
}

package com.jamjam.bookjeok.domains.book.service;

import com.jamjam.bookjeok.domains.book.dto.*;
import com.jamjam.bookjeok.domains.book.entity.*;
import com.jamjam.bookjeok.domains.book.repository.*;
import com.jamjam.bookjeok.exception.book.FileStorageException;
import lombok.RequiredArgsConstructor;
import org.apache.commons.lang3.StringEscapeUtils;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.io.*;
import java.net.URL;
import java.time.LocalDateTime;
import java.util.Optional;
import java.util.concurrent.CompletableFuture;

@Service
@Transactional
@RequiredArgsConstructor
public class BookApiServiceImpl implements BookApiService{

    private final BookRepository bookRepository;
    private final BookCategoryRepository bookCategoryRepository;
    private final PublisherRepository publisherRepository;
    private final AuthorRepository authorRepository;
    private final BookAuthorRepository bookAuthorRepository;
    private final ModelMapper modelMapper;

    public CompletableFuture<String> addTocAndCategoryName(BookApiDTO book) {

        return CompletableFuture.supplyAsync(() -> {

            try {
                Document doc = Jsoup.connect(book.getLink()).get();
                Elements tocElements = doc.select(".bookCatalogTop_category__MLd60");

                if (!tocElements.isEmpty()) {

                    String category = tocElements.last().text();
                    String categoryName = StringEscapeUtils.unescapeHtml4(category.trim());
                    if(categoryName.equals("정가제free")) return null;
                    else {
                        return categoryName;
                    }
                }
                return null;

            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        });
    }

    @Override
    @Transactional
    public Book registBook (BookDTO bookDTO) {

        Optional<Book> findBook = bookRepository.findBookByIsbn(bookDTO.getIsbn());
        Optional<Book> newBook = null;

        if(findBook.isEmpty()) {
            Book book = modelMapper.map(bookDTO, Book.class);
            bookRepository.save(book);
            newBook = bookRepository.findBookByIsbn(bookDTO.getIsbn());
            return newBook.get();
        }
        return null;
    }

    @Override
    @Transactional
    public BookCategory findCategoryByCategoryName(String categoryName) {

        Optional<BookCategory> findCategory = bookCategoryRepository.findCategoryByCategoryName(categoryName);
        Optional<BookCategory> newCategory = Optional.empty();

        if(findCategory.isEmpty()){
            BookCategoryDTO categoryDTO  =  new BookCategoryDTO(categoryName, LocalDateTime.now(), LocalDateTime.now(), false) ;
            BookCategory category = modelMapper.map(categoryDTO, BookCategory.class);
            bookCategoryRepository.save(category);
            newCategory = bookCategoryRepository.findCategoryByCategoryName(categoryName);
            return newCategory.orElseGet(null);
        }
        return findCategory.get();
    }

    @Override
    @Transactional
    public Publisher findPublisher(String publisherName) {

        Optional<Publisher> findPublisher = publisherRepository.findByPublisherName(publisherName);
        Optional<Publisher> newPublisher = Optional.empty();

        if(findPublisher.isEmpty()){
            PublisherDTO publisherDTO = new PublisherDTO(publisherName);
            Publisher publisher = modelMapper.map(publisherDTO, Publisher.class);
            publisherRepository.save(publisher);
            newPublisher = publisherRepository.findByPublisherName(publisherName);
            return newPublisher.orElseGet(null);
        }
        return findPublisher.get();
    }

    @Override
    @Transactional
    public void registAuthor(String[] authors, Long bookId) {

        for(String author : authors) {
            Optional<Author> findAuthor = authorRepository.findByAuthorName(author);
            Optional<Author> newAuthor = Optional.empty();
            Long authorId = 0L;

            if(findAuthor.isEmpty()){
                AuthorDTO authorDTO = new AuthorDTO(author);
                Author mapAuthor = modelMapper.map(authorDTO, Author.class);
                authorRepository.save(mapAuthor);
                newAuthor = authorRepository.findByAuthorName(author);
                authorId = newAuthor.get().getAuthorId();
            } else  {
                authorId = findAuthor.get().getAuthorId();
            }

            registBookAuthor(bookId, authorId);

        }
    }

    @Transactional
    public void registBookAuthor(Long bookId, Long authorId) {

        BookAuthorDTO bookAuthorDTO = new BookAuthorDTO(bookId, authorId);
        BookAuthorId bookAuthorId = modelMapper.map(bookAuthorDTO, BookAuthorId.class);
        BookAuthor bookAuthor = new BookAuthor(bookAuthorId);
        bookAuthorRepository.save(bookAuthor);

    }

    @Override
    public String saveFile(String imgUrl, String filePath){
        InputStream inputStream = null;
        FileOutputStream outputStream = null;
        String fileName = imgUrl.replace("https://shopping-phinf.pstatic.net/", "").replace("/", "");
        try {
            inputStream =  new URL(imgUrl).openStream();
            outputStream = new FileOutputStream(filePath + fileName);

            while(true) {
                int data = inputStream.read();

                if(data == -1) {
                    break;
                }
                outputStream.write(data);
            }

            inputStream.close();;
            outputStream.close();

        } catch (IOException e) {
            throw new FileStorageException("파일 저장을 실패했습니다.");
        }

        return filePath + fileName;

    }
}

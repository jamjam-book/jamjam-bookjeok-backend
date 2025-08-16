package com.jamjam.bookjeok.domains.book.command.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.jamjam.bookjeok.domains.book.command.dto.*;

import com.jamjam.bookjeok.domains.book.command.entity.*;
import com.jamjam.bookjeok.domains.book.command.repository.*;
import com.jamjam.bookjeok.domains.book.query.dto.*;
import com.jamjam.bookjeok.exception.book.FileStorageException;
import lombok.RequiredArgsConstructor;
import org.apache.commons.lang3.StringEscapeUtils;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.RequestEntity;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

import java.io.*;
import java.net.URI;
import java.net.URL;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Date;
import java.util.List;
import java.util.Optional;
import java.util.concurrent.CompletableFuture;

@Service
@Transactional
@RequiredArgsConstructor
public class BookApiServiceImpl implements BookApiService{

    @Value("${naver.book-client-api}")
    private String clientId;

    @Value("${naver.book-secret-key}")
    private String clientKey;

    @Value("${image.image-dir}")
    private String filePath;

    private final BookRepository bookRepository;
    private final BookCategoryRepository bookCategoryRepository;
    private final PublisherRepository publisherRepository;
    private final AuthorRepository authorRepository;
    private final BookAuthorRepository bookAuthorRepository;
    private final ModelMapper modelMapper;

    @Override
    public Book getBookByIsbn(String isbn) {
        ResponseEntity<String> exchangeResponse = isbnExchange(isbn);

        List<BookApiDTO> apiBooks = parseJson(exchangeResponse);

        return maptoBooksDto(apiBooks);
    }

    private ResponseEntity<String> isbnExchange(String isbn) {
        URI uri = UriComponentsBuilder
                .fromUriString("https://openapi.naver.com")
                .path("/v1/search/book_adv.json")
                .queryParam("d_isbn", isbn)
                .encode()
                .build()
                .toUri();

        RequestEntity<Void> request = RequestEntity
                .get(uri)
                .header("X-Naver-Client-Id", clientId)
                .header("X-Naver-Client-Secret", clientKey)
                .build();

        return new RestTemplate().exchange(request, String.class);
    }

    private List<BookApiDTO> parseJson(ResponseEntity<String> response) {

        ObjectMapper om = new ObjectMapper();
        NaverResultDTO resultDTO = new NaverResultDTO();
        System.out.println(response.getBody());

        try {
            resultDTO = om.readValue(response.getBody(), NaverResultDTO.class);
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }

        return resultDTO.getItems();

    }

    private Book maptoBooksDto(List<BookApiDTO> apiBooks) {

        BookDTO bookDTO;
        Book book;
        for(BookApiDTO api : apiBooks) {

            String bookName = api.getTitle();

            if(api.getPublisher() == null) continue;
            Publisher publisher = findPublisher(api.getPublisher());

            if(api.getPubdate() == null) continue;;
            LocalDate pubdate = formattingDate(api.getPubdate());

            if (api.getIsbn().isEmpty()) continue;
            String isbn = api.getIsbn();
            //BookApiDTO apiDTO = getBookByIsbn(isbn);
            CompletableFuture<String> categoryName = addTocAndCategoryName(api);
            String categoryNameStr = categoryName.join(); // or get() with exception handling
            if(categoryNameStr == null) continue;
            BookCategory bookCategory = findCategoryByCategoryName(categoryNameStr);

            if(api.getAuthor() == null) continue;
            String[] authors = api.getAuthor().split("\\^");

            int price = api.getDiscount().isEmpty() ? 0 : Integer.parseInt(api.getDiscount());
            int stockQuantity  = price != 0 ? 100 : 0;

            String url = saveFile(api.getImage());

            String bookInfo = api.getDescription();

            bookDTO = new BookDTO(publisher.getPublisherId(), bookCategory.getCategoryId(), bookName, bookInfo, isbn, pubdate, price, stockQuantity, url);

            book = registBook(bookDTO);

            if(book != null) {
                registAuthor(authors, book.getBookId());
                return book;
            }

        }

        return null;
    }

    private CompletableFuture<String> addTocAndCategoryName(BookApiDTO book) {

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

    private Book registBook (BookDTO bookDTO) {

        Optional<Book> findBook = bookRepository.findBookByIsbn(bookDTO.getIsbn());
        Optional<Book> newBook;

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

        return findCategory.orElseGet(() -> saveCategory(categoryName));
    }

    private BookCategory saveCategory(String categoryName) {

        Optional<BookCategory> newCategory = Optional.empty();

        BookCategoryDTO categoryDTO  =  new BookCategoryDTO(categoryName, LocalDateTime.now(), null, false) ;
        BookCategory category = modelMapper.map(categoryDTO, BookCategory.class);

        bookCategoryRepository.save(category);

        newCategory = bookCategoryRepository.findCategoryByCategoryName(categoryName);

        return newCategory.orElseGet(null);

    }

    @Override
    @Transactional
    public Publisher findPublisher(String publisherName) {

        Optional<Publisher> findPublisher = publisherRepository.findByPublisherName(publisherName);

        return findPublisher.orElseGet(() -> savePublisher(publisherName));
    }

    private Publisher savePublisher(String publisherName) {

        Optional<Publisher> newPublisher = Optional.empty();

        PublisherDTO publisherDTO = new PublisherDTO(publisherName);
        Publisher publisher = modelMapper.map(publisherDTO, Publisher.class);

        publisherRepository.save(publisher);

        newPublisher = publisherRepository.findByPublisherName(publisherName);

        return newPublisher.orElseGet(null);

    }

    @Override
    @Transactional
    public void registAuthor(String[] authors, Long bookId) {

        for(String author : authors) {
            Optional<Author> findAuthor = authorRepository.findByAuthorName(author);

            Long authorId = 0L;

            if(findAuthor.isEmpty()){

                authorId = saveAuthor(author);

            } else  {
                authorId = findAuthor.get().getAuthorId();
            }

            registBookAuthor(bookId, authorId);

        }
    }

    private Long saveAuthor(String author) {

        Optional<Author> newAuthor = Optional.empty();

        AuthorDTO authorDTO = new AuthorDTO(author);
        Author mapAuthor = modelMapper.map(authorDTO, Author.class);

        authorRepository.save(mapAuthor);

        newAuthor = authorRepository.findByAuthorName(author);

        return newAuthor.get().getAuthorId();

    }

    @Override
    @Transactional
    public void registBookAuthor(Long bookId, Long authorId) {

        BookAuthorDTO bookAuthorDTO = new BookAuthorDTO(bookId, authorId);
        BookAuthorId bookAuthorId = modelMapper.map(bookAuthorDTO, BookAuthorId.class);
        BookAuthor bookAuthor = new BookAuthor(bookAuthorId);
        bookAuthorRepository.save(bookAuthor);

    }

    private String saveFile(String imgUrl){
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

        return fileName;

    }

    private LocalDate formattingDate(String pubdate) {
        SimpleDateFormat formatter1 = new SimpleDateFormat("yyyyMMdd");
        SimpleDateFormat formatter2 = new SimpleDateFormat("yyyy-MM-dd");

        try {
            Date formatDate = formatter1.parse(pubdate);
            String newDate = formatter2.format(formatDate);
            return LocalDate.parse(newDate);
        } catch (ParseException e) {
            throw new RuntimeException(e);
        }
    }

}

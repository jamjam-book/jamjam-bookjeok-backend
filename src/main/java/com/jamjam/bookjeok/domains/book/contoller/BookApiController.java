package com.jamjam.bookjeok.domains.book.contoller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.jamjam.bookjeok.common.dto.ApiResponse;
import com.jamjam.bookjeok.domains.book.dto.BookApiDTO;
import com.jamjam.bookjeok.domains.book.dto.BookDTO;
import com.jamjam.bookjeok.domains.book.dto.NaverResultDTO;
import com.jamjam.bookjeok.domains.book.service.BookApiService;
import com.jamjam.bookjeok.domains.book.service.BookCommandService;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.RequestEntity;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

import java.net.URI;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.concurrent.CompletableFuture;

@RestController
@RequestMapping("/api/v1")
public class BookApiController {

    private String clientId;
    private String clientSecret;
    private BookCommandService bookCommandService;
    private BookApiService bookApiService;

     public BookApiController (
             @Value("${naver.book-client-api}") String clientId,
             @Value("${naver.book-secret-key}") String clientSecret,
             BookApiService bookApiService,
             BookCommandService bookCommandService) {
        this.clientId = clientId;
        this.clientSecret = clientSecret;
        this.bookApiService = bookApiService;
        this.bookCommandService = bookCommandService;
    }

    @PostMapping("/bookApi/regist")
    public ResponseEntity<ApiResponse<Void>> registBook(@RequestParam(value = "keyword") String keyword) {

        ResponseEntity<String> exchangeResponse = exchange(keyword);

        List<BookApiDTO> apiBooks = parseJson(exchangeResponse);

        List<BookDTO> bookList = mapDto(apiBooks);

        bookList.forEach(System.out::println);

        /*for (BookDTO book : bookList) {
           bookCommandService.registBook(book);
        }*/

        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(ApiResponse.success(null));
    }

    public BookApiDTO getBookByIsbn(String isbn) {

        ResponseEntity<String> exchangeResponse = isbnExchange(isbn);

        List<BookApiDTO> apiBooks = parseJson(exchangeResponse);

        return apiBooks.get(0);

    }

    private ResponseEntity<String> exchange(String keyword) {

        URI uri = UriComponentsBuilder
                .fromUriString("https://openapi.naver.com")
                .path("/v1/search/book.json")
                .queryParam("query", keyword)
                .queryParam("display", 20)
                .queryParam("start", 1)
                .queryParam("sort", "sim")
                .encode()
                .build()
                .toUri();

        RequestEntity<Void> request = RequestEntity
                .get(uri)
                .header("X-Naver-Client-Id", clientId)
                .header("X-Naver-Client-Secret", clientSecret)
                .build();

        return new RestTemplate().exchange(request, String.class);

    }

    private List<BookApiDTO> parseJson(ResponseEntity<String> response) {

        ObjectMapper om = new ObjectMapper();
        NaverResultDTO resultDTO = new NaverResultDTO();

        try {
            resultDTO = om.readValue(response.getBody(), NaverResultDTO.class);
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }

        return resultDTO.getItems();
    }

    private List<BookDTO> mapDto(List<BookApiDTO> apiBooks) {

        List<BookDTO> bookList = new ArrayList<>();

        for(BookApiDTO api : apiBooks) {

            BookDTO book = new BookDTO();
            book.setAuthorName(api.getAuthor());
            book.setPublisherName(api.getPublisher());
            LocalDateTime pubdate = formattingDate(api.getPubdate());
            book.setPublishedAt(pubdate);

            if (api.getIsbn().isEmpty()) continue;
            BookApiDTO apiDTO = getBookByIsbn(api.getIsbn());
            CompletableFuture<BookApiDTO> newBook = bookApiService.addTocAndCategoryName(apiDTO);
            if(newBook.join().getCategoryName() == null) continue;
            book.setCategoryName(newBook.join().getCategoryName());

            String price = api.getDiscount();
            book.setPrice((price.isEmpty() ? 0 : Integer.parseInt(api.getDiscount())));
            book.setStockQuantity(price.isEmpty() ? 0 : 100);

            book.setImageUrl(api.getImage());
            book.setIsbn(api.getIsbn());

            bookList.add(book);
        }

        return bookList;

    }

    private LocalDateTime formattingDate(String pubdate) {

        SimpleDateFormat formatter1 = new SimpleDateFormat("yyyyMMdd");
        SimpleDateFormat formatter2 = new SimpleDateFormat("yyyy-MM-dd");

        try {
            Date formatDate = formatter1.parse(pubdate);
            String newDate = formatter2.format(formatDate);
            return LocalDate.parse(newDate).atStartOfDay();
        } catch (ParseException e) {
            throw new RuntimeException(e);
        }
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
                .header("X-Naver-Client-Secret", clientSecret)
                .build();

        return new RestTemplate().exchange(request, String.class);
    }
}

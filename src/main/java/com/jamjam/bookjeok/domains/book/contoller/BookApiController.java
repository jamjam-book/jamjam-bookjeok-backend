package com.jamjam.bookjeok.domains.book.contoller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.jamjam.bookjeok.common.dto.ApiResponse;
import com.jamjam.bookjeok.domains.book.dto.BookApiDTO;
import com.jamjam.bookjeok.domains.book.dto.BookDTO;
import com.jamjam.bookjeok.domains.book.dto.NaverResultDTO;
import com.jamjam.bookjeok.domains.book.entity.Book;
import com.jamjam.bookjeok.domains.book.entity.BookCategory;
import com.jamjam.bookjeok.domains.book.entity.Publisher;
import com.jamjam.bookjeok.domains.book.service.BookApiService;
import com.jamjam.bookjeok.domains.book.service.BookAdminService;
import lombok.RequiredArgsConstructor;
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
import java.util.Date;
import java.util.List;
import java.util.concurrent.CompletableFuture;

@RestController
@RequestMapping("/api/v1")
@RequiredArgsConstructor
public class BookApiController {

    private final BookAdminService bookAdminService;
    private final BookApiService bookApiService;

    @Value("${naver.book-client-api}")
    private String clientId;

    @Value("${naver.book-secret-key}")
    private String clientKey;

    @Value("${image.image-dir}")
    private String filepath;

    @PostMapping("/bookapi/regist")
    public ResponseEntity<ApiResponse<Void>> registBook(@RequestParam(value = "keyword") String keyword) {

        ResponseEntity<String> exchangeResponse = exchange(keyword);

        List<BookApiDTO> apiBooks = parseJson(exchangeResponse);
        maptoBooksDto(apiBooks);

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
                .queryParam("display", 10)
                .queryParam("start", 1)
                .queryParam("sort", "sim")
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

    private void maptoBooksDto(List<BookApiDTO> apiBooks) {

        for(BookApiDTO api : apiBooks) {

            String bookName = api.getTitle();

            if(api.getPublisher() == null) continue;
            Publisher publisher = bookApiService.findPublisher(api.getPublisher());

            if(api.getPubdate() == null) continue;;
            LocalDate pubdate = formattingDate(api.getPubdate());

            if (api.getIsbn().isEmpty()) continue;
            String isbn = api.getIsbn();
            BookApiDTO apiDTO = getBookByIsbn(isbn);
            CompletableFuture<String> categoryName = bookApiService.addTocAndCategoryName(apiDTO);
            String categoryNameStr = categoryName.join(); // or get() with exception handling
            if(categoryNameStr == null) continue;
            BookCategory bookCategory = bookApiService.findCategoryByCategoryName(categoryNameStr);

            if(api.getAuthor() == null) continue;
            String[] authors = api.getAuthor().split("\\^");

            int price = api.getDiscount().isEmpty() ? 0 : Integer.parseInt(api.getDiscount());
            int stockQuantity  = price != 0 ? 100 : 0;

            String url = bookApiService.saveFile(api.getImage(), filepath);

            String bookInfo = api.getDescription();

            BookDTO bookDTO = new BookDTO(publisher.getPublisherId(), bookCategory.getCategoryId(), bookName, bookInfo, isbn, pubdate, price, stockQuantity, url);

            Book book = bookApiService.registBook(bookDTO);

            if(book != null) {
                bookApiService.registAuthor(authors, book.getBookId());
            }
        }

        apiBooks.forEach(System.out::println);

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
}

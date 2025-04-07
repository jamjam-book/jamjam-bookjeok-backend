package com.jamjam.bookjeok.domains.book.service;

import com.jamjam.bookjeok.domains.book.dto.BookApiDTO;
import org.apache.commons.lang3.StringEscapeUtils;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.concurrent.CompletableFuture;

@Service
public class BookApiService {
    public CompletableFuture<BookApiDTO> addTocAndCategoryName(BookApiDTO book) {

        return CompletableFuture.supplyAsync(() -> {
            try {
                Document doc = Jsoup.connect(book.getLink()).get();
                Elements tocElements = doc.select(".bookCatalogTop_category__MLd60");

                if (!tocElements.isEmpty()) {
                    book.setToc(tocElements.html());
                    String category = tocElements.last().text();
                    String categoryName = StringEscapeUtils.unescapeHtml4(category.trim());
                    if(categoryName.equals("정가제free")) book.setCategoryName(null);
                    else {
                        book.setCategoryName(categoryName);
                    }
                }
                return book;
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        });
    }
}

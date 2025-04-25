package com.jamjam.bookjeok.domains.book.command.service;

import com.jamjam.bookjeok.domains.book.command.entity.Book;
import com.jamjam.bookjeok.domains.book.command.repository.BookRepository;
import com.jamjam.bookjeok.domains.pendingorder.command.dto.request.PendingOrderBookItemsRequest;
import com.jamjam.bookjeok.domains.pendingorder.command.entity.PendingOrder;
import com.jamjam.bookjeok.exception.payment.BookInfoNotFoundException;
import com.jamjam.bookjeok.exception.payment.InsufficientBookStockException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;

/**
 * 도서 재고 처리를 담당하는 서비스 클래스입니다.
 */
@Service
@Transactional
@RequiredArgsConstructor
public class BookStockCommandServiceImpl implements BookStockCommandService {

    private final BookRepository bookRepository;

    /**
     * 보류 중인 주문에 대해 도서 재고를 검증하고 수량을 차감합니다.
     * 주문된 각 도서에 대해 재고가 충분한지 확인하고, 재고를 차감합니다.
     *
     * @param pendingOrder 보류 중인 주문 정보
     * @return 주문에 포함된 도서 항목 리스트
     * @throws BookInfoNotFoundException 도서 정보를 찾을 수 없는 경우
     * @throws InsufficientBookStockException 도서 재고가 부족한 경우
     */
    @Override
    public List<PendingOrderBookItemsRequest> validateAndUpdateBookStocks(PendingOrder pendingOrder) {
        List<PendingOrderBookItemsRequest> orderDataList = pendingOrder.getOrderData();

        orderDataList.forEach(orderData -> {
            Book book = findBookById(orderData.bookId());
            int newStockQuantity = calculateNewStockQuantity(book, orderData.quantity());
            updateBookStock(book, newStockQuantity);
        });
        
        return orderDataList;
    }

    /**
     * 도서 ID로 도서 정보를 조회합니다.
     *
     * @param bookId 도서 ID
     * @return 도서 엔티티
     * @throws BookInfoNotFoundException 도서 정보를 찾을 수 없는 경우
     */
    private Book findBookById(Long bookId) {
        return bookRepository.findBookByBookId(bookId)
                .orElseThrow(() -> new BookInfoNotFoundException("도서 정보를 찾을 수 없습니다."));
    }

    /**
     * 주문 수량을 차감한 후의 재고 수량을 계산합니다.
     *
     * @param book 도서 엔티티
     * @param orderQuantity 주문 수량
     * @return 차감된 이후의 재고 수량
     * @throws InsufficientBookStockException 도서 재고가 부족한 경우
     */
    private int calculateNewStockQuantity(Book book, int orderQuantity) {
        int newStockQuantity = book.getStockQuantity() - orderQuantity;
        
        if (newStockQuantity < 0) {
            throw new InsufficientBookStockException("도서 재고 수량이 부족하여 결제를 진행할 수 없습니다.");
        }
        return newStockQuantity;
    }

    /**
     * 도서의 재고 수량을 업데이트합니다.
     *
     * @param book 도서 엔티티
     * @param newStockQuantity 업데이트할 재고 수량
     */
    private void updateBookStock(Book book, int newStockQuantity) {
        book.updateStockQuantity(newStockQuantity, LocalDateTime.now().withNano(0));
    }

}
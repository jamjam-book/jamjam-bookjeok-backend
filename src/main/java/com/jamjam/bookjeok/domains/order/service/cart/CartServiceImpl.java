package com.jamjam.bookjeok.domains.order.service.cart;

import com.jamjam.bookjeok.domains.book.entity.Book;
import com.jamjam.bookjeok.domains.order.dto.cart.response.CartBookListResponse;
import com.jamjam.bookjeok.domains.order.dto.cart.response.CartBookResponse;
import com.jamjam.bookjeok.domains.order.dto.cart.response.CartResponse;
import com.jamjam.bookjeok.domains.order.dto.cart.request.CartRequest;
import com.jamjam.bookjeok.domains.order.entity.Cart;
import com.jamjam.bookjeok.domains.order.repository.cart.CartRepository;
import com.jamjam.bookjeok.domains.order.repository.cart.mapper.CartMapper;
import com.jamjam.bookjeok.exception.order.cart.CartBookNotFoundException;
import com.jamjam.bookjeok.exception.order.cart.CartItemLimitExceededException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import static com.jamjam.bookjeok.domains.order.entity.Cart.calculateBookTotalPrice;
import static com.jamjam.bookjeok.domains.order.entity.Cart.validateCartItemLimit;

@Slf4j
@Service
@Transactional
@RequiredArgsConstructor
public class CartServiceImpl implements CartService {

    private final CartMapper cartMapper;
    private final CartRepository cartRepository;

    @Override
    public CartBookListResponse getBooksInCart(Long memberUid) {
        List<CartBookResponse> bookList = cartMapper.findCartBooksByMemberUid(memberUid);
        return new CartBookListResponse(bookList);
    }

    @Override
    public CartResponse createBookToCart(CartRequest cartRequest) {
        // books 테이블에 존재하는 정보인지 검증하는 로직
        Book findBook = findBookOrThrow(cartRequest.bookId(), cartRequest.bookName());

        // 장바구니에 동일한 도서명이 있는지 검증하는 로직
        Optional<Cart> findCart = cartMapper.findCartByMemberUidAndBookId(cartRequest.memberUid(), cartRequest.bookId());

        if (findCart.isPresent()) { // 장바구니에 동일한 도서가 있다면?
            Cart cart = findCart.get();
            int bookQuantity = cartRequest.quantity() + cart.getQuantity();
            cart.updateQuantity(bookQuantity); // 해당 도서에서 개수를 증가시킨다.
            int totalPrice = calculateBookTotalPrice(cart.getQuantity(), findBook.getPrice()); // 총 금액을 구한다.

            return toCartResponse(cart, findBook, totalPrice); // 응답 객체에 넣고 반환한다.
        } else { // 장바구니에 동일한 도서명이 없다면?
            int cartCount = cartMapper.findCartCountByMemberUid(cartRequest.memberUid()); // 장바구니에 있는 도서 정보 개수를 파악
            validateCartItemLimit(cartCount); // 장바구니에 도서 정보가 20개 이상인지 검증

            // 검증이 완료되었으면 Cart 엔티티 생성
            Cart cart = createCartEntity(cartRequest.memberUid(), findBook.getBookId(), cartRequest.quantity());
            Cart savedCart = cartRepository.save(cart); // 엔티티 영속성 컨텍스트에 저장

            int totalPrice = calculateBookTotalPrice(savedCart.getQuantity(), findBook.getPrice()); // 총 금액을 구한다.

            return toCartResponse(savedCart, findBook, totalPrice); // 응답 객체에 넣고 반환한다.
        }
    }

    @Override
    public CartResponse modifyBookQuantity(CartRequest cartRequest) {
        // books 테이블에 존재하는 정보인지 검증하는 로직
        Book findBook = findBookOrThrow(cartRequest.bookId(), cartRequest.bookName());

        // 장바구니에 도서 정보가 있는지 확인한다.
        Cart findCart = findCartOrThrow(cartRequest.memberUid(), cartRequest.bookId());

        // 장바구니에 도서 정보가 있는 경우에만 수량을 변경할 수 있다.
        findCart.updateQuantity(cartRequest.quantity());

        // 변경된 수량과 도서 가격으로 결제 예정 금액을 구한다.
        int totalPrice = calculateBookTotalPrice(findCart.getQuantity(), findBook.getPrice());

        return toCartResponse(findCart, findBook, totalPrice); // 응답 객체에 넣고 반환한다.
    }

    @Override
    public void deleteBookFromCartByMemberId(CartRequest cartRequest) {
        // 장바구니에 삭제할 도서 정보가 있는지 조회한다.
        Cart findCart = findCartOrThrow(cartRequest.memberUid(), cartRequest.bookId());

        // 삭제할 도서 정보가 있으면 삭제한다.
        cartRepository.delete(findCart);
    }

    private Book findBookOrThrow(Long bookId, String bookName) {
        return cartMapper.findByBookIdAndBookName(bookId, bookName)
                .orElseThrow(() -> new CartItemLimitExceededException("존재하지 않는 책 정보 입니다."));
    }

    private Cart findCartOrThrow(Long memberUid, Long bookId) {
        return cartMapper.findCartByMemberUidAndBookId(memberUid, bookId)
                .orElseThrow(() -> new CartBookNotFoundException("장바구니에 해당 도서 정보가 없습니다."));
    }

    private Cart createCartEntity(Long memberUid, Long bookId, int quantity) {
        return Cart.builder()
                .memberUid(memberUid)
                .bookId(bookId)
                .quantity(quantity)
                .createdAt(LocalDateTime.now().withNano(0))
                .build();
    }

    private CartResponse toCartResponse(Cart cart, Book book, int totalPrice) {
        return CartResponse.builder()
                .memberUid(cart.getMemberUid())
                .bookId(cart.getBookId())
                .bookName(book.getBookName())
                .quantity(cart.getQuantity())
                .totalPrice(totalPrice)
                .build();
    }

}
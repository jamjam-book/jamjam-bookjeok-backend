package com.jamjam.bookjeok.domains.cart.service;

import com.jamjam.bookjeok.domains.cart.dto.response.CartBookListResponse;
import com.jamjam.bookjeok.domains.cart.dto.response.CartResponse;
import com.jamjam.bookjeok.domains.cart.dto.request.CartRequest;

public interface CartService {

    CartResponse createBookToCart(CartRequest cartRequest);

    CartResponse modifyBookQuantity(CartRequest cartRequest);

    void deleteBookFromCartByMemberId(CartRequest cartRequest);

    CartBookListResponse getBooksInCart(Long memberUid);

}
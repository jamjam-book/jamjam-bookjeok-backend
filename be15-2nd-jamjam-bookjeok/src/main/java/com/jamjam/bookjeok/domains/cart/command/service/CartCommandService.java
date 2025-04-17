package com.jamjam.bookjeok.domains.cart.command.service;

import com.jamjam.bookjeok.domains.cart.command.dto.request.CartRequest;
import com.jamjam.bookjeok.domains.cart.command.dto.response.CartResponse;

public interface CartCommandService {

    CartResponse createBookToCart(CartRequest cartRequest);

    CartResponse modifyBookQuantity(CartRequest cartRequest);

    void deleteBookFromCartByMemberId(CartRequest cartRequest);

}
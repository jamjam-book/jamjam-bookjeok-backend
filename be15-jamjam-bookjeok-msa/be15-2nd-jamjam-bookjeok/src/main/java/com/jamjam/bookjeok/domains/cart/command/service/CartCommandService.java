package com.jamjam.bookjeok.domains.cart.command.service;

import com.jamjam.bookjeok.domains.cart.command.dto.request.CartRequest;
import com.jamjam.bookjeok.domains.cart.command.dto.response.CartResponse;

public interface CartCommandService {

    CartResponse createBookToCart(Long memberUid, CartRequest cartRequest);

    CartResponse modifyBookQuantity(Long memberUid, CartRequest cartRequest);

    void deleteBookFromCartByMemberId(Long memberUid, CartRequest cartRequest);

}
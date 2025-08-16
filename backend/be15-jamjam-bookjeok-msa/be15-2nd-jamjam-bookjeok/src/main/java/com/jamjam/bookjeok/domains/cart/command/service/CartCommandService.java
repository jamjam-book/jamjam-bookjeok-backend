package com.jamjam.bookjeok.domains.cart.command.service;

import com.jamjam.bookjeok.domains.cart.command.dto.request.CartRequest;
import com.jamjam.bookjeok.domains.cart.command.dto.response.CartResponse;
import com.jamjam.bookjeok.domains.orderdetail.query.dto.OrderDetailBookDTO;

import java.util.List;

public interface CartCommandService {

    CartResponse createBookToCart(Long memberUid, CartRequest cartRequest);

    CartResponse modifyBookQuantity(Long memberUid, CartRequest cartRequest);

    void deleteBookFromCartByMemberId(Long memberUid, CartRequest cartRequest);

    void completePaymentAndRemoveFromCart(List<OrderDetailBookDTO> books, Long memberUid);

}
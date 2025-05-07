package com.jamjam.bookjeok.domains.cart.query.service;

import com.jamjam.bookjeok.domains.cart.query.dto.response.CartBookListResponse;
import com.jamjam.bookjeok.domains.cart.query.dto.response.CartCountResponse;

public interface CartQueryService {

    CartBookListResponse getBooksInCart(Long memberUid);

    CartCountResponse getCartCount(Long memberUid);

}
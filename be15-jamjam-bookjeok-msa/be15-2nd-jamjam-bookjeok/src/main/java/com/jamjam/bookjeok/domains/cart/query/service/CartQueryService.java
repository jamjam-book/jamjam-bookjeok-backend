package com.jamjam.bookjeok.domains.cart.query.service;

import com.jamjam.bookjeok.domains.cart.query.dto.response.CartBookListResponse;

public interface CartQueryService {

    CartBookListResponse getBooksInCart(Long memberUid);

}
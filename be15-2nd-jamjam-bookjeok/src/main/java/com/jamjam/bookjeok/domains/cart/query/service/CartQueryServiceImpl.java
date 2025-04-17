package com.jamjam.bookjeok.domains.cart.query.service;

import com.jamjam.bookjeok.domains.cart.query.dto.response.CartBookListResponse;
import com.jamjam.bookjeok.domains.cart.query.dto.response.CartBookResponse;
import com.jamjam.bookjeok.domains.cart.query.mapper.CartMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Slf4j
@Service
@Transactional
@RequiredArgsConstructor
public class CartQueryServiceImpl implements CartQueryService {

    private final CartMapper cartMapper;

    @Override
    public CartBookListResponse getBooksInCart(Long memberUid) {
        List<CartBookResponse> bookList = cartMapper.findCartBooksByMemberUid(memberUid);
        return new CartBookListResponse(bookList);
    }

}
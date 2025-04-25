package com.jamjam.bookjeok.domains.book.command.service;

import com.jamjam.bookjeok.domains.pendingorder.command.dto.request.PendingOrderBookItemsRequest;
import com.jamjam.bookjeok.domains.pendingorder.command.entity.PendingOrder;

import java.util.List;

public interface BookStockCommandService {

    List<PendingOrderBookItemsRequest> validateAndUpdateBookStocks(PendingOrder pendingOrder);

}
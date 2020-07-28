package ru.geekbrains.service;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import ru.geekbrains.service.model.StockInfo;

@FeignClient(value = "stock-service", url = "http://localhost:8080/stock")
public interface StockService {

    @GetMapping(value = "/{productId}")
    StockInfo getStockInfo(@PathVariable("productId") Long productId);
}

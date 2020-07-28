package ru.geekbrains.resource;

import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Random;

@RestController
public class StockResource {

    @RequestMapping("/{productId}")
    public StockInfo getStockInfo(@PathVariable("productId") Long productId) {
        return new StockInfo(productId, new Random().nextInt(100) + 10);
    }
}

package ru.geekbrains.service.model;

public class StockInfo {

    private Long productId;

    private Integer count;

    public StockInfo() {
    }

    public StockInfo(Long productId, Integer count) {
        this.productId = productId;
        this.count = count;
    }

    public Long getProductId() {
        return productId;
    }

    public void setProductId(Long productId) {
        this.productId = productId;
    }

    public Integer getCount() {
        return count;
    }

    public void setCount(Integer count) {
        this.count = count;
    }
}

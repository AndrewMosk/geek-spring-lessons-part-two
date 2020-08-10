package ru.geekbrains.service;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import ru.geekbrains.controller.repr.ProductRepr;
import ru.geekbrains.service.model.LineItem;

import java.math.BigDecimal;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

public class CartServiceTest {

    private CartService cartService;

    @BeforeEach
    public void init() {
        cartService = new CartServiceImpl();
    }

    @Test
    public void testEmptyCart() {
        emptyCart();
    }

    @Test
    public void testAddOneProduct() {
        ProductRepr expectedProduct = generateProduct(1L, "Product name", new BigDecimal(100));

        cartService.addProductQty(expectedProduct, 1);
        checkCart(expectedProduct, 1, 1);

    }

    @Test
    public void testAddSeveralProducts() {
        ProductRepr expectedProduct1 = generateProduct(1L, "Product name1", new BigDecimal(100));

        cartService.addProductQty(expectedProduct1, 1);
        checkCart(expectedProduct1, 1, 1);

        ProductRepr expectedProduct2 = generateProduct(2L, "Product name2", new BigDecimal(200));
        cartService.addProductQty(expectedProduct2, 2);
        checkCart(expectedProduct2, 2, 2);

        ProductRepr expectedProduct3 = generateProduct(3L, "Product name3", new BigDecimal(300));
        cartService.addProductQty(expectedProduct3, 3);
        checkCart(expectedProduct3, 3, 3);

    }

    @Test
    public void testReduceProduct() {
        ProductRepr expectedProduct = generateProduct(1L, "Product name", new BigDecimal(100));

        cartService.addProductQty(expectedProduct, 2); // добавляю два продукта в корзину (в один элемент корзины)
        checkCart(expectedProduct, 2, 1); // проверяю размер корзины 1, добавлено 2 одинаковых товара

        cartService.removeProductQty(expectedProduct, 1); // удаляю 1 из двух товаров
        checkCart(expectedProduct, 1, 1); // размер корины должен остаться 1 и количество товара тоже 1
    }

    @Test
    public void testRemoveProducts() {
        ProductRepr expectedProduct = generateProduct(1L, "Product name", new BigDecimal(100));

        cartService.addProductQty(expectedProduct, 2); // добавляю два продукта в корзину (в один элемент корзины)
        checkCart(expectedProduct, 2, 1); // проверяю размер корзины 1, добавлено 2 одинаковых товара

        cartService.removeProduct(new LineItem(expectedProduct));
        emptyCart();
    }

    private void checkCart(ProductRepr expectedProduct, int expectedQty, int expectedCartSize) {
        List<LineItem> lineItems = cartService.getLineItems();
        assertNotNull(lineItems);

        assertTrue(lineItems.contains(new LineItem(expectedProduct))); // проверка наличия в корзине добавленного товара (LineItem)
        int index = lineItems.indexOf(new LineItem(expectedProduct)); // поиск индекса товара (LineItem)

        assertEquals(expectedCartSize, lineItems.size()); // проверка количества элементов в корзине

        LineItem lineItem = lineItems.get(index);
        assertEquals(expectedQty, lineItem.getQty()); // проверка количества выбранного товара в строке корзины

        // проверка товара и его полей
        assertEquals(expectedProduct.getId(), lineItem.getProductId());
        assertNotNull(lineItem.getProductRepr());
        assertEquals(expectedProduct.getId(), lineItem.getProductRepr().getId());
        assertEquals(expectedProduct.getName(), lineItem.getProductRepr().getName());


    }

    private void emptyCart() {
        assertEquals(0, cartService.getLineItems().size());
        assertEquals(BigDecimal.ZERO, cartService.getSubTotal());
    }

    private ProductRepr generateProduct(Long id, String name, BigDecimal cost) {
        ProductRepr product = new ProductRepr();
        product.setId(id);
        product.setCost(cost);
        product.setName(name);

        return product;
    }
}

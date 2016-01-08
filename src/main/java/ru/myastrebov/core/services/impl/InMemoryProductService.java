package ru.myastrebov.core.services.impl;

import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import org.springframework.stereotype.Component;
import ru.myastrebov.core.Product;
import ru.myastrebov.core.services.ProductService;

import javax.annotation.PostConstruct;
import java.util.List;
import java.util.Map;
import java.util.concurrent.atomic.AtomicLong;

/**
 * Простейшая реализация серввис для работы с продуктами
 * Объекты сохраняются в памяти.
 *
 * В идеале добавить repository-layer
 * @author Maxim
 */
@Component
public class InMemoryProductService implements ProductService {
    private AtomicLong idAtomicLong = new AtomicLong(1L);
    private final Map<Long, Product> productMap = Maps.newConcurrentMap();

    @Override
    public List<Product> getAllProducts() {
        return Lists.newArrayList(productMap.values());
    }

    @Override
    public Product createProduct(Product product) {
        return putNewProduct(product);
    }

    @Override
    public Product findById(Long productId) {
        return productMap.get(productId);
    }

    @Override
    public Product updateProduct(Long productId, Product productForUpdate) {
        assertExist(productId);
        return productMap.put(productId, productForUpdate);
    }

    @Override
    public void delete(Long productId) {
        assertExist(productId);
        productMap.keySet().remove(productId);
    }

    @PostConstruct
    protected void postConstruct() {
        putNewProduct("tee", 15L);
        putNewProduct("coffee", 20L);
        putNewProduct("prod", 30L);
    }

    private Product putNewProduct(Product product) {
        return putNewProduct(product.getProductName(), product.getId());
    }

    private Product putNewProduct(String name, Long cost) {
        long id = idAtomicLong.getAndIncrement();
        return productMap.put(id, new Product(id, name, cost));
    }

    private void assertExist(Long productId) {
        Product product = productMap.get(productId);
        if (product == null) {
            throw new RuntimeException("Product not found");
        }
    }
}

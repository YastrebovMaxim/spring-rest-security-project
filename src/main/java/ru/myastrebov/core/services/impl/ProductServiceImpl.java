package ru.myastrebov.core.services.impl;

import com.google.common.collect.Lists;
import org.springframework.stereotype.Component;
import ru.myastrebov.core.Product;
import ru.myastrebov.core.services.ProductService;

import java.util.List;

/**
 * @author Maxim
 */
@Component
public class ProductServiceImpl implements ProductService {
    @Override
    public List<Product> getAllProducts() {
        return Lists.newArrayList();
    }

    @Override
    public Product createProduct(Product product) {
        return null;
    }

    @Override
    public Product findById(Long productId) {
        return null;
    }

    @Override
    public Product updateProduct(Long productId, Product productForUpdate) {
        return null;
    }

    @Override
    public void delete(Long productId) {

    }
}

package ru.myastrebov.rest.resources;

import org.springframework.hateoas.ResourceSupport;
import ru.myastrebov.core.Product;

/**
 * @author Maxim
 */
public class ProductResource extends ResourceSupport {

    private String productName;

    private Long cost;

    public Product toProduct() {
        return new Product(productName, cost);
    }

    public String getProductName() {
        return productName;
    }

    public void setProductName(String productName) {
        this.productName = productName;
    }

    public Long getCost() {
        return cost;
    }

    public void setCost(Long cost) {
        this.cost = cost;
    }
}

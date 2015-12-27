package ru.myastrebov.core;

/**
 * @author Maxim
 */
public class Product {
    private Long id;
    private String productName;
    private Long cost;

    public Product() {
    }

    public Product(Long id, String productName, Long cost) {
        this.id = id;
        this.productName = productName;
        this.cost = cost;
    }

    public Product(String productName, Long cost) {
        this.productName = productName;
        this.cost = cost;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
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

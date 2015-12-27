package ru.myastrebov.rest.asm;

import org.springframework.hateoas.mvc.ResourceAssemblerSupport;
import ru.myastrebov.core.Product;
import ru.myastrebov.rest.controllers.ProductsController;
import ru.myastrebov.rest.resources.ProductResource;

import static org.springframework.hateoas.mvc.ControllerLinkBuilder.linkTo;
import static org.springframework.hateoas.mvc.ControllerLinkBuilder.methodOn;

/**
 * @author Maxim
 */
public class ProductAsm extends ResourceAssemblerSupport<Product, ProductResource> {
    public ProductAsm() {
        super(ProductsController.class, ProductResource.class);
    }

    @Override
    public ProductResource toResource(Product product) {
        ProductResource productResource = new ProductResource();
        productResource.setProductName(product.getProductName());
        productResource.setCost(product.getCost());
        productResource.add(linkTo(methodOn(ProductsController.class).getProduct(product.getId())).withSelfRel());
        return productResource;
    }
}

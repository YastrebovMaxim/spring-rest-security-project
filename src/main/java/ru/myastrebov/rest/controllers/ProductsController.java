package ru.myastrebov.rest.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import ru.myastrebov.core.Product;
import ru.myastrebov.core.services.ProductService;
import ru.myastrebov.rest.asm.ProductAsm;
import ru.myastrebov.rest.resources.ProductResource;

import java.net.URI;

/**
 * @author Maxim
 */
@RestController
@RequestMapping("/api/version")
public class ProductsController {

    private ProductService productService;

    @Autowired
    public ProductsController(ProductService productService) {
        this.productService = productService;
    }

    @RequestMapping(value = "/products", method = RequestMethod.GET)
    public ResponseEntity<?> getAllProducts() {
        return ResponseEntity.ok(new ProductAsm().toResources(productService.getAllProducts()));
    }

    @RequestMapping(value = "/products", method = RequestMethod.POST)
    public ResponseEntity<?> createProduct(@RequestBody Product product) {
        try {
            ProductResource productResource = new ProductAsm().toResource(productService.createProduct(product));
            HttpHeaders headers = new HttpHeaders();
            headers.setLocation(URI.create(productResource.getLink("self").getHref()));
            return new ResponseEntity<>(productResource, headers, HttpStatus.CREATED);
        } catch (Exception e) {
            //TODO
            return null;
        }
    }

    @RequestMapping(value = "/products/{productId}", method = RequestMethod.GET)
    public ResponseEntity<?> getProduct(@PathVariable("productId") Long productId) {
        Product product = productService.findById(productId);
        if (product != null) {
            return ResponseEntity.ok(new ProductAsm().toResource(product));
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @RequestMapping(value = "/products/{productId}", method = RequestMethod.PUT)
    public ResponseEntity<?> updateProduct(@PathVariable("productId") Long productId, @RequestBody ProductResource productResource) {
        try {
            Product updatedProduct = productService.updateProduct(productId, productResource.toProduct());
            return ResponseEntity.ok(new ProductAsm().toResource(updatedProduct));
        } catch (Exception e) {
            return ResponseEntity.notFound().build();
        }
    }
}

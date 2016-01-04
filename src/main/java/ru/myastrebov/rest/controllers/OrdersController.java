package ru.myastrebov.rest.controllers;

import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author Maxim
 */
@RestController
@RequestMapping("/api/version")
public class OrdersController {

    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @RequestMapping(value = "/orders", method = RequestMethod.GET)
    public ResponseEntity<?> getOrders() {
        return null;
    }
}

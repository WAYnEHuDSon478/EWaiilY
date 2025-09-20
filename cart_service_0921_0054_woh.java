// 代码生成时间: 2025-09-21 00:54:00
package com.example.cart;

import io.micronaut.core.annotation.Introspected;
import io.micronaut.core.annotation.Nullable;
import io.micronaut.http.annotation.Controller;
import io.micronaut.http.annotation.Get;
import io.micronaut.http.annotation.Post;
import io.micronaut.http.annotation.Body;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

@Introspected
@Controller("/cart")
public class ShoppingCartService {

    private final Map<UUID, Cart> carts = new HashMap<>();

    // Represents a simple Cart item
    public static class Cart {
        private UUID id;
        private Map<UUID, Product> products = new HashMap<>();

        public Cart(UUID id) {
            this.id = id;
        }

        public UUID getId() {
            return id;
        }

        public Map<UUID, Product> getProducts() {
            return products;
        }

        public void addProduct(Product product) {
            products.put(product.getId(), product);
        }

        public void removeProduct(UUID productId) {
            products.remove(productId);
        }
    }

    // Represents a simple Product
    public static class Product {
        private UUID id;
        private String name;
        private double price;

        public Product(UUID id, String name, double price) {
            this.id = id;
            this.name = name;
            this.price = price;
        }

        public UUID getId() {
            return id;
        }

        public String getName() {
            return name;
        }

        public double getPrice() {
            return price;
        }
    }

    @Get("/")
    public Map<UUID, Cart> getAllCarts() {
        return carts;
    }

    @Post("/add")
    public Cart createCart() {
        Cart cart = new Cart(UUID.randomUUID());
        carts.put(cart.getId(), cart);
        return cart;
    }

    @Post("/addProduct")
    public Cart addProductToCart(@Body CartItem cartItem) {
        UUID cartId = cartItem.getCartId();
        Cart cart = carts.get(cartId);
        if (cart == null) {
            throw new IllegalArgumentException("Cart not found");
        }
        cart.addProduct(new Product(cartItem.getProductId(), cartItem.getProductName(), cartItem.getProductPrice()));
        return cart;
    }

    // DTO for Cart Item
    public static class CartItem {
        private UUID cartId;
        private UUID productId;
        private String productName;
        private double productPrice;

        public CartItem() {}

        public CartItem(UUID cartId, UUID productId, String productName, double productPrice) {
            this.cartId = cartId;
            this.productId = productId;
            this.productName = productName;
            this.productPrice = productPrice;
        }

        @Nullable
        public UUID getCartId() {
            return cartId;
        }

        public void setCartId(@Nullable UUID cartId) {
            this.cartId = cartId;
        }

        @Nullable
        public UUID getProductId() {
            return productId;
        }

        public void setProductId(@Nullable UUID productId) {
            this.productId = productId;
        }

        @Nullable
        public String getProductName() {
            return productName;
        }

        public void setProductName(@Nullable String productName) {
            this.productName = productName;
        }

        public double getProductPrice() {
            return productPrice;
        }

        public void setProductPrice(double productPrice) {
            this.productPrice = productPrice;
        }
    }
}

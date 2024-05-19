package com.ecommerce.Service;

import com.ecommerce.Model.Product;

import java.util.List;

public interface ProductService {


    Product addProduct(Product product);

    Product findProductById(Long id);

    Product updateProduct(Long id, Product product);

    void updateProduct(Product product);

    void deleteProduct(Long id);

    List<Product> findAllProducts();

    List<Product> getProductsByCategoryName(String categoryName);
}

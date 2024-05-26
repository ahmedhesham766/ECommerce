package com.ecommerce.Service;

import com.ecommerce.DTO.ProductDTO;
import com.ecommerce.Model.Product;

import java.util.List;

public interface ProductService {


    Product addProduct(ProductDTO product);

    Product findProductById(Long id);

    Product updateProduct(Long id, ProductDTO product);

    void updateProduct(Product product);

    void deleteProduct(Long id);

    List<Product> findAllProducts();

    List<Product> getProductsByCategoryName(String categoryName);
}

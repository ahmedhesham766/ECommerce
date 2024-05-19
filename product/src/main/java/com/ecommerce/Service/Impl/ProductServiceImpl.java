package com.ecommerce.Service.Impl;

import com.ecommerce.Model.Product;
import com.ecommerce.REPO.ProductRepo;
import com.ecommerce.Service.ProductService;
import com.ecommerce.exception.UserNotFoundException;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ProductServiceImpl implements ProductService {

    private final ProductRepo productRepo;

    public ProductServiceImpl(ProductRepo productRepo) {
        this.productRepo = productRepo;
    }


    @Override
    public Product addProduct(Product product) {

        return productRepo.save(product);
    }

    @Override
    public Product findProductById(Long id) {
        return productRepo.findProductByproductId(id).orElseThrow(() -> new UserNotFoundException("Product By Id " + id + "is not Found"));
    }

    @Override
    public Product updateProduct(Long id, Product productDetails) {
        Product existingProduct = findProductById(id);
        if (existingProduct != null) {
            if (productDetails.getProductName() != null) {
                existingProduct.setProductName(productDetails.getProductName());
            }
            if (productDetails.getPrice() != null) {
                existingProduct.setPrice(productDetails.getPrice());
            }
            if (productDetails.getCategoryName() != null) {
                existingProduct.setCategoryName(productDetails.getCategoryName());
            }
            // Add checks for other fields as necessary
            if (productDetails.getStock() != null) {
                existingProduct.setStock(productDetails.getStock());
            }
            if (productDetails.getImageUrl() != null)
            {
                existingProduct.setImageUrl(productDetails.getImageUrl());
            }

            return productRepo.save(existingProduct);
        } else {
            return null;
        }
    }

    @Override
    public void updateProduct(Product product) {
        productRepo.save(product);
    }

    @Override
    public void deleteProduct(Long id) {
        productRepo.deleteById(id);
    }

    @Override
    public List<Product> findAllProducts() {
        return productRepo.findAll();
    }

    @Override
    public List<Product> getProductsByCategoryName(String categoryName) {
        return productRepo.findByCategoryName(categoryName);
    }
}

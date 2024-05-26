package com.ecommerce.Service.Impl;

import com.ecommerce.DTO.ProductDTO;
import com.ecommerce.Model.Product;
import com.ecommerce.Model.ProductImages;
import com.ecommerce.REPO.ProductImagesRepo;
import com.ecommerce.REPO.ProductRepo;
import com.ecommerce.Service.ProductService;
import com.ecommerce.exception.UserNotFoundException;


import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
@Transactional
public class ProductServiceImpl implements ProductService {

    private final ProductRepo productRepo;
    private final ProductImagesRepo productImagesRepo;

    public ProductServiceImpl(ProductRepo productRepo, ProductImagesRepo productImagesRepo) {
        this.productRepo = productRepo;
        this.productImagesRepo = productImagesRepo;
    }


    @Override
    public Product addProduct(ProductDTO productdto) {
        Product product = new Product();
        product.setProductName(productdto.getProductName());
        product.setStock(productdto.getStock());
        product.setPrice(productdto.getPrice());
        product.setCategoryName(productdto.getCategoryName());


        Product savedProduct = productRepo.save(product);
        List<ProductImages> productImagesList = productdto.getImageUrls().stream()
                .map(imageUrl -> {
                    ProductImages productImage = new ProductImages();
                    productImage.setProduct(savedProduct);
                    productImage.setImageUrl(imageUrl);
                    return productImage;
                })
                .collect(Collectors.toList());

        productImagesRepo.saveAll(productImagesList);
        savedProduct.setImages(productImagesList);
        return savedProduct;
    }

    @Override
    public Product findProductById(Long id) {
        return productRepo.findProductByproductId(id).orElseThrow(() -> new UserNotFoundException("Product By Id " + id + "is not Found"));
    }

    @Override
    public Product updateProduct(Long id, ProductDTO productDetails) {
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
            if (productDetails.getImageUrls() != null) {

                existingProduct.getImages().clear();

                List<ProductImages> productImagesList = productDetails.getImageUrls().stream()
                        .map(imageUrl -> {
                            ProductImages productImage = new ProductImages();
                            productImage.setProduct(existingProduct);
                            productImage.setImageUrl(imageUrl);
                            return productImage;
                        })
                        .toList();

                // Set images in the product
                existingProduct.getImages().addAll(productImagesList);

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

package com.ecommerce.Service.Impl;

import com.ecommerce.DTO.ProductDTO;
import com.ecommerce.Model.Product;
import com.ecommerce.Model.ProductColors;
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
            updateProductName(existingProduct, productDetails.getProductName());
            updateProductPrice(existingProduct, productDetails.getPrice());
            updateProductCategoryName(existingProduct, productDetails.getCategoryName());
            updateProductStock(existingProduct, productDetails.getStock());
            updateProductImages(existingProduct, productDetails.getImageUrls());
            updateProductColors(existingProduct, productDetails.getColors());

            return productRepo.save(existingProduct);
        } else {
            return null;
        }
    }

    private void updateProductName(Product existingProduct, String productName) {
        if (productName != null) {
            existingProduct.setProductName(productName);
        }
    }

    private void updateProductPrice(Product existingProduct, Double price) {
        if (price != null) {
            existingProduct.setPrice(price);
        }
    }

    private void updateProductCategoryName(Product existingProduct, String categoryName) {
        if (categoryName != null) {
            existingProduct.setCategoryName(categoryName);
        }
    }

    private void updateProductStock(Product existingProduct, Integer stock) {
        if (stock != null) {
            existingProduct.setStock(stock);
        }
    }

    private void updateProductImages(Product existingProduct, List<String> imageUrls) {
        if (imageUrls != null) {
            if (imageUrls.size() > 4) {
                existingProduct.getImages().clear();
            }

            List<ProductImages> productImagesList = imageUrls.stream()
                    .map(imageUrl -> {
                        ProductImages productImage = new ProductImages();
                        productImage.setProduct(existingProduct);
                        productImage.setImageUrl(imageUrl);
                        return productImage;
                    })
                    .collect(Collectors.toList());
            if (existingProduct.getImages().size() + productImagesList.size() > 4) {
                existingProduct.getImages().clear();
            }
            for (ProductImages newImages : productImagesList) {
                boolean imageExists = existingProduct.getImages().stream()
                        .anyMatch(existingImage -> existingImage.getImageUrl().equals(newImages.getImageUrl()));
                if (!imageExists) {
                    existingProduct.getImages().add(newImages);
                }
            }
        }
    }

    private void updateProductColors(Product existingProduct, List<String> newColors) {
        if (newColors != null) {
            if (newColors.size() > 4) {
                existingProduct.getColors().clear();
            }

            List<ProductColors> productColorsList = newColors.stream()
                    .map(color -> {
                        ProductColors productColor = new ProductColors();
                        productColor.setProduct(existingProduct);
                        productColor.setColorName(color);
                        return productColor;
                    })
                    .collect(Collectors.toList());

            if (existingProduct.getColors().size() + productColorsList.size() > 4) {
                existingProduct.getColors().clear();
            }

            for (ProductColors newColor : productColorsList) {
                boolean colorExists = existingProduct.getColors().stream()
                        .anyMatch(existingColor -> existingColor.getColorName().equals(newColor.getColorName()));
                if (!colorExists) {
                    existingProduct.getColors().add(newColor);
                }
            }
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

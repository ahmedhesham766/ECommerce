package com.ecommerce.Controller;

import com.ecommerce.DTO.ProductDTO;
import com.ecommerce.Model.Product;
import com.ecommerce.Service.ProductService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Controller
@RequestMapping("/products")
@CrossOrigin("*")
public class ProductController {

    private final ProductService productService;

    public ProductController(ProductService productService) {
        this.productService = productService;
    }

    //admin and customer can show products
    @GetMapping("/all")
    public ResponseEntity<List<Product>> getAllProducts()
    {
        List<Product> products = productService.findAllProducts();

        return new ResponseEntity<>(products, HttpStatus.OK);
    }

    //admin and customer can show a product
    @GetMapping("/{id}")
    public ResponseEntity<Product> GetProductById(@PathVariable("id") Long id)
    {
        Product product = productService.findProductById(id);
        return new ResponseEntity<>(product,HttpStatus.OK);
    }

    //admin just can add products
    @PostMapping("/add")
    public ResponseEntity<Map<String,Object>> addProduct(@RequestBody ProductDTO product)
    {
        Product productSaved = productService.addProduct(product);
        Map<String,Object> response = new HashMap<>();
        response.put("addProduct-message","Product " + productSaved.getProductName() + " added in the system successfully and its id is " + productSaved.getProductId());
        response.put("new-product",productSaved);
        return  ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    //admin only can update product
    @PutMapping("/update/{id}")
    public ResponseEntity<Product> updateProduct(@PathVariable("id") Long id, @RequestBody ProductDTO productDetails) {
        Product updatedProduct = productService.updateProduct(id, productDetails);
        if (updatedProduct != null) {
            return new ResponseEntity<>(updatedProduct, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    //admin only can delete product
    @DeleteMapping("/delete/{id}")
    public ResponseEntity<?> deleteProduct(@PathVariable("id") Long id)
    {
        productService.deleteProduct(id);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @PutMapping("/update-stock/{id}")
    public ResponseEntity<Void> updateProductStock(@PathVariable("id") Long id, @RequestBody Map<String, Integer> body) {
        // Extract newStock from the body
        Integer newStock = body.get("newStock");

        // Check if the newStock is present and valid
        if (newStock == null || newStock < 0) {
            return ResponseEntity.badRequest().build();
        }

        // Find the product by ID
        Product product = productService.findProductById(id);
        if (product == null) {
            return ResponseEntity.notFound().build();
        }

        // Update the product stock
        product.setStock(newStock);
        productService.updateProduct(product);
        return ResponseEntity.ok().build();
    }

    @GetMapping("/category/{categoryName}")
    public ResponseEntity<List<Product>> getProductsByCategoryName(@PathVariable String categoryName) {
        List<Product> products = productService.getProductsByCategoryName(categoryName);
        return new ResponseEntity<>(products,HttpStatus.OK);
    }

}

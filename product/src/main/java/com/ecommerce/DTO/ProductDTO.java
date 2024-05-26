package com.ecommerce.DTO;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Data
@AllArgsConstructor
@Setter
@Getter
public class ProductDTO {
    private String productName;
    private Integer stock;
    private Double price;
    private List<String> imageUrls;
    private String categoryName;
}

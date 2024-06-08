package com.ecommerce.DTO;

import lombok.*;

import java.util.List;
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class CartDTO {
    private Long cartId;
    private Long userId;
    private Double totalCost;
    private Integer prodCount;
    private List<CartItemDTO> products;
}

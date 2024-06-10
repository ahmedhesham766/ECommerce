package com.ecommerce.DTO;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;


public class CartRequest {
    private Long userId;
    private Long productID;

    // Constructor, getters, and setters should also use the camelCase convention
    public CartRequest(Long userID, Long productID) {
        this.userId = userID;
        this.productID = productID;
    }

    public void setUserID(Long userID) {
        this.userId = userID;
    }

    public void setProductID(Long productID) {
        this.productID = productID;
    }

    public Long getUserId() {
        return userId;
    }

    public Long getProductID() {
        return productID;
    }
}

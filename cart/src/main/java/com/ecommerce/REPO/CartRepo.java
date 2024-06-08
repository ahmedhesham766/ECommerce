package com.ecommerce.REPO;

import com.ecommerce.Model.Cart;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface CartRepo extends JpaRepository<Cart,Long> {

    Cart findByUserId(Long userId);

    @Query("SELECT c.prodCount FROM Cart c WHERE c.userId = :userId")
    Integer findProdCountByUserID(@Param("userId") Long userId);
}

package com.ecommerce.REPO;

import com.ecommerce.Model.ProductColors;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ProductColosrRepo extends JpaRepository<ProductColors,Long> {
}

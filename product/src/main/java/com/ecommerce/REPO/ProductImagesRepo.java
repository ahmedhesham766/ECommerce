package com.ecommerce.REPO;

import com.ecommerce.Model.ProductImages;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.springframework.web.bind.annotation.RequestBody;

@Repository
public interface ProductImagesRepo extends JpaRepository<ProductImages, Long> {
}

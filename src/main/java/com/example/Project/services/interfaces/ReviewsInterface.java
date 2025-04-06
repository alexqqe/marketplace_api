package com.example.Project.services.interfaces;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import java.util.List;

@Repository
public interface ReviewsInterface extends JpaRepository<Review, Long> {
    List<Review> findByProductIdOrderByCreatedAtDesc(Long productId);
    
    List<Review> findByProductIdOrderByRatingDesc(Long productId);
    
    @Query("SELECT AVG(r.rating) FROM Review r WHERE r.product.id = ?1")
    Double findAverageRatingByProductId(Long productId);
    
    boolean existsByUserIdAndProductId(Long userId, Long productId);
}
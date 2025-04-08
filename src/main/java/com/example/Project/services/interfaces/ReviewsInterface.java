package com.example.Project.services.interfaces;

import com.example.Project.model.entity.Reviews;
import org.springframework.stereotype.Repository;
import java.util.List;

@Repository
public interface ReviewsInterface {
    List<Reviews> findByProductIdOrderByCreatedAtDesc(Long productId);
    
    List<Reviews> findByProductIdOrderByRatingDesc(Long productId);

    Double findAverageRatingByProductId(Long productId);
    
    boolean existsByUserIdAndProductId(Long userId, Long productId);
}
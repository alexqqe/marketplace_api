package com.example.Project.controller;

import com.example.Project.model.entity.Reviews;
import com.example.Project.services.ReviewService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/reviews")
@RequiredArgsConstructor
public class ReviewsController {

    private final ReviewService reviewsService;

    @PostMapping
    public ResponseEntity<Void> createReview(@Valid @RequestBody Reviews review) {
        reviewsService.createReview(review);
        return ResponseEntity.ok().build();
    }

    @GetMapping("/product/{productId}/latest")
    public ResponseEntity<List<Reviews>> getLatestReviews(@PathVariable Long productId) {
        List<Reviews> reviews = reviewsService.findByProductIdOrderByCreatedAtDesc(productId);
        return ResponseEntity.ok(reviews);
    }

    @GetMapping("/product/{productId}/top-rated")
    public ResponseEntity<List<Reviews>> getTopRatedReviews(@PathVariable Long productId) {
        List<Reviews> reviews = reviewsService.findByProductIdOrderByRatingDesc(productId);
        return ResponseEntity.ok(reviews);
    }

    @GetMapping("/product/{productId}/average-rating")
    public ResponseEntity<Double> getAverageRating(@PathVariable Long productId) {
        Double average = reviewsService.findAverageRatingByProductId(productId);
        return ResponseEntity.ok(average);
    }

    @GetMapping("/exists")
    public ResponseEntity<Boolean> existsByUserAndProduct(
            @RequestParam Long userId,
            @RequestParam Long productId) {
        boolean exists = reviewsService.existsByUserIdAndProductId(userId, productId);
        return ResponseEntity.ok(exists);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Reviews> deleteReview(@PathVariable Long id) {
        Reviews deleted = reviewsService.deleteReviewById(id);
        return ResponseEntity.ok(deleted);
    }

    @PutMapping
    public ResponseEntity<Reviews> updateReview(@Valid @RequestBody Reviews review) {
        Reviews updated = reviewsService.updateReview(review);
        return ResponseEntity.ok(updated);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Reviews> getReviewById(@PathVariable Long id) {
        Reviews review = reviewsService.getReviewById(id);
        if (review == null) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(review);
    }
}

package com.example.Project.services;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class ReviewService {
    private final ReviewRepository reviewRepository;
    private final ProductService productService;
    private final UserService userService;

    @Transactional
    public ReviewDto createReview(CreateReviewRequest request, Long userId) {
        // Проверяем, не оставлял ли пользователь уже отзыв на этот товар
        if (reviewRepository.existsByUserIdAndProductId(userId, request.getProductId())) {
            throw new IllegalStateException("User has already reviewed this product");
        }

        // Проверяем валидность оценки
        if (request.getRating() < 1 || request.getRating() > 5) {
            throw new IllegalArgumentException("Rating must be between 1 and 5");
        }

        User user = userService.getUserById(userId);
        Product product = productService.getProductById(request.getProductId());

        Review review = new Review();
        review.setUser(user);
        review.setProduct(product);
        review.setRating(request.getRating());
        review.setComment(request.getComment());

        Review savedReview = reviewRepository.save(review);

        // Обновляем средний рейтинг товара
        updateProductAverageRating(product.getId());

        return convertToDto(savedReview);
    }

    public List<ReviewDto> getProductReviews(Long productId, String sortBy) {
        List<Review> reviews;

        if ("rating".equals(sortBy)) {
            reviews = reviewRepository.findByProductIdOrderByRatingDesc(productId);
        } else {
            reviews = reviewRepository.findByProductIdOrderByCreatedAtDesc(productId);
        }

        return reviews.stream()
                .map(this::convertToDto)
                .collect(Collectors.toList());
    }

    @Transactional
    public void deleteReview(Long reviewId, Long userId) {
        Review review = reviewRepository.findById(reviewId)
                .orElseThrow(() -> new ResourceNotFoundException("Review not found"));

        if (!review.getUser().getId().equals(userId)) {
            throw new UnauthorizedAccessException("User can only delete their own reviews");
        }

        Long productId = review.getProduct().getId();
        reviewRepository.delete(review);

        // Обновляем средний рейтинг товара после удаления отзыва
        updateProductAverageRating(productId);
    }

    private void updateProductAverageRating(Long productId) {
        Double averageRating = reviewRepository.findAverageRatingByProductId(productId);
        productService.updateProductRating(productId, averageRating);
    }

    private ReviewDto convertToDto(Review review) {
        ReviewDto dto = new ReviewDto();
        dto.setId(review.getId());
        dto.setUserId(review.getUser().getId());
        dto.setProductId(review.getProduct().getId());
        dto.setRating(review.getRating());
        dto.setComment(review.getComment());
        dto.setCreatedAt(review.getCreatedAt());
        dto.setUserName(review.getUser().getName());
        return dto;
    }
}
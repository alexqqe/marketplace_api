package com.example.Project.services;

import com.example.Project.model.entity.Reviews;
import com.example.Project.services.interfaces.ReviewsInterface;
import jakarta.validation.constraints.NotNull;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.stereotype.Service;

import java.sql.Timestamp;
import java.util.List;

@Service
public class ReviewService implements ReviewsInterface {

    private final JdbcTemplate jdbcTemplate;

    @Autowired
    public ReviewService(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    @Override
    public List<Reviews> findByProductIdOrderByCreatedAtDesc(Long productId) {
        String sql = "SELECT * FROM reviews WHERE product_id = ? ORDER BY created_at DESC";
        return jdbcTemplate.query(sql, new Object[]{productId}, (rs, rowNum) -> {
            Reviews review = new Reviews();
            review.setId(rs.getLong("id"));
            review.setUserId(rs.getLong("user_id"));
            review.setProductId(rs.getLong("product_id"));
            review.setRating(rs.getInt("rating"));
            review.setComment(rs.getString("comment"));
            review.setCreatedAt(rs.getTimestamp("created_at").toLocalDateTime());
            return review;
        });
    }

    @Override
    public List<Reviews> findByProductIdOrderByRatingDesc(Long productId) {
        String sql = "SELECT * FROM reviews WHERE product_id = ? ORDER BY rating DESC";
        return jdbcTemplate.query(sql, new Object[]{productId}, (rs, rowNum) -> {
            Reviews review = new Reviews();
            review.setId(rs.getLong("id"));
            review.setUserId(rs.getLong("user_id"));
            review.setProductId(rs.getLong("product_id"));
            review.setRating(rs.getInt("rating"));
            review.setComment(rs.getString("comment"));
            review.setCreatedAt(rs.getTimestamp("created_at").toLocalDateTime());
            return review;
        });
    }

    @Override
    public Double findAverageRatingByProductId(Long productId) {
        String sql = "SELECT AVG(rating) FROM reviews WHERE product_id = ?";
        return jdbcTemplate.queryForObject(sql, new Object[]{productId}, Double.class);
    }

    @Override
    public boolean existsByUserIdAndProductId(Long userId, Long productId) {
        String sql = "SELECT COUNT(*) FROM reviews WHERE user_id = ? AND product_id = ?";
        Integer count = jdbcTemplate.queryForObject(sql, new Object[]{userId, productId}, Integer.class);
        return count != null && count > 0;
    }

    public void createReview(@NotNull Reviews review) {
        String sql = "INSERT INTO reviews (user_id, product_id, rating, comment, created_at) VALUES (?, ?, ?, ?, ?)";
        jdbcTemplate.update(sql,
                review.getUserId(),
                review.getProductId(),
                review.getRating(),
                review.getComment(),
                Timestamp.valueOf(review.getCreatedAt()));
    }

    public Reviews getReviewById(Long id) {
        String sql = "SELECT * FROM reviews WHERE id = ?";
        try {
            return jdbcTemplate.queryForObject(sql, new Object[]{id}, (rs, rowNum) -> {
                Reviews review = new Reviews();
                review.setId(rs.getLong("id"));
                review.setUserId(rs.getLong("user_id"));
                review.setProductId(rs.getLong("product_id"));
                review.setRating(rs.getInt("rating"));
                review.setComment(rs.getString("comment"));
                review.setCreatedAt(rs.getTimestamp("created_at").toLocalDateTime());
                return review;
            });
        } catch (EmptyResultDataAccessException e) {
            return null;
        }
    }

    public Reviews deleteReviewById(Long id) {
        Reviews review = getReviewById(id);
        String sql = "DELETE FROM reviews WHERE id = ?";
        jdbcTemplate.update(sql, id);
        return review;
    }

    public Reviews updateReview(@NotNull Reviews review) {
        String sql = "UPDATE reviews SET user_id = ?, product_id = ?, rating = ?, comment = ?, created_at = ? WHERE id = ?";
        jdbcTemplate.update(sql,
                review.getUserId(),
                review.getProductId(),
                review.getRating(),
                review.getComment(),
                Timestamp.valueOf(review.getCreatedAt()),
                review.getId());
        return getReviewById(review.getId());
    }
}

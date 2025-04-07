package com.example.Project.services;

import com.example.Project.model.entity.Favorites;
import com.example.Project.services.interfaces.FavoritesInterface;
import jakarta.validation.constraints.NotNull;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

@Service
public class FavoritesService implements FavoritesInterface {

    private final JdbcTemplate jdbcTemplate;

    @Autowired
    public FavoritesService(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    @Override
    public void addFavorite(@NotNull Long userId, @NotNull Long productId) {
        String sql = "INSERT INTO favorites (user_id, product_id) VALUES (?, ?) ON CONFLICT DO NOTHING";
        jdbcTemplate.update(sql, userId, productId);
    }

    @Override
    public List<Favorites> getFavoritesByUserId(@NotNull Long userId) {
        String sql = "SELECT * FROM favorites WHERE user_id = ?";
        return jdbcTemplate.query(sql, (rs, rowNum) -> mapFavorite(rs), userId);
    }

    @Override
    public Favorites getFavoriteById(@NotNull Long id) {
        String sql = "SELECT * FROM favorites WHERE id = ?";
        try {
            return jdbcTemplate.queryForObject(sql, new Object[]{id}, (rs, rowNum) -> mapFavorite(rs));
        } catch (EmptyResultDataAccessException e) {
            return null;
        }
    }

    @Override
    public Favorites deleteFavoriteById(@NotNull Long id) {
        Favorites favorite = getFavoriteById(id);
        if (favorite != null) {
            String sql = "DELETE FROM favorites WHERE id = ?";
            jdbcTemplate.update(sql, id);
        }
        return favorite;
    }

    private Favorites mapFavorite(ResultSet rs) throws SQLException {
        Favorites favorite = new Favorites();
        favorite.setId(rs.getLong("id"));
        favorite.setUserId(rs.getLong("user_id"));
        favorite.setProductId(rs.getLong("product_id"));
        return favorite;
    }
}

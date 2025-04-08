package com.example.Project.services.interfaces;

import com.example.Project.model.entity.Favorites;
import jakarta.validation.constraints.NotNull;

import java.util.List;

public interface FavoritesInterface {
    void addFavorite(@NotNull Long userId, @NotNull Long productId);

    List<Favorites> getFavoritesByUserId(@NotNull Long userId);

    Favorites getFavoriteById(@NotNull Long id);

    Favorites deleteFavoriteById(@NotNull Long id);
}

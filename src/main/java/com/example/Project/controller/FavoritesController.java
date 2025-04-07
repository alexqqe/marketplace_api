package com.example.Project.controller;

import com.example.Project.model.entity.Favorites;
import com.example.Project.services.FavoritesService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/favorites")
@RequiredArgsConstructor
public class FavoritesController {

    private final FavoritesService favoritesService;

    @GetMapping("/{userId}")
    public List<Favorites> readFavoritesByUserId(@PathVariable long userId) {
        return this.favoritesService.getFavoritesByUserId(userId);
    }

    @PostMapping("/{userId}/{productId}")
    public void addFavorite(@PathVariable long userId, @PathVariable long productId) {
        this.favoritesService.addFavorite(userId, productId);
    }

    @PostMapping("/delete/{id}")
    public Favorites deleteFavoriteById(@PathVariable long id) {
        return this.favoritesService.deleteFavoriteById(id);
    }

    @GetMapping("/id/{id}")
    public Favorites readFavoriteById(@PathVariable long id) {
        return this.favoritesService.getFavoriteById(id);
    }
}

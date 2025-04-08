package com.example.Project.services.interfaces;
import org.springframework.data.jpa.repository.JpaRepository;
import com.example.Project.model.entity.Favorites;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface FavoritesRepository extends JpaRepository<Favorites, Long> {
    List<Favorites> findByUserId(Long userId);
}

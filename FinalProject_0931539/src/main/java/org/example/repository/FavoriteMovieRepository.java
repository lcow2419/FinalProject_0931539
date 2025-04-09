package org.example.repository;

import org.example.model.FavoriteMovie;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

/**
 * Repository interface for FavoriteMovie entity.
 */
public interface FavoriteMovieRepository extends JpaRepository<FavoriteMovie, Long> {
    Optional<FavoriteMovie> findByTmdbId(Long tmdbId);
}

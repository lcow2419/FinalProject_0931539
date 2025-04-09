package org.example.controller;

import org.example.model.FavoriteMovie;
import org.example.service.MovieService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import java.util.Map;

/**
 * Controller layer: handles web routing and data passing to Thymeleaf views.
 */
@Controller
public class MovieController {
    private final MovieService service;

    public MovieController(MovieService service) {
        this.service = service;
    }

    // Home page - show trending movies
    @GetMapping("/")
    public String index(Model model) {
        model.addAttribute("movies", service.getTrendingMovies());
        return "index";
    }

    // Detail page for one movie
    @GetMapping("/movie/{id}")
    public String movieDetails(@PathVariable Long id, Model model) {
        Map movie = service.getMovieDetails(id);
        boolean isFavorite = service.isFavorite(id);
        model.addAttribute("movie", movie);
        model.addAttribute("isFavorite", isFavorite);
        return "movie-details";
    }

    // Add to favorites
    @PostMapping("/favorite")
    public String addToFavorites(@RequestParam Long tmdbId,
                                 @RequestParam String title,
                                 @RequestParam String posterPath,
                                 @RequestParam String overview) {
        FavoriteMovie movie = new FavoriteMovie();
        movie.setTmdbId(tmdbId);
        movie.setTitle(title);
        movie.setPosterPath(posterPath);
        movie.setOverview(overview);
        service.addToFavorites(movie);
        return "redirect:/favorites";
    }

    // View all favorite movies
    @GetMapping("/favorites")
    public String favorites(Model model) {
        model.addAttribute("favorites", service.getFavorites());
        return "favorites";
    }

    // Remove from favorites
    @PostMapping("/favorites/delete/{id}")
    public String removeFavorite(@PathVariable Long id) {
        service.removeFromFavorites(id);
        return "redirect:/favorites";
    }
}

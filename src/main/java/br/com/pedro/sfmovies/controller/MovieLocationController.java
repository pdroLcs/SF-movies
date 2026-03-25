package br.com.pedro.sfmovies.controller;

import br.com.pedro.sfmovies.model.MovieLocation;
import br.com.pedro.sfmovies.service.MovieLocationService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/movies")
@RequiredArgsConstructor
public class MovieLocationController {

    private final MovieLocationService service;

    @GetMapping
    public List<MovieLocation> getAll(@RequestParam("t") Optional<String> title) {
        return title.map(service::filterByTitle)
                .orElseGet(service::getAllMovies);
    }

    @GetMapping("/autocomplete")
    public List<String> autocomplete(@RequestParam("q") String prefix) {
        return service.autocomplete(prefix);
    }

}

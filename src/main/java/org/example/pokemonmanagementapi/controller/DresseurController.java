package org.example.pokemonmanagementapi.controller;

import org.example.pokemonmanagementapi.dto.DresseurDto;
import org.example.pokemonmanagementapi.service.DresseurService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/dresseurs")
public class DresseurController {

    private final DresseurService dresseurService;

    public DresseurController(DresseurService dresseurService) {
        this.dresseurService = dresseurService;
    }

    // 200 OK si le dresseur existe ; sinon DresseurNotFoundException -> 201 (voir GlobalExceptionHandler)
    @GetMapping("/{id}")
    public ResponseEntity<DresseurDto> getDresseur(@PathVariable Long id) {
        return ResponseEntity.ok(dresseurService.getDresseur(id));
    }
}

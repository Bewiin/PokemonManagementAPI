package org.example.pokemonmanagementapi.repository;

import org.example.pokemonmanagementapi.entity.Pokemon;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PokemonRepository extends JpaRepository<Pokemon, Long> {
}

package org.example.pokemonmanagementapi.service;

import org.example.pokemonmanagementapi.dto.DresseurDto;
import org.example.pokemonmanagementapi.entity.Dresseur;
import org.example.pokemonmanagementapi.entity.Pokemon;
import org.example.pokemonmanagementapi.exception.DresseurNotFoundException;
import org.example.pokemonmanagementapi.repository.DresseurRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional(readOnly = true)
public class DresseurService {

    private final DresseurRepository dresseurRepository;

    // Injection par constructeur : permet de passer un mock dans les tests unitaires
    public DresseurService(DresseurRepository dresseurRepository) {
        this.dresseurRepository = dresseurRepository;
    }

    public DresseurDto getDresseur(Long dresseurId) {
        Dresseur dresseur = trouverDresseur(dresseurId);
        return new DresseurDto(
                dresseur.getNom(),
                dresseur.getPokemons().size(),
                calculerNiveauDresseur(dresseurId)
        );
    }

    public int calculerNiveauDresseur(Long dresseurId) {
        List<Pokemon> pokemons = trouverDresseur(dresseurId).getPokemons();

        // Cas limite : aucun Pokémon
        if (pokemons.isEmpty()) {
            return 1;
        }

        // Base : somme des niveaux
        int niveau = pokemons.stream()
                .mapToInt(Pokemon::getNiveau)
                .sum();

        // Bonus diversité : au moins 3 types distincts -> +20
        long nbTypesDistincts = pokemons.stream()
                .map(Pokemon::getType)
                .distinct()
                .count();
        if (nbTypesDistincts >= 3) {
            niveau += 20;
        }

        // Bonus élite : +10 par Pokémon de niveau >= 50
        long nbElites = pokemons.stream()
                .filter(p -> p.getNiveau() >= 50)
                .count();
        niveau += (int) nbElites * 10;

        return niveau;
    }

    private Dresseur trouverDresseur(Long dresseurId) {
        return dresseurRepository.findById(dresseurId)
                .orElseThrow(() -> new DresseurNotFoundException(dresseurId));
    }
}

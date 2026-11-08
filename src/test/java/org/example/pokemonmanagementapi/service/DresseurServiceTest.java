package org.example.pokemonmanagementapi.service;

import org.example.pokemonmanagementapi.dto.DresseurDto;
import org.example.pokemonmanagementapi.entity.Dresseur;
import org.example.pokemonmanagementapi.entity.Pokemon;
import org.example.pokemonmanagementapi.exception.DresseurNotFoundException;
import org.example.pokemonmanagementapi.repository.DresseurRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.when;

// Test unitaire pur : pas de contexte Spring, pas de base de données.
// Le repository est remplacé par un mock Mockito.
@ExtendWith(MockitoExtension.class)
class DresseurServiceTest {

    @Mock
    private DresseurRepository dresseurRepository;

    @InjectMocks
    private DresseurService dresseurService;

    @Test
    void should_return_sum_of_levels_when_no_bonus_applies() {
        // Arrange : 1 seul type, aucun élite
        Dresseur ondine = creerDresseur("Ondine",
                creerPokemon("Stari", "Eau", 20),
                creerPokemon("Psykokwak", "Eau", 25));
        when(dresseurRepository.findById(1L)).thenReturn(Optional.of(ondine));

        // Act
        int niveau = dresseurService.calculerNiveauDresseur(1L);

        // Assert : 20 + 25
        assertEquals(45, niveau);
    }

    @Test
    void should_return_1_when_dresseur_has_no_pokemon() {
        // Arrange
        Dresseur pierre = creerDresseur("Pierre");
        when(dresseurRepository.findById(1L)).thenReturn(Optional.of(pierre));

        // Act
        int niveau = dresseurService.calculerNiveauDresseur(1L);

        // Assert
        assertEquals(1, niveau);
    }

    @Test
    void should_apply_diversity_bonus_when_dresseur_has_3_different_types() {
        // Arrange : 3 types distincts, aucun élite
        Dresseur dresseur = creerDresseur("Régis",
                creerPokemon("Salamèche", "Feu", 10),
                creerPokemon("Carapuce", "Eau", 15),
                creerPokemon("Pikachu", "Électrik", 20));
        when(dresseurRepository.findById(1L)).thenReturn(Optional.of(dresseur));

        // Act
        int niveau = dresseurService.calculerNiveauDresseur(1L);

        // Assert : 10 + 15 + 20 = 45, + 20 (diversité)
        assertEquals(65, niveau);
    }

    @Test
    void should_not_apply_diversity_bonus_when_dresseur_has_only_2_types() {
        // Arrange : 3 Pokémon mais seulement 2 types distincts
        Dresseur dresseur = creerDresseur("Flora",
                creerPokemon("Galifeu", "Feu", 10),
                creerPokemon("Poussifeu", "Feu", 15),
                creerPokemon("Lombre", "Eau", 20));
        when(dresseurRepository.findById(1L)).thenReturn(Optional.of(dresseur));

        // Act
        int niveau = dresseurService.calculerNiveauDresseur(1L);

        // Assert : 10 + 15 + 20, pas de bonus
        assertEquals(45, niveau);
    }

    @Test
    void should_apply_elite_bonus_when_pokemon_level_is_50_or_more() {
        // Arrange : 2 élites (50 pile et 60), 1 seul type
        Dresseur dresseur = creerDresseur("Flora",
                creerPokemon("Braségali", "Feu", 50),
                creerPokemon("Dracaufeu", "Feu", 60));
        when(dresseurRepository.findById(1L)).thenReturn(Optional.of(dresseur));

        // Act
        int niveau = dresseurService.calculerNiveauDresseur(1L);

        // Assert : 50 + 60 = 110, + 2 x 10 (élites)
        assertEquals(130, niveau);
    }

    @Test
    void should_not_apply_elite_bonus_when_pokemon_level_is_49() {
        // Arrange : juste sous le seuil élite
        Dresseur dresseur = creerDresseur("Régis",
                creerPokemon("Raichu", "Électrik", 49));
        when(dresseurRepository.findById(1L)).thenReturn(Optional.of(dresseur));

        // Act
        int niveau = dresseurService.calculerNiveauDresseur(1L);

        // Assert
        assertEquals(49, niveau);
    }

    @Test
    void should_cumulate_bonuses_when_dresseur_has_3_types_and_elite_pokemons() {
        // Arrange : 3 types distincts + 2 élites
        Dresseur sacha = creerDresseur("Sacha",
                creerPokemon("Pikachu", "Électrik", 55),
                creerPokemon("Dracaufeu", "Feu", 60),
                creerPokemon("Tortank", "Eau", 30));
        when(dresseurRepository.findById(1L)).thenReturn(Optional.of(sacha));

        // Act
        int niveau = dresseurService.calculerNiveauDresseur(1L);

        // Assert : 55 + 60 + 30 = 145, + 20 (diversité) + 2 x 10 (élites)
        assertEquals(185, niveau);
    }

    @Test
    void should_throw_exception_when_dresseur_does_not_exist() {
        // Arrange
        when(dresseurRepository.findById(99L)).thenReturn(Optional.empty());

        // Act + Assert : assertThrows exécute l'appel et vérifie l'exception levée
        DresseurNotFoundException exception = assertThrows(DresseurNotFoundException.class,
                () -> dresseurService.calculerNiveauDresseur(99L));

        assertEquals("Le dresseur avec l'id 99 n'existe pas", exception.getMessage());
    }

    @Test
    void should_return_dto_when_dresseur_exists() {
        // Arrange
        Dresseur sacha = creerDresseur("Sacha",
                creerPokemon("Pikachu", "Électrik", 55),
                creerPokemon("Dracaufeu", "Feu", 60),
                creerPokemon("Tortank", "Eau", 30));
        when(dresseurRepository.findById(1L)).thenReturn(Optional.of(sacha));

        // Act
        DresseurDto dto = dresseurService.getDresseur(1L);

        // Assert
        assertEquals("Sacha", dto.nomDresseur());
        assertEquals(3, dto.nombrePokemons());
        assertEquals(185, dto.niveauDresseur());
    }

    // --- Méthodes utilitaires pour construire les données de test ---

    private Dresseur creerDresseur(String nom, Pokemon... pokemons) {
        Dresseur dresseur = new Dresseur();
        dresseur.setNom(nom);
        dresseur.setPokemons(List.of(pokemons));
        return dresseur;
    }

    private Pokemon creerPokemon(String nom, String type, int niveau) {
        Pokemon pokemon = new Pokemon();
        pokemon.setNom(nom);
        pokemon.setType(type);
        pokemon.setNiveau(niveau);
        return pokemon;
    }
}

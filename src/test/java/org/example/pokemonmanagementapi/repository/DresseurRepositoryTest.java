package org.example.pokemonmanagementapi.repository;

import org.example.pokemonmanagementapi.entity.Dresseur;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.data.jpa.test.autoconfigure.DataJpaTest;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

// Charge uniquement la couche JPA (entités + repositories) sur une base H2,
// initialisée avec src/test/resources/data.sql. Chaque test est rollback à la fin.
@DataJpaTest
class DresseurRepositoryTest {

    @Autowired
    private DresseurRepository dresseurRepository;

    @Test
    void should_find_dresseur_when_id_exists() {
        // Arrange
        Long idSacha = 1L;

        // Act
        Optional<Dresseur> resultat = dresseurRepository.findById(idSacha);

        // Assert
        assertTrue(resultat.isPresent());
        assertEquals("Sacha", resultat.get().getNom());
        assertEquals("Kanto", resultat.get().getRegion());
    }

    @Test
    void should_return_empty_when_id_does_not_exist() {
        // Arrange
        Long idInexistant = 99L;

        // Act
        Optional<Dresseur> resultat = dresseurRepository.findById(idInexistant);

        // Assert
        assertFalse(resultat.isPresent());
    }

    @Test
    void should_return_all_dresseurs_when_data_sql_is_loaded() {
        // Arrange : les 5 dresseurs sont insérés par data.sql

        // Act
        long nombreDresseurs = dresseurRepository.count();

        // Assert
        assertEquals(5, nombreDresseurs);
    }

    @Test
    void should_load_pokemons_when_dresseur_has_pokemons() {
        // Arrange
        Long idSacha = 1L;

        // Act
        Dresseur sacha = dresseurRepository.findById(idSacha).orElseThrow();

        // Assert
        assertEquals(3, sacha.getPokemons().size());
    }

    @Test
    void should_return_empty_pokemon_list_when_dresseur_has_no_pokemon() {
        // Arrange
        Long idPierre = 3L;

        // Act
        Dresseur pierre = dresseurRepository.findById(idPierre).orElseThrow();

        // Assert
        assertEquals(0, pierre.getPokemons().size());
    }

    @Test
    void should_persist_dresseur_when_saved() {
        // Arrange
        Dresseur nouveau = new Dresseur();
        nouveau.setNom("Aurore");
        nouveau.setRegion("Sinnoh");

        // Act
        Dresseur sauvegarde = dresseurRepository.save(nouveau);

        // Assert
        Dresseur relu = dresseurRepository.findById(sauvegarde.getId()).orElseThrow();
        assertEquals("Aurore", relu.getNom());
        assertEquals("Sinnoh", relu.getRegion());
        assertEquals(6, dresseurRepository.count());
    }
}

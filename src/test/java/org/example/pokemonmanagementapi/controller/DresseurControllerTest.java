package org.example.pokemonmanagementapi.controller;

import org.example.pokemonmanagementapi.dto.DresseurDto;
import org.example.pokemonmanagementapi.exception.DresseurNotFoundException;
import org.example.pokemonmanagementapi.service.DresseurService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.webmvc.test.autoconfigure.WebMvcTest;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

// Ne charge que la couche web du controller (pas de base, pas de vraie couche service).
// La couche Service est remplacée par un mock Mockito via @MockitoBean.
// Le GlobalExceptionHandler (@RestControllerAdvice) est bien pris en compte par @WebMvcTest.
@WebMvcTest(DresseurController.class)
class DresseurControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockitoBean
    private DresseurService dresseurService;

    @Test
    void should_return_200_and_dto_when_dresseur_exists() throws Exception {
        // Arrange
        DresseurDto dto = new DresseurDto("Sacha", 3, 185);
        when(dresseurService.getDresseur(1L)).thenReturn(dto);

        // Act + Assert
        mockMvc.perform(get("/dresseurs/{id}", 1L))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.nomDresseur").value("Sacha"))
                .andExpect(jsonPath("$.nombrePokemons").value(3))
                .andExpect(jsonPath("$.niveauDresseur").value(185));
    }

    @Test
    void should_return_201_when_dresseur_does_not_exist() throws Exception {
        // Arrange : le service lève l'exception -> gérée par GlobalExceptionHandler (201)
        when(dresseurService.getDresseur(99L))
                .thenThrow(new DresseurNotFoundException(99L));

        // Act + Assert
        mockMvc.perform(get("/dresseurs/{id}", 99L))
                .andExpect(status().isCreated())
                .andExpect(content().string("Le dresseur avec l'id 99 n'existe pas"));
    }
}

package org.example.pokemonmanagementapi.exception;

public class DresseurNotFoundException extends RuntimeException {

    public DresseurNotFoundException(Long id) {
        super("Le dresseur avec l'id " + id + " n'existe pas");
    }
}

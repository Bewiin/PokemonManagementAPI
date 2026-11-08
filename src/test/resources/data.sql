-- Jeu de données de test (H2), chargé au démarrage du contexte de test

INSERT INTO dresseur (nom, region) VALUES ('Sacha', 'Kanto');     -- id 1
INSERT INTO dresseur (nom, region) VALUES ('Ondine', 'Kanto');    -- id 2
INSERT INTO dresseur (nom, region) VALUES ('Pierre', 'Kanto');    -- id 3
INSERT INTO dresseur (nom, region) VALUES ('Flora', 'Hoenn');     -- id 4
INSERT INTO dresseur (nom, region) VALUES ('Régis', 'Kanto');     -- id 5

-- Sacha (id 1) : 3 types + 2 élites -> 55 + 60 + 30 = 145 + 20 + 20 = 185
INSERT INTO pokemon (nom, type, niveau, dresseur_id) VALUES ('Pikachu', 'Électrik', 55, 1);
INSERT INTO pokemon (nom, type, niveau, dresseur_id) VALUES ('Dracaufeu', 'Feu', 60, 1);
INSERT INTO pokemon (nom, type, niveau, dresseur_id) VALUES ('Tortank', 'Eau', 30, 1);

-- Ondine (id 2) : 1 seul type, aucun élite -> 20 + 25 = 45
INSERT INTO pokemon (nom, type, niveau, dresseur_id) VALUES ('Stari', 'Eau', 20, 2);
INSERT INTO pokemon (nom, type, niveau, dresseur_id) VALUES ('Psykokwak', 'Eau', 25, 2);

-- Pierre (id 3) : aucun Pokémon -> 1

-- Flora (id 4) : 2 types seulement (pas de bonus diversité), 1 élite à 50 pile -> 50 + 40 + 10 = 100 + 10 = 110
INSERT INTO pokemon (nom, type, niveau, dresseur_id) VALUES ('Braségali', 'Feu', 50, 4);
INSERT INTO pokemon (nom, type, niveau, dresseur_id) VALUES ('Galifeu', 'Feu', 40, 4);
INSERT INTO pokemon (nom, type, niveau, dresseur_id) VALUES ('Lombre', 'Eau', 10, 4);

-- Régis (id 5) : 3 types, aucun élite (49 = juste sous le seuil) -> 49 + 10 + 15 = 74 + 20 = 94
INSERT INTO pokemon (nom, type, niveau, dresseur_id) VALUES ('Raichu', 'Électrik', 49, 5);
INSERT INTO pokemon (nom, type, niveau, dresseur_id) VALUES ('Salamèche', 'Feu', 10, 5);
INSERT INTO pokemon (nom, type, niveau, dresseur_id) VALUES ('Carapuce', 'Eau', 15, 5);

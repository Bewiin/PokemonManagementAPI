-- Jeu de données pour lancer l'application en local
INSERT INTO dresseur (nom, region) VALUES ('Sacha', 'Kanto');
INSERT INTO dresseur (nom, region) VALUES ('Ondine', 'Kanto');
INSERT INTO dresseur (nom, region) VALUES ('Pierre', 'Kanto');

-- Sacha (id 1) : 55 + 60 + 30 = 145, +20 (3 types), +20 (2 élites) -> 185
INSERT INTO pokemon (nom, type, niveau, dresseur_id) VALUES ('Pikachu', 'Électrik', 55, 1);
INSERT INTO pokemon (nom, type, niveau, dresseur_id) VALUES ('Dracaufeu', 'Feu', 60, 1);
INSERT INTO pokemon (nom, type, niveau, dresseur_id) VALUES ('Tortank', 'Eau', 30, 1);

-- Ondine (id 2) : 20 + 25 = 45, 1 seul type, aucun élite -> 45
INSERT INTO pokemon (nom, type, niveau, dresseur_id) VALUES ('Stari', 'Eau', 20, 2);
INSERT INTO pokemon (nom, type, niveau, dresseur_id) VALUES ('Psykokwak', 'Eau', 25, 2);

-- Pierre (id 3) : aucun Pokémon -> 1

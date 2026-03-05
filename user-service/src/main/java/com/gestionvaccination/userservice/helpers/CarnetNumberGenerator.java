package com.gestionvaccination.userservice.helpers;

import java.util.UUID;

/**
 * Classe utilitaire pour générer les numéros de carnet de vaccination
 * Format simple: VAC0 + 8 caractères UUID (pour éviter les doublons)
 * Exemple: VAC0A1B2C3D4E
 */
public class CarnetNumberGenerator {

    private static final String PREFIX = "VAC0";
    private static final int UUID_LENGTH = 8;

    /**
     * Génère un numéro de carnet unique avec UUID
     * Format: VAC0 + 8 caractères d'UUID (alphanumériques majuscules)
     *
     * @return Le numéro de carnet généré (12 caractères)
     * Exemples:
     * - VAC0A1B2C3D
     * - VAC0F5E8D9C
     * - VAC078ABCDEF
     */
    public static String generateCarnetNumber() {
        // Générer un UUID et extraire les 8 premiers caractères (sans tirets)
        String uuid = UUID.randomUUID().toString().replace("-", "").toUpperCase();
        String shortUUID = uuid.substring(0, UUID_LENGTH);
        
        return PREFIX + shortUUID;
    }

}

// package com.gestionvaccination.userservice.services;

// import com.gestionvaccination.userservice.entites.Enfant;
// import com.gestionvaccination.userservice.entites.Utilisateur;
// import com.gestionvaccination.userservice.helpers.CarnetNumberGenerator;
// import com.gestionvaccination.userservice.repository.EnfantRepository;
// import com.gestionvaccination.userservice.repository.UtilisateurRepository;

// import lombok.AllArgsConstructor;
// import org.springframework.stereotype.Service;

// /**
//  * Service pour générer et gérer les numéros de carnet de vaccination
//  * Format: VAC0 + 8 caractères UUID
//  * Exemple: VAC0A1B2C3D4E
//  * 
//  * Avantages:
//  * - Génération simple et rapide
//  * - Unicité 100% garantie (basée sur UUID)
//  * - Pas de collision possible
//  * - Indépendant de l'ID utilisateur
//  */
// @Service
// @AllArgsConstructor
// public class CarnetNumberService {

//        private EnfantRepository enfantRepository;
//        private UtilisateurRepository utilisateurRepository;


//     /**
//      * Génère et attribue un numéro de carnet unique à un utilisateur
//      * Format: VAC0 + 8 caractères UUID
//      *
//      * @param utilisateur L'utilisateur auquel attribuer le numéro
//      * @return Le numéro de carnet généré
//      */
//     public String generateAndAssignCarnetForUtilisateur(Utilisateur utilisateur) {
//         // Si le numéro existe déjà, le retourner
//         if (utilisateur.getNumeroCarnet() != null && !utilisateur.getNumeroCarnet().isEmpty()) {
//             return utilisateur.getNumeroCarnet();
//         }


       // Générer un numéro unique

        // String carnetNumber;
        // do {
        //     carnetNumber = CarnetNumberGenerator.generateCarnetNumber();
        //     // Tant que le numéro existe déjà dans un enfant ou un utilisateur, on régénère
        // } while (utilisateurRepository.findByNumeroCarnet(carnetNumber) != null
        //         || enfantRepository.findByNumeroCarnet(carnetNumber) != null);


 
//         String carnetNumber = CarnetNumberGenerator.generateCarnetNumber();
//         utilisateur.setNumeroCarnet(carnetNumber);
        
//         return carnetNumber;
//     }

//     /**
//      * Génère et attribue un numéro de carnet unique à un enfant
//      * Format: VAC0 + 8 caractères UUID
//      *
//      * @param enfant L'enfant auquel attribuer le numéro
//      * @return Le numéro de carnet généré
//      */
//     public String generateAndAssignCarnetForEnfant(Enfant enfant) {
//         // Si le numéro existe déjà, le retourner
//         if (enfant.getNumeroCarnet() != null && !enfant.getNumeroCarnet().isEmpty()) {
//             return enfant.getNumeroCarnet();
//         }

//         // Générer un numéro unique
//         String carnetNumber = CarnetNumberGenerator.generateCarnetNumber();
//         enfant.setNumeroCarnet(carnetNumber);
        
//         return carnetNumber;
//     }

// }

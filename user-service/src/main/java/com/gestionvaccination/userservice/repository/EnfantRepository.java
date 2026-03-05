package com.gestionvaccination.userservice.repository;

import com.gestionvaccination.userservice.entites.Enfant;
import com.gestionvaccination.userservice.enumeration.Sexe;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

/**
 * Repository pour la gestion des enfants
 */
@Repository
public interface EnfantRepository extends JpaRepository<Enfant, Long> {
    Optional<Enfant> findByContenuQrCode(String contenuQrCode);

    Optional<Enfant> findByAccessToken(String accessToken);

    Enfant findByNumeroCarnet(String numeroCarnet);

    // Compte le nombre d'enfants par sexe
    long countBySexe(Sexe sexe);

}
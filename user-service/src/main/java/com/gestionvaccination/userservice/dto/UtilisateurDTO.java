package com.gestionvaccination.userservice.dto;

import com.gestionvaccination.userservice.client.dto.CentreDTO;
import com.gestionvaccination.userservice.client.dto.LocalityDTO;
import com.gestionvaccination.userservice.enumeration.*;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import lombok.Data;

import java.time.LocalDate;
import java.util.*;

/**
 * DTO pour retourner les données d'un utilisateur
 */
@Data
public class UtilisateurDTO {

    private Long id;

    private String email;

    private Long age;

    private String statutMatrimonial;


    private String telephone;

    private UserRole userRole;


    private String prenom;


    private String nom;

    private String password;

    private String adresse;

    private String groupeSanguin;

    private String matricule;

    private LocalDate dateEmbauche;

    private String profession;


    private String niveauEtude;

    private Set<UserRole> roles ;

    // Informations médicales
    private String allergies;

    private String antecedentsMedicaux;

    private String numeroCarnet;

    private LocalityDTO locality;

    private CentreDTO centre;

    private String nomTuteur1;

    private String nomTuteur2;

    private String prenomTuteur1;

    private String prenomTuteur2;

    private String numeroTuteur1;

    private String numeroTuteur2;

    private LangueMaternelle langueMaternelle;

//    private Date createdAt;
//
//    private Date updatedAt;
//
//    private List<EnfantDTO> enfants;

    // Informations Pour la prediction
    private NiveauInstruction niveauInstruction;

    private Long nombre_enfants;

    private String retard_vaccinal;

    private float distance_centre_sante;

    private  String acces_transport;

    private ZoneResidence zoneResidence;

    private NiveauRevenu niveauRevenu;



}
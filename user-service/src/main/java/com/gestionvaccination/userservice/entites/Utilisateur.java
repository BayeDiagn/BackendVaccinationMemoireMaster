package com.gestionvaccination.userservice.entites;

import com.gestionvaccination.userservice.client.dto.CentreDTO;
import com.gestionvaccination.userservice.client.dto.LocalityDTO;
import com.gestionvaccination.userservice.enumeration.*;
import com.gestionvaccination.userservice.helpers.AbstractEntity;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDate;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * Entité représentant un utilisateur du système de vaccination
 */
@Entity
@Table(name = "utilisateurs")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class Utilisateur extends AbstractEntity {
    
    @Column(unique = true, nullable = false)
    private String email;

    //@Column(unique = false, nullable = false)
    private Long age;

    private String statutMatrimonial;

    private String password;

    private String telephone;
    
    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private UserRole userRole;

    @Column(nullable = false)
    private String prenom;

    private LocalDate dateEmbauche;

    private String matricule;
    
    @Column(nullable = false)
    private String nom;
    
    private String adresse;

    @Column(name = "groupe_sanguin")
    private String groupeSanguin;

    @Column(name = "locality_id")
    private Long localityId; // ID de la localité dans location-service (généralement la commune)

    @Column(name= "centre_id")
    private Long centreId;
    
    private String profession;


    private String niveauEtude;

    // Informations médicales
    private String allergies;

    private String antecedentsMedicaux;

    private String numeroCarnet;

    @Enumerated(EnumType.STRING)
    private LangueMaternelle langueMaternelle;

    @Transient
    private Set<UserRole> roles = new HashSet<>();

    // Relations
    @OneToMany(mappedBy = "parent", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List<Enfant> enfants ;

    // Données de localisation enrichies depuis location-service
    @Transient
    private LocalityDTO locality;


    @Transient
    private CentreDTO centre;

    private String nomTuteur1;

    private String nomTuteur2;

    private String prenomTuteur1;

    private String prenomTuteur2;

    private String numeroTuteur1;

    private String numeroTuteur2;

    //Informations Capitales Pour la prediction de notre modele
    @Enumerated(EnumType.STRING)
    private NiveauInstruction niveauInstruction;

    private Long nombre_enfants;

    private String retard_vaccinal;

    private float distance_centre_sante;

    private  String acces_transport;

    @Enumerated(EnumType.STRING)
    private ZoneResidence zoneResidence;

    @Enumerated(EnumType.STRING)
    private NiveauRevenu niveauRevenu;


}

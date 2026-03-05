package com.gestionvaccination.userservice.dto;


import com.gestionvaccination.userservice.enumeration.LangueMaternelle;
import com.gestionvaccination.userservice.enumeration.NiveauInstruction;
import com.gestionvaccination.userservice.enumeration.NiveauRevenu;
import com.gestionvaccination.userservice.enumeration.ZoneResidence;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.Data;

@Data
public class SaveParentDTO {

    private String email;

    @NotBlank(message = "L'age est obligatoire")
    private Long age;

    @NotBlank(message = "Le statut est obligatoire")
    private String statutMatrimonial;

    @NotBlank(message = "Le téléphone est obligatoire")
    @Pattern(regexp = "^(\\+221)?7[015678]{7}$", message = "Format de téléphone sénégalais invalide")
    private String telephone;

//    @NotNull(message = "Le rôle utilisateur est obligatoire")
//    private UserRole userRole;

    @NotBlank(message = "Le prénom est obligatoire")
    @Size(min = 2, max = 50, message = "Le prénom doit contenir entre 2 et 50 caractères")
    private String prenom;

    private String password;

    @NotBlank(message = "Le nom est obligatoire")
    @Size(min = 2, max = 50, message = "Le nom doit contenir entre 2 et 50 caractères")
    private String nom;

    private String adresse;

    private String groupeSanguin;

    // ID de la localité (stocké en base) - généralement la commune
    //private Long localityId;

    //private Long centreId;

    private String profession;

    private String nomTuteur1;

    private String nomTuteur2;

    private String prenomTuteur1;

    private String prenomTuteur2;

    private String numeroTuteur1;

    private String numeroTuteur2;

    private String niveauEtude;

    private LangueMaternelle langueMaternelle;

    // Informations médicales
    private String allergies;

    private String antecedentsMedicaux;

    //Informations Pour prediction
    private NiveauInstruction niveauInstruction;

    private Long nombre_enfants;

    private String retard_vaccinal;

    private float distance_centre_sante;

    private  String acces_transport;

    private ZoneResidence zoneResidence;

    private NiveauRevenu niveauRevenu;
}

package com.gestionvaccination.userservice.mapper;

import com.gestionvaccination.userservice.client.dto.CentreDTO;
import com.gestionvaccination.userservice.client.dto.LocalityDTO;
import lombok.AllArgsConstructor;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import com.gestionvaccination.userservice.dto.*;
import com.gestionvaccination.userservice.entites.Utilisateur;
//import com.gestionvaccination.userservice.services.EntityEnrichmentService;

/**
 * Mapper pour la conversion entre Utilisateur et ses DTOs
 */
@Service
@AllArgsConstructor
public class UtilisateurMapper {

    PasswordEncoder passwordEncoder()
    {
        return new BCryptPasswordEncoder();
    }
    //private final EnfantMapper enfantMapper;

    /**
     * Convertir une entité Utilisateur en UtilisateurDTO
     */
    public UtilisateurDTO fromEntity(Utilisateur utilisateur) {
        if (utilisateur == null)
            return null;

        UtilisateurDTO utilisateurDTO = new UtilisateurDTO();

        if (utilisateur.getId() != null)
            utilisateurDTO.setId(utilisateur.getId());

        if (utilisateur.getEmail() != null)
            utilisateurDTO.setEmail(utilisateur.getEmail());

        if (utilisateur.getTelephone() != null)
            utilisateurDTO.setTelephone(utilisateur.getTelephone());


        if (utilisateur.getAge() != null)
            utilisateurDTO.setAge(utilisateur.getAge());

        if (utilisateur.getPrenom() != null)
            utilisateurDTO.setPrenom(utilisateur.getPrenom());

        if(utilisateur.getStatutMatrimonial() != null)
            utilisateurDTO.setStatutMatrimonial(utilisateur.getStatutMatrimonial());

        if (utilisateur.getNom() != null)
            utilisateurDTO.setNom(utilisateur.getNom());

        if (utilisateur.getAdresse() != null)
            utilisateurDTO.setAdresse(utilisateur.getAdresse());
        
        if (utilisateur.getGroupeSanguin() != null)
            utilisateurDTO.setGroupeSanguin(utilisateur.getGroupeSanguin());


        if (utilisateur.getProfession() != null)
            utilisateurDTO.setProfession(utilisateur.getProfession());

        if(utilisateur.getLocality() != null)
            utilisateurDTO.setLangueMaternelle(utilisateur.getLangueMaternelle());


//        if (utilisateur.getCreatedAt() != null)
//            utilisateurDTO.setCreatedAt(utilisateur.getCreatedAt());
//
//        if (utilisateur.getUpdatedAt() != null)
//            utilisateurDTO.setUpdatedAt(utilisateur.getUpdatedAt());

        if(utilisateur.getNumeroTuteur1() != null)
            utilisateurDTO.setNumeroTuteur1(utilisateur.getNumeroTuteur1());

        if(utilisateur.getNumeroTuteur2() != null)
            utilisateurDTO.setNumeroTuteur2(utilisateur.getNumeroTuteur2());

        if(utilisateur.getNomTuteur2() != null)
            utilisateurDTO.setNomTuteur2(utilisateur.getNomTuteur2());

        if(utilisateur.getNomTuteur1() != null)
            utilisateurDTO.setNomTuteur1(utilisateur.getNomTuteur1());

        if(utilisateur.getPrenomTuteur1() != null)
            utilisateurDTO.setPrenomTuteur1(utilisateur.getPrenomTuteur1());

        if(utilisateur.getPrenomTuteur2() != null)
            utilisateurDTO.setPrenomTuteur2(utilisateur.getPrenomTuteur2());

        if(utilisateur.getNiveauEtude()!= null)
            utilisateurDTO.setNiveauEtude(utilisateur.getNiveauEtude());

        if(utilisateur.getUserRole()!=null)
            utilisateurDTO.setUserRole(utilisateur.getUserRole());

        // Informations médicales
        if(utilisateur.getAllergies() != null)
            utilisateurDTO.setAllergies(utilisateur.getAllergies());

        if(utilisateur.getAntecedentsMedicaux() != null)
            utilisateurDTO.setAntecedentsMedicaux(utilisateur.getAntecedentsMedicaux());

        if(utilisateur.getNumeroCarnet() != null)
            utilisateurDTO.setNumeroCarnet(utilisateur.getNumeroCarnet());

//        if(utilisateur.getLocalityId()!=null)
//            utilisateurDTO.setLocality(utilisateur.getLocality());


        // CORRECTION: Map the locality and centre DTOs from the enriched entity
        if (utilisateur.getLocality() != null) {
            utilisateurDTO.setLocality(utilisateur.getLocality());
        }

        if (utilisateur.getCentre() != null) {
            utilisateurDTO.setCentre(utilisateur.getCentre());
        }

        if(utilisateur.getPassword()!=null){
            utilisateurDTO.setPassword(utilisateur.getPassword());
        }

        // Informations pour prediction
        if(utilisateur.getNiveauEtude()!= null){
            utilisateurDTO.setNiveauRevenu(utilisateur.getNiveauRevenu());
        }

        if(utilisateur.getNiveauInstruction()!=null){
            utilisateurDTO.setNiveauInstruction(utilisateur.getNiveauInstruction());
        }

        if(utilisateur.getLangueMaternelle()!=null){
            utilisateurDTO.setLangueMaternelle(utilisateur.getLangueMaternelle());
        }

        if(utilisateur.getNiveauEtude()!=null){
            utilisateurDTO.setNiveauEtude(utilisateurDTO.getNiveauEtude());
        }

        if(utilisateur.getZoneResidence()!=null){
            utilisateurDTO.setZoneResidence(utilisateur.getZoneResidence());
        }

        if(utilisateur.getNombre_enfants()!=null){
            utilisateurDTO.setNombre_enfants(utilisateur.getNombre_enfants());
        }

        if(utilisateur.getAcces_transport()!=null){
            utilisateurDTO.setAcces_transport(utilisateur.getAcces_transport());
        }

        if(utilisateur.getRetard_vaccinal()!=null){
            utilisateurDTO.setRetard_vaccinal(utilisateur.getRetard_vaccinal());
        }

//        if(utilisateur.getDistance_centre_sante()!=null){
//            utilisateurDTO.setDistance_centre_sante(utilisateur.getDistance_centre_sante());
//        }

        return utilisateurDTO;
    }



    /**
     * Convertir un SaveUtilisateurDTO en entité Utilisateur
     */
    public Utilisateur fromSaveUtilisateurDTO(SaveUtilisateurDTO saveUtilisateurDTO, LocalityDTO locality, CentreDTO centre) {
        if (saveUtilisateurDTO == null) {
            return null;
        }

        Utilisateur utilisateur = new Utilisateur();

        utilisateur.setEmail(saveUtilisateurDTO.getEmail());
        utilisateur.setTelephone(saveUtilisateurDTO.getTelephone());
        utilisateur.setAge(saveUtilisateurDTO.getAge());
        utilisateur.setStatutMatrimonial(saveUtilisateurDTO.getStatutMatrimonial());
        utilisateur.setPrenom(saveUtilisateurDTO.getPrenom());
        utilisateur.setNom(saveUtilisateurDTO.getNom());
        utilisateur.setAdresse(saveUtilisateurDTO.getAdresse());
        utilisateur.setGroupeSanguin(saveUtilisateurDTO.getGroupeSanguin());
        utilisateur.setNiveauEtude(saveUtilisateurDTO.getNiveauEtude());
        utilisateur.setPrenomTuteur1(saveUtilisateurDTO.getPrenomTuteur1());
        utilisateur.setPrenomTuteur2(saveUtilisateurDTO.getPrenomTuteur2());
        utilisateur.setNomTuteur1(saveUtilisateurDTO.getNomTuteur1());
        utilisateur.setNomTuteur2(saveUtilisateurDTO.getNomTuteur2());
        utilisateur.setNumeroTuteur2(saveUtilisateurDTO.getNumeroTuteur2());
        utilisateur.setNumeroTuteur1(saveUtilisateurDTO.getNumeroTuteur1());
        utilisateur.setNiveauEtude(saveUtilisateurDTO.getNiveauEtude());
        utilisateur.setProfession(saveUtilisateurDTO.getProfession());
        utilisateur.setLangueMaternelle(saveUtilisateurDTO.getLangueMaternelle());

        // Informations médicales
        utilisateur.setAllergies(saveUtilisateurDTO.getAllergies());
        utilisateur.setAntecedentsMedicaux(saveUtilisateurDTO.getAntecedentsMedicaux());




        if(locality.getId()!=null){
            utilisateur.setLocalityId(locality.getId());
            utilisateur.setLocality(locality);
        }else{
            utilisateur.setLocalityId(null);
            utilisateur.setLocality(null);
        }


        if(centre.getId()!=null){
            utilisateur.setCentreId(centre.getId());
            utilisateur.setCentre(centre);
        }else{
            utilisateur.setCentreId(null);
            utilisateur.setCentre(null);
        }


        return utilisateur;
    }


    /**
     * Mettre à jour partiellement un utilisateur
     */
    public Utilisateur partialUpdateUtilisateur(UpdateUtilisateurDTO updateUtilisateurDTO, Utilisateur utilisateur) {
        if (updateUtilisateurDTO == null)
            return null;

        if (updateUtilisateurDTO.getTelephone() != null)
            utilisateur.setTelephone(updateUtilisateurDTO.getTelephone());

        if (updateUtilisateurDTO.getPrenom() != null)
            utilisateur.setPrenom(updateUtilisateurDTO.getPrenom());

        if (updateUtilisateurDTO.getNom() != null)
            utilisateur.setNom(updateUtilisateurDTO.getNom());

        if (updateUtilisateurDTO.getAdresse() != null)
            utilisateur.setAdresse(updateUtilisateurDTO.getAdresse());

        if (updateUtilisateurDTO.getGroupeSanguin() != null)
            utilisateur.setGroupeSanguin(updateUtilisateurDTO.getGroupeSanguin());


        if(updateUtilisateurDTO.getLangueMaternelle() != null)
            utilisateur.setLangueMaternelle(updateUtilisateurDTO.getLangueMaternelle());


        if (updateUtilisateurDTO.getProfession() != null)
            utilisateur.setProfession(updateUtilisateurDTO.getProfession());

        if(updateUtilisateurDTO.getAge()!=null)
            utilisateur.setAge(updateUtilisateurDTO.getAge());

        if(updateUtilisateurDTO.getStatutMatrimonial()!=null)
            utilisateur.setStatutMatrimonial(updateUtilisateurDTO.getStatutMatrimonial());

        if(updateUtilisateurDTO.getNiveauEtude()!=null)
            utilisateur.setNiveauEtude(updateUtilisateurDTO.getNiveauEtude());

        if(updateUtilisateurDTO.getPrenomTuteur1()!=null)
            utilisateur.setPrenomTuteur1(updateUtilisateurDTO.getPrenomTuteur1());

        if(updateUtilisateurDTO.getPrenomTuteur2()!=null)
            utilisateur.setPrenomTuteur2(updateUtilisateurDTO.getPrenomTuteur2());

        if(updateUtilisateurDTO.getNomTuteur1()!=null)
            utilisateur.setNomTuteur1(updateUtilisateurDTO.getNomTuteur1());

        if(updateUtilisateurDTO.getNomTuteur2()!=null)
            utilisateur.setNomTuteur2(updateUtilisateurDTO.getNomTuteur2());

        if(updateUtilisateurDTO.getNumeroTuteur1()!=null)
            utilisateur.setNumeroTuteur1(updateUtilisateurDTO.getNumeroTuteur1());

        if(updateUtilisateurDTO.getNumeroTuteur2()!=null)
            utilisateur.setNumeroTuteur2(updateUtilisateurDTO.getNumeroTuteur2());

        if(updateUtilisateurDTO.getAllergies()!=null)
            utilisateur.setAllergies(updateUtilisateurDTO.getAllergies());

        if(updateUtilisateurDTO.getAntecedentsMedicaux()!=null)
            utilisateur.setAntecedentsMedicaux(updateUtilisateurDTO.getAntecedentsMedicaux());

        if(updateUtilisateurDTO.getNumeroCarnet()!=null)
            utilisateur.setNumeroCarnet(updateUtilisateurDTO.getNumeroCarnet());

        return utilisateur;

    }



    public Utilisateur fromSavingInfirmier(SaveInfirmierDTO saveInfirmierDTO,CentreDTO centre) {
        if(saveInfirmierDTO == null || centre == null){
            return null;
        }

        Utilisateur utilisateur = new Utilisateur();
        utilisateur.setPrenom(saveInfirmierDTO.getPrenom());
        utilisateur.setNom(saveInfirmierDTO.getNom());
        utilisateur.setEmail(saveInfirmierDTO.getEmail());
        utilisateur.setTelephone(saveInfirmierDTO.getPhone());
        utilisateur.setMatricule(saveInfirmierDTO.getMatricule());
        utilisateur.setDateEmbauche(saveInfirmierDTO.getDateEmbauche());
        utilisateur.setAge(saveInfirmierDTO.getAge());
        utilisateur.setPassword(passwordEncoder().encode(saveInfirmierDTO.getPassword()));

        if(centre.getId()!=null){
            utilisateur.setCentreId(centre.getId());
            utilisateur.setCentre(centre);
        }else{
            utilisateur.setCentreId(null);
            utilisateur.setCentre(null);
        }

        return utilisateur;
    }



    public Utilisateur fromSavingParentDTO(SaveParentDTO saveParentDTO,CentreDTO centre) {
        if(saveParentDTO == null || centre == null){
            return null;
        }

        Utilisateur utilisateur = new Utilisateur();
        utilisateur.setPrenom(saveParentDTO.getPrenom());
        utilisateur.setNom(saveParentDTO.getNom());
        utilisateur.setEmail(saveParentDTO.getEmail());
        utilisateur.setStatutMatrimonial(saveParentDTO.getStatutMatrimonial());
        utilisateur.setAge(saveParentDTO.getAge());
        utilisateur.setGroupeSanguin(saveParentDTO.getGroupeSanguin());
        utilisateur.setNiveauEtude(saveParentDTO.getNiveauEtude());
        utilisateur.setProfession(saveParentDTO.getProfession());
        utilisateur.setPrenomTuteur1(saveParentDTO.getPrenomTuteur1());
        utilisateur.setPrenomTuteur2(saveParentDTO.getPrenomTuteur2());
        utilisateur.setNumeroTuteur1(saveParentDTO.getNumeroTuteur1());
        utilisateur.setNumeroTuteur2(saveParentDTO.getNumeroTuteur2());
        utilisateur.setNomTuteur1(saveParentDTO.getNomTuteur1());
        utilisateur.setNomTuteur2(saveParentDTO.getNomTuteur2());
        utilisateur.setTelephone(saveParentDTO.getTelephone());
        utilisateur.setPassword(passwordEncoder().encode(saveParentDTO.getPassword()));
        utilisateur.setLangueMaternelle(saveParentDTO.getLangueMaternelle());

        // Informations médicales
        utilisateur.setAllergies(saveParentDTO.getAllergies());
        utilisateur.setAntecedentsMedicaux(saveParentDTO.getAntecedentsMedicaux());

        // Informations pour la prediction
        utilisateur.setNiveauEtude(saveParentDTO.getNiveauEtude());
        utilisateur.setNiveauRevenu(saveParentDTO.getNiveauRevenu());
        utilisateur.setNiveauInstruction(saveParentDTO.getNiveauInstruction());
        utilisateur.setDistance_centre_sante(saveParentDTO.getDistance_centre_sante());
        utilisateur.setNombre_enfants(saveParentDTO.getNombre_enfants());
        utilisateur.setZoneResidence(saveParentDTO.getZoneResidence());
        utilisateur.setAcces_transport(saveParentDTO.getAcces_transport());
        utilisateur.setRetard_vaccinal(saveParentDTO.getRetard_vaccinal());

        if(centre.getId()!=null){
            utilisateur.setCentreId(centre.getId());
            utilisateur.setCentre(centre);
        }else{
            utilisateur.setCentreId(null);
            utilisateur.setCentre(null);
        }

        return utilisateur;

    }


}

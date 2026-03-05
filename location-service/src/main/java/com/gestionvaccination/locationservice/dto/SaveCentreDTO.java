package com.gestionvaccination.locationservice.dto;


import lombok.Data;

@Data
public class SaveCentreDTO {

    private String name;

    private String phone;


    // private CentreType  type;

    private Long locationId;

    private Long parentId; // Id Null si type est DISTRICT
}

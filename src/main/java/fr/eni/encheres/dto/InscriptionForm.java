package fr.eni.encheres.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Data;

@Data
public class InscriptionForm {

    @NotBlank
//    @Size(min=3, max=30)
    private String pseudo;

    @NotBlank
//    @Size(min=3, max=40)
    private String nom;

//    @NotBlank
//    @Size(min=3, max=50)
    private String prenom;

//    @NotBlank
//    @Email
    private String email;

    private String telephone;

//    @NotBlank
//    @Size(min=6, message = "Mot de passe trop court")
    private String mdp;

//    @NotBlank
    private String mdpConfirm;

//    @NotBlank
    private String rue;

//    @NotBlank
    private String codePostal;

//    @NotBlank
    private String ville;
}

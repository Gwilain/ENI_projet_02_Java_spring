package fr.eni.encheres.dto;
import jakarta.persistence.*;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Data;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDate;

@Data
public class VenteFormDTO {

    private int id;


    @NotBlank(message = "Le nom de l'article est obligatoire")
    @Size(max = 30)
    private String nom;

    @NotBlank(message = "La description est obligatoire")
    @Size(max = 300)
    private String description;

    // Champ facultatif si géré ailleurs (upload ?)
    private Integer photo;

    @NotNull(message = "La date de début est obligatoire")
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private LocalDate dateDebut;

    @NotNull(message = "La date de fin est obligatoire")
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private LocalDate dateFin;

    @NotNull(message = "Le prix est obligatoire")
    @Min(value = 1, message = "Le prix doit être supérieur à 0")
    private Integer prixInitial;

    @NotNull(message = "Veuillez sélectionner une catégorie")
    private Integer categorieId;

    @NotNull(message = "Veuillez sélectionner une adresse de retrait")
    private Integer adresseRetraitId;
}
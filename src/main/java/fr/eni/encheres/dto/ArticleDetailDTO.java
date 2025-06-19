package fr.eni.encheres.dto;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Data;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDate;

@Data
public class ArticleDetailDTO {

    private int id;
    private String nom;
    private String description;
    private String photoUrl;
    private LocalDate dateDebut;
    private LocalDate dateFin;
    private Integer prixInitial;
    private Integer prixActuel;
    private String pseudoVendeur;
    private String nomCategorie;
    private String adresseRetrait;
    private boolean modifiable;

}

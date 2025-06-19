package fr.eni.encheres.dto;

import fr.eni.encheres.model.ArticleAVendre;
import fr.eni.encheres.model.Utilisateur;
import jakarta.persistence.Column;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import lombok.Data;

import java.time.LocalDateTime;

@Data
public class EnchereDTO {

//    private Utilisateur encherisseur;

    private int idArticle;

    private int montant;

}

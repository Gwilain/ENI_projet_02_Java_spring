package fr.eni.encheres.model;
import jakarta.persistence.*;
import lombok.Data;
import lombok.ToString;

import java.time.LocalDate;

@Data
@Entity
@Table(name = "ARTICLES_A_VENDRE")
public class ArticleAVendre {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "no_article")
    private Integer id;

    @Column(name = "nom_article", nullable = false, length = 30)
    private String nom;

    @Column(name = "description", nullable = false, length = 300)
    private String description;

    @Column(name = "photo")
    private Integer photo;

    @Column(name = "date_debut_encheres", nullable = false)
    private LocalDate dateDebutEncheres;

    @Column(name = "date_fin_encheres", nullable = false)
    private LocalDate dateFinEncheres;

    @Column(name = "statut_enchere", nullable = false)
    private Integer statutEnchere;

    @Column(name = "prix_initial", nullable = false)
    private Integer prixInitial;

    @Column(name = "prix_vente")
    private Integer prixVente;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "id_utilisateur", nullable = false)
    private Utilisateur vendeur;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "no_categorie", nullable = false)
    @ToString.Exclude
    private Categorie categorie;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "no_adresse_retrait", nullable = false)
    @ToString.Exclude
    private Adresse adresseRetrait;

}

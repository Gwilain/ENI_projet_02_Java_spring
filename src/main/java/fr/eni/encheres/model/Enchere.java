package fr.eni.encheres.model;

import jakarta.persistence.*;
import lombok.Data;

import java.io.Serializable;
import java.time.LocalDate;
import java.time.LocalDateTime;

@Data
@Entity
@Table(name = "ENCHERES")
public class Enchere implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @ManyToOne
    @JoinColumn(name = "id_utilisateur", nullable = false)
    private Utilisateur encherisseur;

    @Id
    @ManyToOne
    @JoinColumn(name = "no_article", nullable = false)
    private ArticleAVendre articleAVendre;

    @Column(name = "montant_enchere", nullable = false)
    private int montant;

    @Column(name = "date_enchere", nullable = false)
    private LocalDateTime date;
}

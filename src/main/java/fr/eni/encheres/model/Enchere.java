package fr.eni.encheres.model;

import jakarta.persistence.*;
import lombok.Data;

import java.time.LocalDate;

//@Data
//@Entity
//@Table(name = "ENCHERES")
//@IdClass(EncherePK.class)
public class Enchere {

//    @Column(name = "date_enchere")
    private LocalDate date;

//    @Id
//    @Column(name = "montant_enchere")
    private int montant;

    //@Id
    //@ManyToOne
    @JoinColumn(name = "no_article", nullable = false)
    //private ArticleAVendre articleAVendre;

    @Id
    @ManyToOne
    @JoinColumn(name = "id_utilisateur")
    private Utilisateur acquereur;





}

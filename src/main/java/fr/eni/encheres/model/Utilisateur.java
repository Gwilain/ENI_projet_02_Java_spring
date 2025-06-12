package fr.eni.encheres.model;

import jakarta.persistence.*;

import jakarta.validation.constraints.Size;
import lombok.Data;
import org.antlr.v4.runtime.misc.NotNull;

@Data
@Entity
@Table(name = "UTILISATEURS")
public class Utilisateur {

    @Id
    @NotNull
    @Size(min= 3, max = 30)
    @Column(length = 30, nullable = false, unique = true)
    private String pseudo;

    @NotNull
    @Size(min= 3, max = 40)
    private String nom;

    @NotNull
    @Size(min= 3, max = 50)
    private String prenom;

    @NotNull
    @Column(length = 100, nullable = false, unique = true)
    private String email;

    @Column(length = 15)
    private String telephone;

    @Column(name = "mot_de_passe", length = 68, nullable = false)
    private String motDePasse;

    @Column(nullable = false)
    private int credit = 10;

    @Column(name = "administrateur", nullable = false)
    private boolean admin = false;

//    @OneToOne(cascade = CascadeType.ALL, orphanRemoval = true)
//    @JoinColumn(name = "no_adresse", nullable = false)
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "no_adresse", nullable = false)
    private Adresse adresse;
}

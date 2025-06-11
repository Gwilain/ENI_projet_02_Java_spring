package fr.eni.encheres.model;

import jakarta.persistence.*;
import lombok.Data;

@Data
@Entity
@Table(name = "ADRESSES")
public class Adresse {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "no_adresse")
    private Long id;



    private String rue;

    @Column(name = "code_postal")
    private String codePostal;

    private String ville;


    @Column(name = "adresse_eni")
    private boolean adresseEni;
}

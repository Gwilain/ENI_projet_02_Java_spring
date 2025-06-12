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
    private Integer id;

    @Column(name = "rue", nullable = false)
    private String rue;

    @Column(name = "code_postal", nullable = false)
    private String codePostal;

    @Column(name = "ville", nullable = false)
    private String ville;

    @Column(name = "adresse_eni", nullable = false)
    private boolean adresseEni;
}

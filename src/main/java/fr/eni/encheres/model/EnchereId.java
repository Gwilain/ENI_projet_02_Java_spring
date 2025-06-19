package fr.eni.encheres.model;

import lombok.Data;

import java.io.Serializable;

@Data
public class EnchereId implements Serializable {
    private String encherisseur;
    private Integer articleAVendre;
    private Integer montant;
}

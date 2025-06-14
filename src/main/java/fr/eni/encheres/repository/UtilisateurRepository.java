package fr.eni.encheres.repository;

import fr.eni.encheres.model.Utilisateur;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository  // facultatif, Spring détecte automatiquement les interfaces JpaRepository
public interface UtilisateurRepository extends JpaRepository<Utilisateur, Long> {
    boolean existsByEmail(String email);
    boolean existsByPseudo(String pseudo);

    Utilisateur findByEmail(String email);
    Utilisateur findByPseudo(String email);
}

package fr.eni.encheres.repository;

import fr.eni.encheres.model.Enchere;
import fr.eni.encheres.model.EnchereId;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface EnchereRepository extends JpaRepository<Enchere, EnchereId>{

    Optional<Enchere> findTopByArticleAVendreIdOrderByMontantDesc(Long articleId);

}

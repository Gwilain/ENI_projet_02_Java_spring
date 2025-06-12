package fr.eni.encheres.repository;

import fr.eni.encheres.model.ArticleAVendre;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ArticleAVendreRepository extends JpaRepository<ArticleAVendre, Integer> {
    List<ArticleAVendre> findByVendeurPseudo(String pseudo);

}

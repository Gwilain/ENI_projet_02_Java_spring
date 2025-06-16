package fr.eni.encheres.repository;

import fr.eni.encheres.model.ArticleAVendre;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

@Repository
public interface ArticleAVendreRepository extends JpaRepository<ArticleAVendre, Long> {
//    List<ArticleAVendre> findByVendeurPseudo(String pseudo);
    List<ArticleAVendre> findByCategorieId(Integer categorieId);

    //    List<ArticleAVendre> findByDateDebutEncheresBeforeAndDateFinEncheresAfterOrderByDateFinEncheresAsc(
    //            LocalDate now1, LocalDate now2);

    @Query("SELECT a FROM ArticleAVendre a WHERE :now BETWEEN a.dateDebutEncheres AND a.dateFinEncheres ORDER BY a.dateFinEncheres ASC")
    List<ArticleAVendre> findEncheresEnCours(@Param("now") LocalDate now);



}

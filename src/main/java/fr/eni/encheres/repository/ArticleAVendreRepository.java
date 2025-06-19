package fr.eni.encheres.repository;

import fr.eni.encheres.model.ArticleAVendre;
import fr.eni.encheres.model.Categorie;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

@Repository
public interface ArticleAVendreRepository extends JpaRepository<ArticleAVendre, Long> {

    List<ArticleAVendre> findByCategorieId(Integer categorieId);

    //    List<ArticleAVendre> findByDateDebutEncheresBeforeAndDateFinEncheresAfterOrderByDateFinEncheresAsc(
    //            LocalDate now1, LocalDate now2);

    @Query("SELECT a FROM ArticleAVendre a WHERE :now BETWEEN a.dateDebutEncheres AND a.dateFinEncheres ORDER BY a.dateFinEncheres ASC")
    List<ArticleAVendre> findEncheresEnCours(@Param("now") LocalDate now);

    @Query("""
    SELECT a FROM ArticleAVendre a 
        WHERE a.categorie.id = :categorieId
        AND :now BETWEEN a.dateDebutEncheres AND a.dateFinEncheres ORDER BY a.dateFinEncheres ASC
    """)
    List<ArticleAVendre> findEncheresEnCoursByCategorie(@Param("now") LocalDate now, @Param("categorieId") Integer categorieId );


    @Query("""
    SELECT DISTINCT e.articleAVendre
    FROM Enchere e
    WHERE e.encherisseur.pseudo = :pseudo
      AND CURRENT_DATE BETWEEN e.articleAVendre.dateDebutEncheres AND e.articleAVendre.dateFinEncheres
""")
    List<ArticleAVendre> findArticlesEnCoursEncherisPar(@Param("pseudo") String pseudo);

    @Query("""
    SELECT DISTINCT e.articleAVendre
    FROM Enchere e
    WHERE e.encherisseur.pseudo = :pseudo
      AND e.articleAVendre.categorie.id = :categorieId
      AND CURRENT_DATE BETWEEN e.articleAVendre.dateDebutEncheres AND e.articleAVendre.dateFinEncheres
""")
    List<ArticleAVendre> findArticlesEnCoursEncherisParEtCategorie(@Param("pseudo") String pseudo, @Param("categorieId") Integer categorieId);



    @Query("""
    SELECT e.articleAVendre FROM Enchere e
    WHERE e.encherisseur.pseudo = :pseudo
    AND e.montant = (
        SELECT MAX(e2.montant) FROM Enchere e2
        WHERE e2.articleAVendre.id = e.articleAVendre.id
    )
    AND e.articleAVendre.dateFinEncheres < :now
""")
    List<ArticleAVendre> findArticlesRemportesByPseudo(@Param("pseudo") String pseudo, @Param("now") LocalDate now);


    @Query("""
    SELECT e.articleAVendre FROM Enchere e
    WHERE e.encherisseur.pseudo = :pseudo
    AND e.articleAVendre.categorie.id = :categorieId
    AND e.montant = (
        SELECT MAX(e2.montant) FROM Enchere e2
        WHERE e2.articleAVendre.id = e.articleAVendre.id
    )
    AND e.articleAVendre.dateFinEncheres < :now
""")
    List<ArticleAVendre> findArticlesRemportesByPseudoAndCategorie(@Param("pseudo") String pseudo, @Param("now") LocalDate now, @Param("categorieId") Integer categorieId);



    @Query("""
    SELECT a FROM ArticleAVendre a
    WHERE a.vendeur.pseudo = :pseudo
      AND :now BETWEEN a.dateDebutEncheres AND a.dateFinEncheres
""")
    List<ArticleAVendre> findVentesEnCoursByPseudo(@Param("pseudo") String pseudo, @Param("now") LocalDate now);


    @Query("""
    SELECT a FROM ArticleAVendre a
    WHERE a.vendeur.pseudo = :pseudo
      AND a.categorie.id = :categorieId
      AND :now BETWEEN a.dateDebutEncheres AND a.dateFinEncheres
""")
    List<ArticleAVendre> findVentesEnCoursByPseudoAndCategorie(@Param("pseudo") String pseudo, @Param("categorieId") Integer categorieId, @Param("now") LocalDate now);


    @Query("""
    SELECT a FROM ArticleAVendre a
    WHERE a.vendeur.pseudo = :pseudo
      AND a.dateDebutEncheres > :now
""")
    List<ArticleAVendre> findVentesNonDebuteesByPseudo(@Param("pseudo") String pseudo, @Param("now") LocalDate now);

    @Query("""
    SELECT a FROM ArticleAVendre a
    WHERE a.vendeur.pseudo = :pseudo
      AND a.categorie.id = :categorieId
      AND a.dateDebutEncheres > :now
""")
    List<ArticleAVendre> findVentesNonDebuteesByPseudoAndCategorie(@Param("pseudo") String pseudo, @Param("categorieId") Integer categorieId, @Param("now") LocalDate now);


    @Query("""
    SELECT a FROM ArticleAVendre a
    WHERE a.vendeur.pseudo = :pseudo
      AND a.dateFinEncheres < :now
""")
    List<ArticleAVendre> findVentesTermineesByPseudo(@Param("pseudo") String pseudo, @Param("now") LocalDate now);


    @Query("""
    SELECT a FROM ArticleAVendre a
    WHERE a.vendeur.pseudo = :pseudo
      AND a.categorie.id = :categorieId
      AND a.dateFinEncheres < :now
""")
    List<ArticleAVendre> findVentesTermineesByPseudoAndCategorie(@Param("pseudo") String pseudo, @Param("categorieId") Integer categorieId, @Param("now") LocalDate now);


    ArticleAVendre getArticleAVendreById(Integer id);
}

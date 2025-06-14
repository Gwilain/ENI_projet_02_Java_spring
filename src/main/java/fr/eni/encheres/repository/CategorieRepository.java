package fr.eni.encheres.repository;

import fr.eni.encheres.model.ArticleAVendre;
import fr.eni.encheres.model.Categorie;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface CategorieRepository extends JpaRepository<Categorie, Integer> {

//        List<ArticleAVendre> findByCategorieId(long categorieId);

}

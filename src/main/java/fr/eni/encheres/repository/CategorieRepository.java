package fr.eni.encheres.repository;

import fr.eni.encheres.model.ArticleAVendre;
import fr.eni.encheres.model.Categorie;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CategorieRepository extends JpaRepository<Categorie, Integer> {

}

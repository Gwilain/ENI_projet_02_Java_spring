package fr.eni.encheres.service;

import fr.eni.encheres.model.Categorie;
import fr.eni.encheres.repository.CategorieRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CategorieService {

    private final List<Categorie> categories;

    public CategorieService(CategorieRepository categoryRepository) {

        this.categories = categoryRepository.findAll();
    }

    public List<Categorie> getAllCategories() {
        return categories;
    }

//    @Autowired
//    private CategorieRepository categorieRepository;
//
//        public List<Categorie> findAll() {
//
//        return categorieRepository.findAll();
//    };
}

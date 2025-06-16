package fr.eni.encheres.service;

import fr.eni.encheres.model.ArticleAVendre;
import fr.eni.encheres.repository.ArticleAVendreRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;

@Service
public class ArticleService {

    @Autowired
    ArticleAVendreRepository articleAVendreRepository;

    public List<ArticleAVendre> findAll(){

//        return articleAVendreRepository.findAll();
        return articleAVendreRepository.findEncheresEnCours(LocalDate.now());
    };

    public List<ArticleAVendre> findByCategorieId(Integer id){

        return articleAVendreRepository.findByCategorieId(id);
    }

//    public List<ArticleAVendre> findByNomContainingIgnoreCase(){
//
//
//        return lst;
//    }
}

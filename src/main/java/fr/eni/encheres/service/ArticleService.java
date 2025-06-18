package fr.eni.encheres.service;

import fr.eni.encheres.controller.filter.ArticleChainFilter;
import fr.eni.encheres.dto.VenteFormDTO;
import fr.eni.encheres.model.Adresse;
import fr.eni.encheres.model.ArticleAVendre;
import fr.eni.encheres.model.Categorie;
import fr.eni.encheres.model.Utilisateur;
import fr.eni.encheres.repository.AdresseRepository;
import fr.eni.encheres.repository.ArticleAVendreRepository;
import fr.eni.encheres.repository.CategorieRepository;
import jakarta.persistence.Convert;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Service
public class ArticleService {

    @Autowired
    ArticleAVendreRepository articleAVendreRepository;

    @Autowired
    CategorieRepository categorieRepository;

    @Autowired
    AdresseRepository adresseRepository;

    public List<ArticleAVendre> findAll(){

//        return articleAVendreRepository.findAll();
        return articleAVendreRepository.findEncheresEnCours(LocalDate.now());
    };

    public List<ArticleAVendre> findByCategorieId(Integer id){

        return articleAVendreRepository.findByCategorieId(id);
    }


    public void registerVente(VenteFormDTO dto, Utilisateur user) {
        ArticleAVendre article = fromDto(dto, user);

        articleAVendreRepository.save(article);
    }

    public List<ArticleAVendre> find( Boolean achats, Integer categorieId, String contient, Integer typeEnchere, String pseudo){

        List<ArticleAVendre> articles = new ArrayList<>();

        if(achats){
            articles = findAchat(categorieId, contient, typeEnchere, pseudo);
        }else{
            articles = findVente(categorieId, contient, typeEnchere, pseudo);
        }

        articles = ArticleChainFilter.contains(articles, contient );

        return articles;
    }

    public List<ArticleAVendre> findAchat(Integer categorieId, String contient, Integer typeEnchere, String pseudo){

        List<ArticleAVendre> articles = new ArrayList<>();

        if( typeEnchere == 0 ){

            System.out.println("typeEnchere == 0");

            if (categorieId != null) {
                articles = articleAVendreRepository.findEncheresEnCoursByCategorie(LocalDate.now(), categorieId);
            } else {
                articles = articleAVendreRepository.findEncheresEnCours(LocalDate.now());
            }

        }else if(typeEnchere == 1){

            if (categorieId != null) {
                articles = articleAVendreRepository.findArticlesEnCoursEncherisParEtCategorie(pseudo, categorieId);
            } else {
                articles = articleAVendreRepository.findArticlesEnCoursEncherisPar(pseudo);
            }

        }else if(typeEnchere == 2){

            if (categorieId != null) {
                articles = articleAVendreRepository.findArticlesRemportesByPseudoAndCategorie(pseudo, LocalDate.now(), categorieId);
            } else {
                articles = articleAVendreRepository.findArticlesRemportesByPseudo(pseudo, LocalDate.now());
            }

        }

       // articles = ArticleChainFilter.contains(articles, contient );

        return articles;
    }

    public List<ArticleAVendre> findVente(Integer categorieId, String contient, Integer typeEnchere, String pseudo){

        List<ArticleAVendre> articles = new ArrayList<>();

        if( typeEnchere == 0 ){
            System.out.println("typeEnchere == 0");

            if (categorieId != null) {
                articles = articleAVendreRepository.findVentesEnCoursByPseudoAndCategorie(pseudo, categorieId,LocalDate.now());
            } else {
                articles = articleAVendreRepository.findVentesEnCoursByPseudo(pseudo,LocalDate.now());
            }

        }else if(typeEnchere == 1){

            if (categorieId != null) {
                articles = articleAVendreRepository.findVentesNonDebuteesByPseudoAndCategorie(pseudo, categorieId, LocalDate.now());
            } else {
                articles = articleAVendreRepository.findVentesNonDebuteesByPseudo(pseudo, LocalDate.now());
            }

        }else if(typeEnchere == 2){

            if (categorieId != null) {
                articles = articleAVendreRepository.findVentesTermineesByPseudoAndCategorie(pseudo, categorieId, LocalDate.now());
            } else {
                articles = articleAVendreRepository.findVentesTermineesByPseudo(pseudo, LocalDate.now());
            }
        }

        // articles = ArticleChainFilter.contains(articles, contient );

        return articles;
    }


    public ArticleAVendre fromDto(VenteFormDTO dto, Utilisateur vendeur) {

        Categorie categorie = categorieRepository.findById(dto.getCategorieId())
                .orElseThrow(() -> new IllegalArgumentException("Catégorie non trouvée"));
        Adresse adresse = adresseRepository.findById(dto.getAdresseRetraitId())
                .orElseThrow(() -> new IllegalArgumentException("Adresse non trouvée"));

        ArticleAVendre article = new ArticleAVendre();
        article.setNom(dto.getNom());
        article.setDescription(dto.getDescription());
        article.setPhoto(dto.getPhoto());
        article.setDateDebutEncheres(dto.getDateDebut());
        article.setDateFinEncheres(dto.getDateFin());
        article.setPrixInitial(dto.getPrixInitial());
        article.setStatutEnchere(0); // par défaut, ex: "à venir"
        article.setCategorie(categorie);
        article.setAdresseRetrait(adresse);
        article.setVendeur(vendeur);

        System.out.println("article a enregistrer en base : "+article);
        return article;
    }

    public VenteFormDTO toDto(ArticleAVendre article) {
        VenteFormDTO dto = new VenteFormDTO();

        dto.setNom(article.getNom());
        dto.setDescription(article.getDescription());
        dto.setPhoto(article.getPhoto());
        dto.setDateDebut(article.getDateDebutEncheres());
        dto.setDateFin(article.getDateFinEncheres());
        dto.setPrixInitial(article.getPrixInitial());

        dto.setCategorieId(article.getCategorie().getId());
        dto.setAdresseRetraitId(article.getAdresseRetrait().getId());

        return dto;
    }

}

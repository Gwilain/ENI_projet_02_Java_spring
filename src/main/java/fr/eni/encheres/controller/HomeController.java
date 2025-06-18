package fr.eni.encheres.controller;

import fr.eni.encheres.controller.filter.ArticleChainFilter;
import fr.eni.encheres.dto.VenteFormDTO;
import fr.eni.encheres.model.Adresse;
import fr.eni.encheres.model.ArticleAVendre;
import fr.eni.encheres.model.Categorie;
import fr.eni.encheres.model.Utilisateur;
import fr.eni.encheres.service.AdresseService;
import fr.eni.encheres.service.ArticleService;
import fr.eni.encheres.service.CategorieService;
import jakarta.servlet.http.HttpSession;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.time.LocalDate;
import java.util.List;

@Controller
public class HomeController {

    @Autowired
    private ArticleService articleService;

    @Autowired
    private AdresseService adresseService;

    private CategorieService categorieService;


    public HomeController(CategorieService categorieService){
        this.categorieService = categorieService;
    }


    @GetMapping("/")
    public String afficherIndex(@RequestParam(value = "categorieId", required = false) Integer categorieId,
                                @RequestParam(value = "contient", required = false) String contient,
                                @RequestParam(value = "typeEnchere", required = false) Integer typeEnchere,
                                HttpSession session,
                                Model model) {

        List<Categorie> categories = categorieService.getAllCategories();
        model.addAttribute("categories", categories);

        List<ArticleAVendre> articles;

        if (categorieId != null) {
//            articles = articleService.findByCategorieId( categorieId);
            model.addAttribute("selectedCategorieId", categorieId);
//            System.out.println("categorieId is not null find by date and id all");

        } else {
//            System.out.println("categorieId is null find all");
//            articles = articleService.findAll();
        }

        Utilisateur user = (Utilisateur)  session.getAttribute("userInSession");

        String pseudo = (user != null) ? user.getPseudo() : "";

        typeEnchere = (typeEnchere != null ) ? typeEnchere : 0;

        articles = articleService.find( categorieId, contient, typeEnchere, pseudo );

        model.addAttribute("typeEnchere", typeEnchere);
        model.addAttribute("contient", contient);

        articles = ArticleChainFilter.contains(articles, contient );

        model.addAttribute("articles", articles);
        model.addAttribute("isAchat", true);
        return "index";
    }



    @GetMapping("/encheres")
    public String afficherEncheres(@RequestParam(value = "categorieId", required = false) Integer categorieId,
                                @RequestParam(value = "contient", required = false) String contient,
                                @RequestParam(value = "typeEnchere", required = false) Integer typeEnchere,
                                Model model) {

        List<Categorie> categories = categorieService.getAllCategories();
        model.addAttribute("categories", categories);

        List<ArticleAVendre> articles;

        if (categorieId != null) {
            articles = articleService.findByCategorieId( categorieId);
            model.addAttribute("selectedCategorieId", categorieId);
        } else {
            articles = articleService.findAll();
        }

        model.addAttribute("typeEnchere", 0);


        articles = ArticleChainFilter.contains(articles, contient );

        model.addAttribute("articles", articles);
        model.addAttribute("isAchat", true);
        return "index";
    }



}

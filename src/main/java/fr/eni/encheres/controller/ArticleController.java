package fr.eni.encheres.controller;

import fr.eni.encheres.dto.VenteFormDTO;
import fr.eni.encheres.model.Adresse;
import fr.eni.encheres.model.ArticleAVendre;
import fr.eni.encheres.model.Categorie;
import fr.eni.encheres.model.Utilisateur;
import fr.eni.encheres.service.AdresseService;
import fr.eni.encheres.service.ArticleService;
import fr.eni.encheres.service.CategorieService;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.time.LocalDate;
import java.util.List;

@Controller
public class ArticleController {

    @Autowired
    private ArticleService articleService;

    @Autowired
    private CategorieService categorieService;

    @Autowired
    private AdresseService adresseService;
    
    private List<Categorie> categories;


    @GetMapping("/")
    public String afficherIndex(@RequestParam(value = "categorieId", required = false) Integer categorieId,
                                @RequestParam(value = "contient", required = false) String contient,
                                Model model) {

        System.out.println(" afficherIndex ");

        categories = categorieService.findAll();
        List<ArticleAVendre> articles;

        if (categorieId != null) {
            articles = articleService.findByCategorieId( categorieId);
            model.addAttribute("selectedCategorieId", categorieId);
        } else {
            articles = articleService.findAll();
        }

        if(contient != null  && !contient.isBlank()) {

            String recherche = contient.toLowerCase();
            articles = articles.stream()
                    .filter(a -> a.getNom() != null && a.getNom().toLowerCase().contains(recherche))
                    .toList();
            model.addAttribute("contient", contient);
        }

        model.addAttribute("categories", categories);
        model.addAttribute("articles", articles);
        return "index";
    }


    @GetMapping("/vendre")
    public String vendre(HttpSession session, Model model) {

        VenteFormDTO form = new VenteFormDTO();
        form.setDateDebut(LocalDate.now());
        form.setDateFin(LocalDate.now().plusDays(30));

        model.addAttribute("articleAVendre", form);

        if (categories == null || categories.isEmpty()) {
            categories = categorieService.findAll();
            model.addAttribute("categories", categories);
        }

        Utilisateur user = (Utilisateur)  session.getAttribute("userInSession");
        Adresse userAdresse  = user.getAdresse();
        System.out.println("userAdresse = " + userAdresse);

        List<Adresse> adressesEni = adresseService.findAdressesEni();

        model.addAttribute("adresseVendeur", userAdresse);
        model.addAttribute("adressesEni", adressesEni);


        return "vendre";
    }

}

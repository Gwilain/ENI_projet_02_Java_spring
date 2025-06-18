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
import jakarta.servlet.http.HttpServletRequest;
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


    @GetMapping({"/", "/mes-encheres-en-cours", "/mes-encheres-remportees"})
    public String achats(
            HttpServletRequest request,
            HttpSession session,
            Model model,
            @RequestParam(value = "categorieId", required = false) Integer categorieId,
            @RequestParam(value = "contient", required = false) String contient) {

        String path = request.getServletPath();
        int typeEnchere = switch (path) {
            case "/mes-encheres-en-cours" -> 1;
            case "/mes-encheres-remportees" -> 2;
            default -> 0;
        };

        String currentPath = request.getServletPath();
        model.addAttribute("formActionUrl", currentPath);

        String baseParams = "";
        if (categorieId != null) {
            baseParams += "?categorieId=" + categorieId;
        }
        if (contient != null && !contient.isBlank()) {
            baseParams += (baseParams.isEmpty() ? "?" : "&") + "contient=" + contient;
        }

        model.addAttribute("urlType0", "/" + baseParams);
        model.addAttribute("urlType1", "/mes-encheres-en-cours" + baseParams);
        model.addAttribute("urlType2", "/mes-encheres-remportees" + baseParams);


        model.addAttribute("isAchat", true);
        displayArticle(true, categorieId, contient, typeEnchere, session, model);
        return "index";
    }

    @GetMapping({ "/mes-ventes-en-cours", "/mes-ventes-non-debutees", "/mes-ventes-terminees"})
    public String ventes(
            HttpServletRequest request,
            HttpSession session,
            Model model,
            @RequestParam(value = "categorieId", required = false) Integer categorieId,
            @RequestParam(value = "contient", required = false) String contient) {

        String path = request.getServletPath();
        int typeEnchere = switch (path) {
            case "/mes-ventes-non-debutees" -> 1;
            case "/mes-ventes-terminees" -> 2;
            default -> 0;
        };

        String currentPath = request.getServletPath();
        model.addAttribute("formActionUrl", currentPath);

        String baseParams = "";
        if (categorieId != null) {
            baseParams += "?categorieId=" + categorieId;
        }
        if (contient != null && !contient.isBlank()) {
            baseParams += (baseParams.isEmpty() ? "?" : "&") + "contient=" + contient;
        }

        model.addAttribute("urlType0", "/mes-ventes-en-cours" + baseParams);
        model.addAttribute("urlType1", "/mes-ventes-non-debutees" + baseParams);
        model.addAttribute("urlType2", "/mes-ventes-terminees" + baseParams);


        displayArticle(false,categorieId, contient, typeEnchere, session, model);

        model.addAttribute("isAchat", false);
        return "index";
    }


    private void displayArticle(boolean achat,
                                Integer categorieId,
                                String contient,
                                Integer typeEnchere,
                                HttpSession session,
                                Model model ) {

        List<Categorie> categories = categorieService.getAllCategories();
        //System.out.println("categories = " + categories);
        model.addAttribute("categories", categories);

        if (categorieId != null) {
            model.addAttribute("selectedCategorieId", categorieId);
        }

        Utilisateur user = (Utilisateur)  session.getAttribute("userInSession");
        String pseudo = (user != null) ? user.getPseudo() : "";

        List<ArticleAVendre> articles = articleService.find( achat, categorieId, contient, typeEnchere, pseudo );

        model.addAttribute("typeEnchere", typeEnchere);
        model.addAttribute("contient", contient);

        articles = ArticleChainFilter.contains(articles, contient );

        model.addAttribute("articles", articles);
    }


}

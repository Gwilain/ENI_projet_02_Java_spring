package fr.eni.encheres.controller;

import fr.eni.encheres.model.ArticleAVendre;
import fr.eni.encheres.model.Categorie;
import fr.eni.encheres.service.ArticleService;
import fr.eni.encheres.service.CategorieService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

@Controller
public class ArticleController {

    @Autowired
    private ArticleService articleService;

    @Autowired
    private CategorieService categorieService;

//    @GetMapping
//    public String index(Model model) {
//        System.out.println("index");
//
//        System.out.println( articleService.findAll() );
//
//        model.addAttribute("articles", articleService.findAll());
//
//        model.addAttribute("categories", categorieService.findAll());
//
//
//        return "index";
//    }

    @GetMapping("/")
    public String afficherIndex(@RequestParam(value = "categorieId", required = false) Long categorieId, Model model) {
        List<Categorie> categories = categorieService.findAll();
        List<ArticleAVendre> articles;

        if (categorieId != null) {
            articles = articleService.findByCategorieId(categorieId);
            model.addAttribute("selectedCategorieId", categorieId);
        } else {
            articles = articleService.findAll();
        }

        model.addAttribute("categories", categories);
        model.addAttribute("articles", articles);
        return "index";
    }
}

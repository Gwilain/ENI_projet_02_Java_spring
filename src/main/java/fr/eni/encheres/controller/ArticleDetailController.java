package fr.eni.encheres.controller;


import fr.eni.encheres.dto.ArticleDetailDTO;
import fr.eni.encheres.dto.EnchereDTO;
import fr.eni.encheres.model.Utilisateur;
import fr.eni.encheres.repository.EnchereRepository;
import fr.eni.encheres.service.ArticleDetailService;
import fr.eni.encheres.service.ArticleService;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;

@Controller
@RequestMapping("/detail-article")
public class ArticleDetailController {

    @Autowired
    private ArticleDetailService articleDetailService;


    @GetMapping
    public String articleDetail(@RequestParam( value="id", required = true ) long id,
                                Principal principal,
                                HttpSession session,
                                Model model) {

        ArticleDetailDTO detail = articleDetailService.getArticleDetail(id, principal);
        EnchereDTO dto = new EnchereDTO();
        dto.setIdArticle((int) id);

        model.addAttribute("enchere",dto );

        model.addAttribute("article", detail);
        return "detail-article";
    }

    @PostMapping
    public String encherir(@ModelAttribute EnchereDTO enchere, HttpSession session) {

        System.out.println("***Encherir post***");
        System.out.println("***Encherir post***");
        System.out.println("***Encherir post***");
        int montant = enchere.getMontant();
        System.out.println("montant = "+montant);
        int idArticle = enchere.getIdArticle();

        System.out.println("idArticle = "+idArticle);

        Utilisateur user = (Utilisateur) session.getAttribute("userInSession");
        articleDetailService.addEnchere(user, montant, idArticle);



        String url = "redirect:/detail-article?id="+idArticle;
        return url;
    }


}

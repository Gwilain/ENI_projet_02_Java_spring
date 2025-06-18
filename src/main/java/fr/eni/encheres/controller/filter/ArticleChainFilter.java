package fr.eni.encheres.controller.filter;

import fr.eni.encheres.model.ArticleAVendre;

import java.util.List;

public class ArticleChainFilter {

    public static List<ArticleAVendre> contains(List<ArticleAVendre> articles, String contient) {

        if(contient != null  && !contient.isBlank()) {
            String recherche = contient.toLowerCase();
            articles = articles.stream()
                    .filter(a -> a.getNom() != null && a.getNom().toLowerCase().contains(recherche))
                    .toList();
            //model.addAttribute("contient", contient);
        }

        return articles;
    }

}

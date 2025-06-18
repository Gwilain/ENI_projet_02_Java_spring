package fr.eni.encheres.controller;


import fr.eni.encheres.dto.ArticleDetailDTO;
import fr.eni.encheres.service.ArticleDetailService;
import fr.eni.encheres.service.ArticleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
@RequestMapping("/article")
public class ArticleDetailController {

    @Autowired
    private ArticleDetailService articleDetailServiceService;

    @GetMapping
    public String articleDetail(@RequestParam( value="id", required = true ) long id, Model model) {

        ArticleDetailDTO detail = articleDetailServiceService.getArticleDetail(id);

        model.addAttribute("article", detail);
        return "detail-article";
    }



}

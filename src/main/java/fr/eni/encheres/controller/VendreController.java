package fr.eni.encheres.controller;

import fr.eni.encheres.dto.VenteFormDTO;
import fr.eni.encheres.model.Adresse;
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

import java.time.LocalDate;
import java.util.List;

@Controller
public class VendreController {
    @Autowired
    private ArticleService articleService;

    @Autowired
    private AdresseService adresseService;

    @Autowired
    private CategorieService categorieService;



    @GetMapping("/vendre")
    public String vendre(HttpSession session, Model model) {

        VenteFormDTO form = new VenteFormDTO();
        form.setDateDebut(LocalDate.now());
        form.setDateFin(LocalDate.now().plusDays(30));

        model.addAttribute("articleAVendre", form);


        List<Categorie> categories = categorieService.getAllCategories();

        Utilisateur user = (Utilisateur)  session.getAttribute("userInSession");
        Adresse userAdresse  = user.getAdresse();
        System.out.println("userAdresse = " + userAdresse);

        List<Adresse> adressesEni = adresseService.findAdressesEni();

        model.addAttribute("categories", categories);
        model.addAttribute("adresseVendeur", userAdresse);
        model.addAttribute("adressesEni", adressesEni);

        return "vendre";
    }


    @PostMapping("/vendre")
    public String miseEnVente(@Valid @ModelAttribute VenteFormDTO form, HttpSession session,
                              BindingResult bindingResult,
                              Model model) {

        System.out.println("controler mise en vente : post");

        if (bindingResult.hasErrors()) {
            return "vendre";
        }
        Utilisateur user = (Utilisateur)  session.getAttribute("userInSession");
        articleService.registerVente(form, user);

        return "redirect:/";
    }
}

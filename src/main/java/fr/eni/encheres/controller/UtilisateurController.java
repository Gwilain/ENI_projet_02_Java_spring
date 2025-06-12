package fr.eni.encheres.controller;

import fr.eni.encheres.dto.InscriptionForm;
import fr.eni.encheres.model.Utilisateur;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("utilisateur")
public class UtilisateurController {

    @GetMapping("/inscription")
    public String afficherFormulaire(Model model) {
        model.addAttribute("inscriptionForm", new InscriptionForm());
        return "inscription";
    }

    @PostMapping("/inscription")
    public String crerProfilSubmit(@ModelAttribute InscriptionForm form) {

        return "inscription";
    }

}

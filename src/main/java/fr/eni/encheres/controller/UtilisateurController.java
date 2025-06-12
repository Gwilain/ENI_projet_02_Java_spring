package fr.eni.encheres.controller;

import fr.eni.encheres.dto.InscriptionForm;
import fr.eni.encheres.model.Utilisateur;
import fr.eni.encheres.service.UtilisateurService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("utilisateur")
public class UtilisateurController {

    @Autowired
    private UtilisateurService utilisateurService;


    @GetMapping("/inscription")
    public String afficherFormulaire(Model model) {
        model.addAttribute("inscriptionForm", new InscriptionForm());
        return "inscription";
    }


    @PostMapping("/inscription")
    public String traiterFormulaire(@Valid @ModelAttribute InscriptionForm form,
                                    BindingResult bindingResult,
                                    Model model) {

        System.out.println("controler inscription");

        if (bindingResult.hasErrors()) {
            return "inscription";
        }


        utilisateurService.register(form);


//        try {
//            Utilisateur utilisateur = fromDto(form);
//            utilisateurService.save(utilisateur);
//        } catch (ServiceException e) {
//            model.addAttribute("erreur", "Erreur : " + e.getCode());
//            return "inscription";
//        }

        return "redirect:/confirmation";
    }

}

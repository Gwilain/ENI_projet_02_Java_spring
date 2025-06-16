package fr.eni.encheres.controller;

import fr.eni.encheres.dto.InscriptionForm;
import fr.eni.encheres.model.Utilisateur;
import fr.eni.encheres.service.UtilisateurService;
import jakarta.servlet.http.HttpSession;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;
import java.util.Enumeration;

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

        model.addAttribute("modeModif", false);

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

//    @GetMapping
//    public String profil(Model model) {
//
//        return "profil";
//    }


    @GetMapping("/profil")
    public String monProfil(@RequestParam(value = "pseudo", required = false)String pseudo, Model model, Principal principal) {

        System.out.println("Profil appelé avec pseudo = " + pseudo);

        String user_pseudo;

        if (pseudo != null) {

            // Profil d’un autre utilisateur (ex. vendeur)
            user_pseudo = pseudo;
            model.addAttribute("isCUserProfil", false);
            // Utilisateur utilisateur = utilisateurService.findUser(pseudo);

        } else {

            // Profil de l’utilisateur connecté
            user_pseudo = principal.getName();

            model.addAttribute("isCUserProfil", true);
        }

        Utilisateur utilisateur = utilisateurService.findUser(user_pseudo);

        System.out.println("utilisateur = " + utilisateur);

        model.addAttribute("user", utilisateur);

        return "profil";
    }

//@GetMapping("/profil")
//public String monProfil(Model model, Principal principal) {
//
//    String email = principal.getName(); // identifiant (email ici)
//    Utilisateur utilisateur = utilisateurService.findUser(email);
//    model.addAttribute("user", utilisateur);
//
//    return "profil";
//}


    @GetMapping("/modifier-profil")
    public String modifierProfil(HttpSession session, Model model) {


        model.addAttribute("modeModif", true);
//        System.out.println("modifierProfil ::: Session ID: " + session.getId());
//        System.out.println("modifierProfil ::: Contenu session: " + session.getAttributeNames());

        Utilisateur user = (Utilisateur)  session.getAttribute("userInSession");
  //      System.out.println();
        InscriptionForm form = new InscriptionForm();
//         System.out.println("modifier-profil :  user "+user );
//
//        System.out.println("Valeur test dans la session : " + session.getAttribute("test"));

        //Enumeration<String> attributeNames = session.getAttributeNames();
//        while (attributeNames.hasMoreElements()) {
//            String name = attributeNames.nextElement();
//            Object value = session.getAttribute(name);
//            System.out.println("Session Attribute - Name: " + name + ", Value: " + value);
//        }
        form.setPseudo(user.getPseudo() );
        form.setNom(user.getNom());
        form.setPrenom(user.getPrenom());
        form.setEmail(user.getEmail());
        form.setRue( user.getAdresse().getRue() );
        form.setCodePostal( user.getAdresse().getCodePostal() );
        form.setVille( user.getAdresse().getVille() );

        model.addAttribute("inscriptionForm", form);

        //model.addAttribute("modifProfil", true);

        return "inscription";
    }

    @PostMapping("/modifier-profil")
    public String savegModifierProfil(@Valid @ModelAttribute InscriptionForm form,
                                      BindingResult bindingResult,
                                      HttpSession session,
                                      Model model) {

        System.out.println("savegModifierProfil");

        Utilisateur user = (Utilisateur)  session.getAttribute("userInSession");

        String pseudoSession = user.getPseudo();

        System.out.println("controler modification");

        if (bindingResult.hasErrors()) {
            return "/modifier-profil";
        }

        utilisateurService.savegModifierProfil(form, pseudoSession);

        return "redirect:/";

    }

}

package fr.eni.encheres.controller;

import fr.eni.encheres.service.UtilisateurService;
import fr.eni.encheres.service.UtilisateurService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

//@Controller
public class TestController {

    //@Autowired
    private UtilisateurService utilisateurService;


    //@GetMapping("/creer-utilisateur")
    public String creer() {

        utilisateurService.creerUtilisateur();
        return "ok";
    }



}

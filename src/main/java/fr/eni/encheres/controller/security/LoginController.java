package fr.eni.encheres.controller.security;

import fr.eni.encheres.model.Utilisateur;
import fr.eni.encheres.service.UtilisateurService;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

import java.security.Principal;

@Controller
public class LoginController {

    @Autowired
    private UtilisateurService utilisateurService;

    @GetMapping("/login")
    public String login() {

        return "login";
    }

    @GetMapping("/session")
    public String userInSession(HttpSession session, Principal principal) {

        Utilisateur user = utilisateurService.findUser(principal.getName());
        if (user != null) {
            Utilisateur userInSession = new Utilisateur();
            userInSession.setPseudo(user.getPseudo());
            userInSession.setNom(user.getNom());
            userInSession.setPrenom(user.getPrenom());
            userInSession.setTelephone(user.getTelephone());
            userInSession.setEmail(user.getEmail());
            userInSession.setAdresse(user.getAdresse());
            userInSession.setCredit(user.getCredit());

            session.setAttribute("userInSession", userInSession);

            System.out.println("session au login ::: Session ID: " + session.getId());
            System.out.println("Convert 'System.out' call to log call ::: Contenu session: " + session.getAttributeNames());

            session.setAttribute("test", "coucou");

            System.out.println( "session.getAttribute(userInSession) :::"+ session.getAttribute("userInSession") );
            System.out.println("userInSession: " + userInSession);

        }else{
            System.out.println("Utilisateur non trouvé !");
        }

        return "redirect:/";
    }

}

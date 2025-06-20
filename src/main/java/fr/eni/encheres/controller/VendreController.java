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
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
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
    public String vendre(@RequestParam(value = "id", required = false) Integer id,
                         HttpSession session,
                         Model model) {

        VenteFormDTO form;

        if(id != null && id >= 0) {

            form =  articleService.getArticle(id);
            model.addAttribute( "selectedCategorieId", form.getCategorieId());
            model.addAttribute( "isModif", true );
        }else{

            form = new VenteFormDTO();
            form.setDateDebut(LocalDate.now());
            form.setDateFin(LocalDate.now().plusDays(30));
            model.addAttribute( "isModif", false );
        }

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
    public String miseEnVente(@Valid @ModelAttribute VenteFormDTO form,
                              @RequestParam String action,
                              HttpSession session,
                              BindingResult bindingResult,
                              Model model) {

        System.out.println("controler mise en vente : post");

        if (bindingResult.hasErrors()) {
            return "vendre";
        }
        Utilisateur user = (Utilisateur)  session.getAttribute("userInSession");

        if ("annuler".equals(action)) {

            articleService.deleterArticle(form.getId(), user.getPseudo());

            return "redirect:/mes-ventes-non-debutees";
        }

        /*if (!file.isEmpty()) {
            Path uploadPath = Paths.get("uploads/");
           // Files.createDirectories(uploadPath);
            String fileName = articleService.getPhotoName() + ".jpg";

            Path filePath = uploadPath.resolve(fileName);
            Files.copy(file.getInputStream(), filePath, StandardCopyOption.REPLACE_EXISTING);



            // Sauvegarder le nom du fichier dans ton DTO ou entité si besoin
            //articleDTO.setPhoto(fileName);
        }*/

        articleService.registerVente(form, user);

        return "redirect:/";
    }
}

package fr.eni.encheres.service;

import fr.eni.encheres.dto.ArticleDetailDTO;
import fr.eni.encheres.model.ArticleAVendre;
import fr.eni.encheres.model.Enchere;
import fr.eni.encheres.model.Utilisateur;
import fr.eni.encheres.repository.ArticleAVendreRepository;
import fr.eni.encheres.repository.EnchereRepository;
import fr.eni.encheres.repository.UtilisateurRepository;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.security.Principal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Optional;

@Service
public class ArticleDetailService {

    @Autowired
    ArticleAVendreRepository articleAVendreRepository;

    @Autowired
    UtilisateurService utilisateurService;

    @Autowired
    EnchereRepository enchereRepository;

    public ArticleDetailDTO getArticleDetail(Long id, Principal principal) {

        ArticleAVendre aav = articleAVendreRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Vente non trouvée"));

        ArticleDetailDTO articleDetailDTO = new ArticleDetailDTO();
        articleDetailDTO.setNom(aav.getNom());
        articleDetailDTO.setId(aav.getId());
        articleDetailDTO.setPhotoId(aav.getPhoto());

        articleDetailDTO.setDescription(aav.getDescription());
        articleDetailDTO.setDateDebut(aav.getDateDebutEncheres());
        articleDetailDTO.setDateFin(aav.getDateFinEncheres());
        articleDetailDTO.setNomCategorie( aav.getCategorie().getLibelle() );
        articleDetailDTO.setAdresseRetrait( aav.getAdresseRetrait().affichageComplet() );
        articleDetailDTO.setPrixInitial( aav.getPrixInitial() );
        articleDetailDTO.setPrixActuel( aav.getPrixVente() );
        articleDetailDTO.setPseudoVendeur( aav.getVendeur().getPseudo() );

        Enchere maxEnchere = enchereRepository.findTopByArticleAVendreIdOrderByMontantDesc( id).orElse(null);



        articleDetailDTO.setModifiable(false);

        System.out.println("LocalDate.now().isBefore(aav.getDateDebutEncheres()) ::: "+LocalDate.now().isBefore(aav.getDateDebutEncheres()));

        if( LocalDate.now().isBefore(aav.getDateDebutEncheres()) ) {

            System.out.println("la date est bien avant le date de debut");
            Utilisateur user =  utilisateurService.findUser( principal.getName() );

            System.out.println("user loggé pseudo: " + user.getPseudo()+"  vendeur pseudo :"+aav.getVendeur().getPseudo() );
            if( user.getPseudo().equals( aav.getVendeur().getPseudo() ) ) {
                System.out.println("C'est le même utilisateur");
                articleDetailDTO.setModifiable(true);
            }
        }
        return articleDetailDTO;
    }

    public void addEnchere( Utilisateur user, int montant, long idArticle){
//        Enchere enchere = new Enchere();
//        enchere.setEncherisseur(user);
//        enchere.setArticleAVendre( articleAVendreRepository.getArticleAVendreById(idArticle) );
//        enchere.setMontant( montant );
//        enchere.setDate( LocalDateTime.now() );
//        enchereRepository.save( enchere );

        ArticleAVendre article = articleAVendreRepository.findById( idArticle)
                .orElseThrow(() -> new EntityNotFoundException("Article non trouvé"));

        // On vérifie que l'enchère est supérieure
        if (article.getPrixVente() != null && montant <= article.getPrixVente()) {
            throw new IllegalArgumentException("Le montant doit être supérieur à l'enchère actuelle.");
        }

        Enchere enchere = new Enchere();
        enchere.setEncherisseur(user);
        enchere.setArticleAVendre(article);
        enchere.setMontant(montant);
        enchere.setDate(LocalDateTime.now());

        enchereRepository.save(enchere);

        // Mise à jour du prix de vente
        article.setPrixVente(montant);
        articleAVendreRepository.save(article);

    }

}

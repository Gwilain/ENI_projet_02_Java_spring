package fr.eni.encheres.service;

import fr.eni.encheres.dto.ArticleDetailDTO;
import fr.eni.encheres.model.ArticleAVendre;
import fr.eni.encheres.repository.ArticleAVendreRepository;
import fr.eni.encheres.repository.UtilisateurRepository;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class ArticleDetailService {

    @Autowired
    ArticleAVendreRepository articleAVendreRepository;

    @Autowired
    UtilisateurRepository utilisateurRepository;

    public ArticleDetailDTO getArticleDetail(Long id) {

        ArticleAVendre aav = articleAVendreRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Vente non trouvée"));

        ArticleDetailDTO articleDetailDTO = new ArticleDetailDTO();
        articleDetailDTO.setNom(aav.getNom());

        articleDetailDTO.setDescription(aav.getDescription());
        articleDetailDTO.setDateDebut(aav.getDateDebutEncheres());
        articleDetailDTO.setDateFin(aav.getDateFinEncheres());
        articleDetailDTO.setNomCategorie( aav.getCategorie().getLibelle() );
        articleDetailDTO.setAdresseRetrait( aav.getAdresseRetrait().affichageComplet() );
        articleDetailDTO.setPrixInitial( aav.getPrixInitial() );
        articleDetailDTO.setPrixActuel( aav.getPrixVente() );
        //articleDetailDTO.setPhotoUrl( aav.getPhoto(). );


        return articleDetailDTO;
    }

}

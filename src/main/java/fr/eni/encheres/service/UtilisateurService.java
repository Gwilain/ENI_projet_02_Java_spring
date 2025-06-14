package fr.eni.encheres.service;

import fr.eni.encheres.dto.InscriptionForm;
import fr.eni.encheres.exception.ServiceException;
import fr.eni.encheres.exception.ServiceExceptionCode;
import fr.eni.encheres.model.Adresse;
import fr.eni.encheres.model.Utilisateur;
import fr.eni.encheres.repository.AdresseRepository;
import fr.eni.encheres.repository.UtilisateurRepository;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class UtilisateurService {

    @Autowired
    private UtilisateurRepository repo;
    @Autowired
    private UtilisateurRepository utilisateurRepository;

    @Transactional
    public Utilisateur populate(InscriptionForm dto) {
        Adresse adresse = new Adresse();
        adresse.setRue(dto.getRue());
        adresse.setCodePostal(dto.getCodePostal());
        adresse.setVille(dto.getVille());

        System.out.println("adresse : " + adresse);
        // Vérifications préalables sur les champs du DTO
        if (repo.existsByEmail(dto.getEmail())) {
            throw new ServiceException(ServiceExceptionCode.EMAIL_EXIST);
        }

        if (repo.existsByPseudo(dto.getPseudo())) {
            throw new ServiceException(ServiceExceptionCode.PSEUDO_EXIST);
        }

        if (!dto.getPseudo().matches("^[a-zA-Z0-9_]+$")) {
            throw new ServiceException(ServiceExceptionCode.PSEUDO_PERMIT);
        }

        BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
        String motDePasseEncode = passwordEncoder.encode(dto.getMdp());

        Utilisateur utilisateur = new Utilisateur();
        utilisateur.setPseudo(dto.getPseudo());
        utilisateur.setNom(dto.getNom());
        utilisateur.setPrenom(dto.getPrenom());
        utilisateur.setEmail(dto.getEmail());
        utilisateur.setTelephone(dto.getTelephone());
        utilisateur.setMotDePasse(motDePasseEncode);
        utilisateur.setAdresse(adresse);

        return utilisateur;
    }


    public Utilisateur register(InscriptionForm dto) {
        Utilisateur utilisateur = populate(dto);

        System.out.println("saveFromDto utilisateur = " + utilisateur);

        return repo.save(utilisateur);
    }

    public Utilisateur findUser(String alias){
            if(alias.contains("@")){

              return utilisateurRepository.findByEmail(alias);

            }else{
                return utilisateurRepository.findByPseudo(alias);
            }
     }


}

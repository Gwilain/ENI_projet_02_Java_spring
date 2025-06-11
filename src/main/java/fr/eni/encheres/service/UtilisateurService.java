package fr.eni.encheres.service;

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
    private AdresseRepository adresseRepo;

//    @Autowired
//    private PasswordEncoder passwordEncoder;


    @Transactional
    public Utilisateur save(Utilisateur utilisateur) {

        if (repo.existsByEmail(utilisateur.getEmail())) {

            throw new ServiceException(ServiceExceptionCode.EMAIL_EXIST);
        }

        if (repo.existsByPseudo(utilisateur.getPseudo())) {
            throw new ServiceException(ServiceExceptionCode.PSEUDO_EXIST);
        }

        if (! utilisateur.getPseudo().matches("^[a-zA-Z0-9_]+$")) {
            throw new ServiceException(ServiceExceptionCode.PSEUDO_PERMIT);
        }


        BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
        String motDePasseEncode = passwordEncoder.encode(utilisateur.getMotDePasse());
        utilisateur.setMotDePasse(motDePasseEncode);

        return repo.save(utilisateur);
    }

    @Transactional
    public void creerUtilisateur() {
        // Créer une adresse
        Adresse adresse = new Adresse();
        adresse.setRue("42 rue des Lilas");
        adresse.setCodePostal("75000");
        adresse.setVille("Paris");
        adresse.setAdresseEni(false);
        adresse = adresseRepo.save(adresse);

        // Créer un utilisateur
        Utilisateur utilisateur = new Utilisateur();
        utilisateur.setNom("MARTIN");
        utilisateur.setPrenom("Jean");
        utilisateur.setPseudo("j_martin");
        utilisateur.setEmail("jean.martin@example.com");
        utilisateur.setMotDePasse("1234");
        utilisateur.setTelephone("0102030405");
        utilisateur.setCredit(10);
        utilisateur.setAdmin(true);
        utilisateur.setAdresse(adresse);

        save(utilisateur);
    }


}

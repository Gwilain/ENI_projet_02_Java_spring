package fr.eni.encheres.service;

import fr.eni.encheres.model.Adresse;
import fr.eni.encheres.repository.AdresseRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class AdresseService {

    @Autowired
    private AdresseRepository adresseRepository;


    public List<Adresse> findAdressesEni(){


        return adresseRepository.findByAdresseEniTrue();

    }
}

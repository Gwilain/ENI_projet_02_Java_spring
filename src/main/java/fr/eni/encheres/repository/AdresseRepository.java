package fr.eni.encheres.repository;

import fr.eni.encheres.model.Adresse;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface AdresseRepository extends JpaRepository<Adresse, Integer> {

    List<Adresse> findByAdresseEniTrue();

}

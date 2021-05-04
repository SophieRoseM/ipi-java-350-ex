package com.ipiecoles.java.java350.model;



import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

import java.time.LocalDate;

class EmployeTest {

    //senarios de test, 1 senario = 1 test


    //employe embauché aujourdhui => doit avoir un nb d'année de 0
    @Test
    public void testGetNbAnneesAncienneteDateEmbaucheToday(){
        //given
        LocalDate dateEmbaucheToday = LocalDate.now(); //date d'aujourdhui
        Employe employe = new Employe();
        employe.setDateEmbauche(dateEmbaucheToday);

        //when
        Integer nbAnneeAnciennete = employe.getNombreAnneeAnciennete();

        //then
        Assertions.assertThat(nbAnneeAnciennete).isEqualTo(0);

    }

    //Date d'embauche dans le futur => Nombre années ancienneté : null
    @Test
    public void testGetNbAnneesAncienneteDateEmbaucheFuture(){
        //Given
        LocalDate dateEmbaucheFuture = LocalDate.now().plusYears(5);
        Employe employe = new Employe();
        employe.setDateEmbauche(dateEmbaucheFuture);
        //When
        Integer nbAnneesAnciennete = employe.getNombreAnneeAnciennete();
        //Then
        Assertions.assertThat(nbAnneesAnciennete).isNull();
    }

    //Date d'embauche null => Nombre années ancienneté : null

    @Test
    public void testGetNbAnneesAncienneteDateEmbauchePast(){
        //Given
        Employe employe = new Employe();
        employe.setDateEmbauche(LocalDate.now().minusYears(5));
        //When
        Integer nbAnneesAnciennete = employe.getNombreAnneeAnciennete();
        //Then
        Assertions.assertThat(nbAnneesAnciennete).isEqualTo(5);
    }

    //Date d'embauche 5 ans dans le passé => Nombre années ancienneté : 5
    @Test
    public void testNombreAnneeAncienneteDateEmbaucheCinqAns() {
        //Given
        LocalDate dateEmbaucheCinqAns = LocalDate.now().minusYears(5);
        Employe employe = new Employe();
        employe.setDateEmbauche(dateEmbaucheCinqAns);

        //When
        Integer nbAnneesAnciennete = employe.getNombreAnneeAnciennete();

        //Then
        Assertions.assertThat(nbAnneesAnciennete).isEqualTo(5);
    }

    @Test
    public void testgetPrimeAnnuelleManagerSansAnciennete(){
        //given
        //4 données entrée
        LocalDate dateEmbauche = LocalDate.now();
        Integer performance = null;
        String matricule = "M12345";
        Double tempsPartiel = 1.0;
        //initialise l'employe à partir des données d'entrée
        Employe employe = new Employe("Doe", "John",
                matricule, dateEmbauche, Entreprise.SALAIRE_BASE, performance, tempsPartiel);

        //when
        //calcul de prime
        Double primeCalculee = employe.getPrimeAnnuelle();

        //then
        //resultat attendus : prime * indice manager
        // 1000 * 1.7 = 1700
        Assertions.assertThat(primeCalculee).isEqualTo(1700.0);

    }
    //test parametré
    @ParameterizedTest(name = "Employé anciennete {0}, performance {1}, matricule {2}, temps partiel {3} => Prime {4}")
    @CsvSource({
            "0,,'M12345',1.0,1700.0" , // manager à temps plein sans ancienneté
            "0,,'T12345',1.0,1000.0", //Technicien à temps plein sans anienneté
            "0,,'M12345',0.5,850.0", // manager à mi-temps sans ancienneté
            "5,,'M12345',1.0,2200.0", // manager 5 ans annciennté
            "0,3,'T12345',1.0,3300.0", //Technicien à temps plein sans anienneté, performance 3
    })
    public void testgetPrimeAnnuelle(Integer nbAnnéeAnciennete, Integer performance, String matricule, Double tempsPartiel,
                                     Double primeObtenue){
        //given
        LocalDate dateEmbauche = LocalDate.now().minusYears(nbAnnéeAnciennete);
        //initialise l'employe à partir des données d'entrée
        Employe employe = new Employe("Doe", "John",
                matricule, dateEmbauche, Entreprise.SALAIRE_BASE, performance, tempsPartiel);

        //when
        //calcul de prime
        Double primeCalculee = employe.getPrimeAnnuelle();

        //then
        //resultat attendus
        Assertions.assertThat(primeCalculee).isEqualTo(primeObtenue);

    }

}

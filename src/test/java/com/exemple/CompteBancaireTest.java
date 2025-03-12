package com.exemple;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class CompteBancaireTest {

    double initialSold;
    double initialSoldNegatif ;
    double depotmontant;
    double montantNegatif;
    double retiremontant;
    double retraitmontantSupAuSolde;
    CompteBancaire compteBancaire;


    @BeforeEach
    void setUp() {
        initialSold = 100;
        initialSoldNegatif = -50;
        depotmontant = 50;
        retiremontant = 30;
        retraitmontantSupAuSolde =200;
        montantNegatif = -10;
        compteBancaire = new CompteBancaire(initialSold);
    }

    @Test
    void getSolde() {
        assertEquals(initialSold, compteBancaire.getSolde());
    }

    @Test
    void deposer() {
        compteBancaire.deposer(depotmontant);
        assertEquals(150, compteBancaire.getSolde());
    }

    @Test
    void retirer() {
        compteBancaire.retirer(retiremontant);
        assertEquals(70, compteBancaire.getSolde());
    }

    @Test
    void test_retrait_avec_montant_superieu_au_solde(){

        var thrown = assertThrows(IllegalArgumentException.class, () -> {
           compteBancaire.retirer(retraitmontantSupAuSolde);
        });
        assertTrue(thrown.getMessage().contains("Fonds insuffisants"));
    }

    @Test
    void test_depot_montant_negatif(){
        var thrown = assertThrows(IllegalArgumentException.class, () -> {
            compteBancaire.deposer(montantNegatif);
        });
        assertTrue(thrown.getMessage().contains("Le montant du dépôt doit être positif")) ;
    }
    @Test
    void test_retirer_montant_negatif(){
        var thrown = assertThrows(IllegalArgumentException.class, () -> {
           compteBancaire.retirer(montantNegatif);
        });
        assertTrue(thrown.getMessage().contains("Le montant du retrait doit être positif"));
    }

    @Test
    void test_creation_compte_avec_solde_initial_negatif(){
        var thrown = assertThrows(IllegalArgumentException.class, () -> {
            compteBancaire = new CompteBancaire(initialSoldNegatif);
        });
        assertTrue(thrown.getMessage().contains("Le solde initial ne peut pas être négatif"));
    }
}
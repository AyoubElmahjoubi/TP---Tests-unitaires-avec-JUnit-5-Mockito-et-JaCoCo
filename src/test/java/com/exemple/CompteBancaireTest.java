package com.exemple;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;


class CompteBancaireTest {

    double initialSold;
    double initialSoldNegatif ;
    double depotmontant;
    double montantNegatif;
    double retiremontant;
    double retraitmontantSupAuSolde;

    @InjectMocks
    CompteBancaire compteBancaire;
    @Mock
    NotificationService notificationService;
    @Mock
    NotificationService notificationService_1;
    @Mock
    NotificationService notificationService_2;

    CompteBancaire compteBancaire_1;
    CompteBancaire compteBancaire_2;



    @BeforeEach
    void setUp() {
        MockitoAnnotations.initMocks(this);
        initialSold = 100;
        initialSoldNegatif = -50;
        depotmontant = 50;
        retiremontant = 30;
        retraitmontantSupAuSolde =200;
        montantNegatif = -10;
        compteBancaire = new CompteBancaire(initialSold , notificationService);
        compteBancaire_1 = new CompteBancaire(initialSold, notificationService_1);
        compteBancaire_2 = new CompteBancaire(initialSold, notificationService_2);
    }

    @Test
    void getSolde() {
        assertEquals(initialSold, compteBancaire.getSolde());
    }

    @Test
    void deposer() {
        compteBancaire.deposer(depotmontant);
        assertEquals(150, compteBancaire.getSolde());
        verify(notificationService).envoyerNotification("Dépôt de " + depotmontant + " effectué.");
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
        verify(notificationService, never()).envoyerNotification("Retrait de " + retraitmontantSupAuSolde + " effectué");
    }

    @Test
    void test_depot_montant_negatif(){
        var thrown = assertThrows(IllegalArgumentException.class, () -> {
            compteBancaire.deposer(montantNegatif);
        });
        assertTrue(thrown.getMessage().contains("Le montant du dépôt doit être positif")) ;
        verify(notificationService, never()).envoyerNotification("Dépôt de " + montantNegatif + " effectué.");
    }
    @Test
    void test_retirer_montant_negatif(){
        var thrown = assertThrows(IllegalArgumentException.class, () -> {
           compteBancaire.retirer(montantNegatif);
        });
        assertTrue(thrown.getMessage().contains("Le montant du retrait doit être positif"));
        verify(notificationService, never()).envoyerNotification("Retrait de " + montantNegatif + " effectué");
    }

    @Test
    void test_creation_compte_avec_solde_initial_negatif(){
        var thrown = assertThrows(IllegalArgumentException.class, () -> {
            compteBancaire = new CompteBancaire(initialSoldNegatif , notificationService);
        });
        assertTrue(thrown.getMessage().contains("Le solde initial ne peut pas être négatif"));
    }

    // Pour repondre à la question 4 :
    @Test
    void test_deux_depot_successif(){
        compteBancaire.deposer(depotmontant);
        compteBancaire.deposer(depotmontant);
        assertEquals(200, compteBancaire.getSolde());
        verify(notificationService, times(2)).envoyerNotification("Dépôt de " + depotmontant + " effectué.");
    }

    // Pour repondre à la question 5 :
    @Test
    void test_interaction_complexe_avec_plusieurs_mocks(){
        compteBancaire_1.transfererVers(compteBancaire_2 ,depotmontant);
        assertEquals(50, compteBancaire_1.getSolde());
        assertEquals(150, compteBancaire_2.getSolde());
        verify(notificationService_1).envoyerNotification("vous avez transfrer ce montant :"+ depotmontant +" avec succés");
        verify(notificationService_2).envoyerNotification("un mantant : "+ depotmontant +" à été ajouter dans votre compte avec succés");
    }

}
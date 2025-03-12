package com.exemple;

public class CompteBancaire {

    private double solde;
    private NotificationService notificationService;

    public CompteBancaire() {
    }

    public CompteBancaire(double soldeInitial, NotificationService notificationService) {
        if (soldeInitial < 0) {
            throw new IllegalArgumentException("Le solde initial ne peut pas être négatif.");
        }
        this.solde = soldeInitial;
        this.notificationService = notificationService;
    }

    public CompteBancaire(double soldeInitial) {
        if (soldeInitial < 0) {
            throw new IllegalArgumentException("Le solde initial ne peut pas être négatif.");
        }
        this.solde = soldeInitial;
    }

    public double getSolde() {
        return solde;
    }

    public void deposer(double montant) {
        if (montant <= 0) {
            throw new IllegalArgumentException("Le montant du dépôt doit être positif.");
        }
        solde += montant;
        notificationService.envoyerNotification("Dépôt de " + montant + " effectué.");
    }

    public void retirer(double montant) {
        if (montant <= 0) {
            throw new IllegalArgumentException("Le montant du retrait doit être positif.");
        }
        if (montant > solde) {
            throw new IllegalArgumentException("Fonds insuffisants.");
        }
        solde -= montant;
        notificationService.envoyerNotification("Retrait de " + montant + " effectué.");
    }

    public void transfererVers(CompteBancaire autreCompte, double montant){
        this.retirer(montant);
        autreCompte.deposer(montant);
        this.notificationService.envoyerNotification("vous avez transfrer ce montant :"+ montant +" avec succés");
        autreCompte.notificationService.envoyerNotification("un mantant : "+ montant +" à été ajouter dans votre compte avec succés");
    }
}

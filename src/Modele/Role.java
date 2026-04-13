package Modele;

public enum Role {
    ARCHEOLOGUE("Archéologue", 3),
    GRIMPEUR("Grimpeur", 3),
    PORTEUR_D_EAU("Porteur d'eau", 5),
    EXPLORATEUR("Explorateur", 4),
    NAVIGATEUR("Navigateur", 4),
    METEOROLOGUE("Météorologue", 4);

    private final String nom;
    private final int maxGourde;

    Role(String nom, int maxGourde) {
        this.nom = nom;
        this.maxGourde = maxGourde;
    }

    public String getNom() {
        return nom;
    }

    public int getMaxGourde() {
        return maxGourde;
    }
}

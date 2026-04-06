package Modele;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Random;

public class Grille {
    public static final int X = 5, Y = 5;
    private Tuile[][] tuilesTableau;
    private int oeilX, oeilY;

    Grille() {
        ArrayList<Integer> tirage = new ArrayList<>(Arrays.asList(0, 1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 12, 13, 14, 15, 16, 17, 18, 19, 20, 21, 22, 23, 24));
        ArrayList<String> losange = new ArrayList<>(Arrays.asList("0,2", "1,1", "2,0", "3,1", "4,2", "3,3", "2,4", "1,3"));

        tuilesTableau = new Tuile[X][Y];

        oeilX = 2;
        oeilY = 2;

        Random r = new Random();

        ArrayList<Tunnel> tunnelsTrouves = new ArrayList<>();
        for (int i = 0; i < X; i++) {
            for (int j = 0; j < Y; j++) {

                int tuile = tirage.get(r.nextInt(tirage.size()));

                if (i == 2 && j == 2) {
                    tuilesTableau[oeilX][oeilY] = new OeilTempete(i, j);
                } else {
                    tirage.remove(Integer.valueOf(tuile));
                    switch (tuile) {
                        case 1:
                            tuilesTableau[i][j] = new TuileIndice(i, j, 0, TypePiece.MOTEUR, true);
                            break;
                        case 2:
                            tuilesTableau[i][j] = new TuileIndice(i, j, 0, TypePiece.MOTEUR, false);
                            break;
                        case 3:
                            tuilesTableau[i][j] = new TuileIndice(i, j, 0, TypePiece.HELICE, true);
                            break;
                        case 4:
                            tuilesTableau[i][j] = new TuileIndice(i, j, 0, TypePiece.HELICE, false);
                            break;
                        case 5:
                            tuilesTableau[i][j] = new TuileIndice(i, j, 0, TypePiece.GOUVERNAIL, true);
                            break;
                        case 6:
                            tuilesTableau[i][j] = new TuileIndice(i, j, 0, TypePiece.GOUVERNAIL, false);
                            break;
                        case 7:
                            tuilesTableau[i][j] = new TuileIndice(i, j, 0, TypePiece.CAPTEUR_SOLAIRE, true);
                            break;
                        case 8:
                            tuilesTableau[i][j] = new TuileIndice(i, j, 0, TypePiece.CAPTEUR_SOLAIRE, false);
                            break;
                        case 9:
                            tuilesTableau[i][j] = new ZoneCrash(i, j, 0);
                            break;
                        case 10:
                            tuilesTableau[i][j] = new PisteDecollage(i, j, 0);
                            break;
                        case 11:
                            tuilesTableau[i][j] = new Tunnel(i, j, 0);
                            break;
                        case 12:
                            tuilesTableau[i][j] = new Tunnel(i, j, 0);
                            break;
                        case 13:
                            tuilesTableau[i][j] = new Oasis(i, j, 0, false);
                            break;
                        case 14:
                            tuilesTableau[i][j] = new Oasis(i, j, 0, false);
                            break;
                        case 15:
                            tuilesTableau[i][j] = new Oasis(i, j, 0, true);
                            break;

                        default:
                            tuilesTableau[i][j] = new TuileOrdinaire(i, j, 0);
                    }
                }

                if (losange.contains(i + "," + j)) tuilesTableau[i][j].ajouterSable();
                if (tuilesTableau[i][j] instanceof Tunnel tunnel) {
                    tunnelsTrouves.add(tunnel);
                }
            }
        }
        for (Tunnel connect : tunnelsTrouves) {
            for (Tunnel connecte2 : tunnelsTrouves) {
                if (connect != connecte2) {
                    connect.ajouterTunnelConnecte(connecte2);
                }
            }
        }
    }

    public Tuile[][] getTuilesTableau() {
        return tuilesTableau;
    }

    public void deplacerTuiles(Direction vent, int f) {
        int ox = oeilX, oy = oeilY;
        for (int k = 0; k < f; k++) {
            switch (vent) {
                case NORD -> {
                    if (oy > 0) {
                        Tuile temp = tuilesTableau[ox][oy - 1];
                        tuilesTableau[ox][oy - 1] = tuilesTableau[ox][oy];
                        tuilesTableau[ox][oy] = temp;
                        oy--;
                        tuilesTableau[ox][oy].setX(ox);
                        tuilesTableau[ox][oy].setY(oy);
                        tuilesTableau[ox][oy + 1].setX(ox);
                        tuilesTableau[ox][oy + 1].setY(oy + 1);
                        tuilesTableau[ox][oy + 1].ajouterSable();
                    }
                }
                case SUD -> {
                    if (oy < Y - 1) {
                        Tuile temp = tuilesTableau[ox][oy + 1];
                        tuilesTableau[ox][oy + 1] = tuilesTableau[ox][oy];
                        tuilesTableau[ox][oy] = temp;
                        oy++;
                        tuilesTableau[ox][oy].setX(ox);
                        tuilesTableau[ox][oy].setY(oy);
                        tuilesTableau[ox][oy - 1].setX(ox);
                        tuilesTableau[ox][oy - 1].setY(oy - 1);
                        tuilesTableau[ox][oy - 1].ajouterSable();
                    }
                }
                case OUEST -> {
                    if (ox > 0) {
                        Tuile temp = tuilesTableau[ox - 1][oy];
                        tuilesTableau[ox - 1][oy] = tuilesTableau[ox][oy];
                        tuilesTableau[ox][oy] = temp;
                        ox--;
                        tuilesTableau[ox][oy].setX(ox);
                        tuilesTableau[ox][oy].setY(oy);
                        tuilesTableau[ox + 1][oy].setX(ox + 1);
                        tuilesTableau[ox + 1][oy].setY(oy);
                        tuilesTableau[ox + 1][oy].ajouterSable();
                    }
                }
                case EST -> {
                    if (ox < X - 1) {
                        Tuile temp = tuilesTableau[ox + 1][oy];
                        tuilesTableau[ox + 1][oy] = tuilesTableau[ox][oy];
                        tuilesTableau[ox][oy] = temp;
                        ox++;
                        tuilesTableau[ox][oy].setX(ox);
                        tuilesTableau[ox][oy].setY(oy);
                        tuilesTableau[ox - 1][oy].setX(ox - 1);
                        tuilesTableau[ox - 1][oy].setY(oy);
                        tuilesTableau[ox - 1][oy].ajouterSable();
                    }
                }
            }
        }
        this.oeilX = ox;
        this.oeilY = oy;
    }
}
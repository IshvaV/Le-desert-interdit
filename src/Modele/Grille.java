package Modele;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Random;

public class Grille {
    public static final int X=5, Y=5;
    private Tuile[][] tuilesTableau;
    Grille() {
        ArrayList<Integer> tirage = new ArrayList<>(Arrays.asList(0, 1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 12, 13, 14, 15, 16, 17, 18, 19, 20, 21, 22, 23, 24));
        ArrayList<String> losange = new ArrayList<>(Arrays.asList("0,2", "1,1", "2,0", "3,1", "4,2", "3,3", "2,4", "1,3"));

        tuilesTableau = new Tuile[X][Y];

        Random r = new Random();
        for (int i = 0; i < X; i++) {
            for (int j = 0; j < Y; j++) {

                int tuile = tirage.get(r.nextInt(tirage.size()));

                if (i == 2 && j == 2) {
                    tuilesTableau[i][j] = new OeilTempete(i, j);
                }else {
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
            }
        }
    }
}
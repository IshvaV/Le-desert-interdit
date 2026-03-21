package Modele;

import java.util.ArrayList;

public class Tunnel extends Tuile {

    private ArrayList<Tunnel> tunnelsConnectes;

    public Tunnel(int x, int y, int nbSable) {
        super(x, y, nbSable);
        tunnelsConnectes = new ArrayList<Tunnel>();
    }

    public void ajouterTunnelConnecte(Tunnel tunnel) {
        tunnelsConnectes.add(tunnel);
    }

    public ArrayList<Tunnel> getTunnelsConnectes() {
        return tunnelsConnectes;
    }
}

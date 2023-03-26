import peersim.core.CommonState;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;

public class Ring {

    private ArrayList<MyNode> listNodes = new ArrayList<>();
    private long ID;

    public Ring(long ID) {
        this.ID = ID;
    }


    public void setNeighbors() {
        // On initialise les noeuds de gauche et de droite en fonction du placement dans la liste pour qu'elle soit ordonnée
        // Ici, si il n'y a qu'un seul noeud, il est son propre voisin de gauche et de droite
        if (listNodes.size() == 1) {
            listNodes.get(0).setNodeLeft(listNodes.get(0));
            listNodes.get(0).setNodeRight(listNodes.get(0));
        }
        // Si il y a plus qu'un noeud, on defini le voisin de gauche comme celui qui est avant dans la liste, et inversement pour celui de droite
        // Il y a une exception pour le premier et dernier noeud où le premier a pour voisin de gauche le dernier et le dernier a pour voisin de droite le premier
        else {
            listNodes.get(0).setNodeLeft(listNodes.get(listNodes.size() - 1));
            listNodes.get(0).setNodeRight(listNodes.get(1));

            listNodes.get(listNodes.size() - 1).setNodeLeft(listNodes.get(listNodes.size() - 2));
            listNodes.get(listNodes.size() - 1).setNodeRight(listNodes.get(0));

            for (int i = 1; i < listNodes.size()-1; i++) {
                listNodes.get(i).setNodeLeft(listNodes.get(i - 1));
                listNodes.get(i).setNodeRight(listNodes.get(i + 1));
            }
        }
    }

    public void addNode(MyNode node) {
        // On ajoute un noeud à la liste de noeud donc à l'anneau
        listNodes.add(node);
        Collections.sort(listNodes, new Comparator<MyNode>() {
            // On tri la liste par ordre d'ID de noeud croissant
            @Override
            public int compare(MyNode n1, MyNode n2) {
                return Long.compare(n1.getID(), n2.getID());
            }
        });

        // On set bien les voisins à chaque noeud ajouter dans la liste
        this.setNeighbors();

    }

    public void removeNode(MyNode node) {
        // On retire le noeud désirer et on remet à jour les voisins
        listNodes.remove(node);
        this.setNeighbors();
    }

    public ArrayList<MyNode> getNodes(){
        return this.listNodes;
    }

    public MyNode getNode(long ID) {
        for (MyNode node : this.listNodes) {
            if (node.getID() == ID) {
                return node;
            }
        }
        return null;
    }

    public MyNode findNeighbor(long id) {
        if (listNodes.isEmpty()) {
            // L'anneau est vide, on ne peut pas trouver de voisin
            return null;
        } else if (listNodes.size() == 1) {
            // Il n'y a qu'un noeud dans l'anneau, il est son propre voisin
            return listNodes.get(0);
        } else {
            // Recherche du noeud le plus proche de l'id dans l'anneau
            MyNode left = listNodes.get(listNodes.size() - 1);
            MyNode right = listNodes.get(0);
            for (int i = 1; i < listNodes.size(); i++) {
                if (id > left.getID() && id <= listNodes.get(i).getID()) {
                    right = listNodes.get(i);
                    break;
                }
                left = listNodes.get(i);
            }
            // Retourne le voisin à droite du noeud trouvé
            return right;
        }
    }

}

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;

public class Ring {

    private ArrayList<Node> listNodes = new ArrayList<>();
    private int identifiant;

    public Ring(int identifiant) {
        this.identifiant = identifiant;
    }


    public void setVoisins() {
        if (listNodes.size() == 1) {
            listNodes.get(0).setNodeLeft(listNodes.get(0));
            listNodes.get(0).setNodeRight(listNodes.get(0));
        }
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

    public void addNode(Node node) {
        listNodes.add(node);

        Collections.sort(listNodes, new Comparator<Node>() {
            @Override
            public int compare(Node n1, Node n2) {
                return Integer.compare(n1.getIdentifiant(), n2.getIdentifiant());
            }
        });

        this.setVoisins();

    }

    public void removeNode(Node node) {
        listNodes.remove(node);
        this.setVoisins();
    }

    public ArrayList<Node> getlistNodes(){
        return this.listNodes;
    }
}

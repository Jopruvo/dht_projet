import peersim.config.Configuration;
import peersim.core.CommonState;
import peersim.core.Control;
import peersim.core.Network;
import peersim.core.Node;

public class MyController implements Control {

    private static final String PAR_RING_PROTOCOL = "ring_protocol";
    private final int ringPid;

    public MyController(String prefix) {
        ringPid = Configuration.getPid(prefix + "." + PAR_RING_PROTOCOL);
    }

    @Override
    public boolean execute() {

        // Création d'un anneau de noeuds
        Ring ring = new Ring(CommonState.r.nextLong());

        // Ajout de chaque noeud dans l'anneau
        for (int i = 0; i < Network.size(); i++) {
            MyNode node = (MyNode) Network.get(i);
            ring.addNode(node);
        }

        // Transmission de messages entre les noeuds dans l'anneau
        for (int i = 0; i < Network.size(); i++) {
            MyNode node = (MyNode) Network.get(i);
            Node nodeLeft = node.getNodeLeft();
            Node nodeRight = node.getNodeRight();

            // Envoi d'un message au noeud à gauche
            ring.getlistNodes().get(i).deliver(nodeLeft, node);

            // Envoi d'un message au noeud à droite
            ring.getlistNodes().get(i).deliver(nodeRight, node);
        }

        // Retourne 'false' pour indiquer que le contrôleur doit être exécuté une seule fois
        return false;
    }

}
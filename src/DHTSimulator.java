import peersim.config.Configuration;
import peersim.core.Control;
import peersim.core.Network;
import peersim.core.CommonState;

public class DHTSimulator implements Control {

    private static final String PAR_RING_PROTOCOL = "ring_protocol";
    private final int ringPid;

    public DHTSimulator(String prefix) {
        ringPid = Configuration.getPid(prefix + "." + PAR_RING_PROTOCOL);
    }

    @Override
    public boolean execute() {

        // Création d'un anneau de noeuds
        Ring ring = new Ring(CommonState.r.nextLong());
        MyNode firstNode = new MyNode(CommonState.r.nextLong(), ringPid);
        ring.addNode(firstNode);

        // Ajout de nouveaux noeuds dans l'anneau
        if (CommonState.r.nextDouble() < 0.5) {
            MyNode newNode = new MyNode(CommonState.r.nextLong(), ringPid);
            int randomNodeIndex = CommonState.r.nextInt(Network.size());
            MyNode randomNode = (MyNode) Network.get(randomNodeIndex);
            ring.addNode(randomNode);
            ring.addNode(newNode);
        }

        // Suppression de noeuds de l'anneau
        if (CommonState.r.nextDouble() < 0.2 && Network.size() > 1) {
            int randomNodeIndex = CommonState.r.nextInt(Network.size());
            MyNode nodeToRemove = (MyNode) Network.get(randomNodeIndex);
            MyNode nodeLeft = nodeToRemove.getNodeLeft();
            MyNode nodeRight = nodeToRemove.getNodeRight();
            nodeToRemove.leave();
            nodeLeft.setNodeRight(nodeRight);
            nodeRight.setNodeLeft(nodeLeft);
            ring.removeNode(nodeToRemove);
        }

        // Transmission de messages entre les noeuds dans l'anneau
        for (int i = 0; i < Network.size(); i++) {
            MyNode node = (MyNode) Network.get(i);
            MyNode nodeLeft = node.getNodeLeft();
            MyNode nodeRight = node.getNodeRight();

            // Envoi d'un message au noeud à gauche
            nodeLeft.deliver(node, node);

            // Envoi d'un message au noeud à droite
            nodeRight.deliver(node, node);
        }

        // Retourne 'false' pour indiquer que le contrôleur doit être exécuté une seule fois
        return false;
    }
}

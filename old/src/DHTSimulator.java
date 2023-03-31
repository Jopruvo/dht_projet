import peersim.config.Configuration;
import peersim.core.Control;
import peersim.core.Network;
import peersim.core.CommonState;

import java.util.Random;

public class DHTSimulator implements Control {

    private static final String PAR_RING_PROTOCOL = "ring_protocol";
    private final int ringPid;
    private final int Nodes;

    private final int addingNodes;
    private final int removingNodes;
    private final int messages;

    // Configuration du nombre de nœuds dans le réseau
    private static final String NODES = "size";
    private static final String ADDINGNODES = "add_nodes";
    private static final String REMOVINGNODES = "remove";
    private static final String MESSAGES = "messages";

    public DHTSimulator(String prefix) {
        ringPid = Configuration.getInt(prefix + "." + PAR_RING_PROTOCOL);
        Nodes = Configuration.getInt(prefix + "." + NODES);
        addingNodes = Configuration.getInt(prefix + "." + ADDINGNODES);
        removingNodes = Configuration.getInt(prefix + "." + REMOVINGNODES);
        messages = Configuration.getInt(prefix + "." + MESSAGES);

    }


    public Ring initialize(){
        // Création d'un anneau de noeuds
        Ring ring = new Ring(CommonState.r.nextLong());
        MyNode firstNode = new MyNode(CommonState.r.nextLong(), ringPid);
        ring.addNode(firstNode);

        //création du nombre de noeuds de départ
        for (int i=0; i< Nodes; i++){
            MyNode newNode = new MyNode(CommonState.r.nextLong(), ringPid);
            ring.addNode(newNode);

        }
        return ring;
    }

    public void round(Ring ring){

        //loop for node creation
        for (int i=0; i< addingNodes; i++){
            MyNode newNode = new MyNode(CommonState.r.nextLong(), ringPid);
            ring.addNode(newNode);
        }

        //loop for node destruction
        for(int i=0; i< removingNodes; i++){
            MyNode randomNode = ring.getRandomNode();
            ring.removeNode(randomNode);
        }

        //loop for message sending
        for(int i=0; i< messages; i++){
            MyNode sender = ring.getRandomNode();
            MyNode receiver = ring.getRandomNode();
            MyNode node = (MyNode) ring.getRandomNode();
            sender.send(receiver, node);

        }

    }

    @Override
    public boolean execute() {
        //creation of the ring
        Ring ring = initialize();

        //loop
        round(ring);

        return false;
    }
}
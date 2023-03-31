import peersim.core.Node;
import peersim.core.Protocol;
import peersim.edsim.EDProtocol;

public class MyNode implements Node, EDProtocol {

    long ID;
    MyNode nodeLeft;
    MyNode nodeRight;
    private String message = null;

    private int ringPid = 1;

    public MyNode(long ID, int ringPid){
        this.ID = ID;
        this.ringPid = ringPid;
    }

    @Override
    public Protocol getProtocol(int i) {
        return null;
    }

    @Override
    public int protocolSize() {
        return 0;
    }

    @Override
    public void setIndex(int i) {

    }

    @Override
    public int getIndex() {
        return 0;
    }

    public long getID(){
        return this.ID;
    }

    public int getRingPid(){
        return this.ringPid;
    }

    @Override
    public Object clone() {
        return null;
    }

    public void setID(long ID) {
        this.ID = ID;
    }

    public void setNodeLeft(MyNode nodeLeft){
        this.nodeLeft = nodeLeft;
    }

    public MyNode getNodeLeft(){
        return this.nodeLeft;
    }

    public long getLeftID(){
        return this.nodeLeft.getID();
    }

    public void setNodeRight(MyNode nodeRight){
        this.nodeRight = nodeRight;
    }

    public MyNode getNodeRight(){
        return this.nodeRight;
    }

    public long getRightID(){
        return this.nodeRight.getID();
    }

    @Override
    public int getFailState() {
        return 0;
    }

    @Override
    public void setFailState(int i) {

    }

    @Override
    public boolean isUp() {
        return false;
    }

    @Override
    public void processEvent(Node node, int pid, Object event) {
        if (event instanceof String) {
            String message = (String) event;
            if (this.message == null) {
                // Réception du message pour la première fois
                this.message = message;
                System.out.println("Node " + this.getID() + " received message: " + message);
                // Diffusion du message aux voisins gauche et droite
                deliver(nodeLeft, message);
                deliver(nodeRight, message);
            }
            else if (!this.message.equals(message)) {
                // Réception du message pour la deuxième fois (après diffusion aux voisins)
                System.out.println("Node " + this.getID() + " received message: " + message);
            }
            // Si le message a déjà été reçu, il est ignoré
        }
    }

    public void deliver(Node sender, Object message) {
        // Vérifier si le message est destiné à ce noeud
        if (this.getID() == sender.getID()) {
            // Traiter le message
            System.out.println("Node " + this.getID() + " received message from node " + ((MyNode)message).getID());
        }
        else{
            this.nodeRight.deliver(sender, message);
        }
    }

    public void send(Node sender, Object message){
        //envoi du message
        System.out.println("Node " + this.getID() + " is sending a message for node " + sender.getID());
        this.nodeRight.deliver(sender, message);
    }


    public void leave() {
        MyNode leftNode = this.getNodeLeft();
        MyNode rightNode = this.getNodeRight();

        // Mise à jour des voisins de gauche et de droite
        leftNode.setNodeRight(rightNode);
        rightNode.setNodeLeft(leftNode);

        // Suppression du nœud de l'anneau
        setNodeLeft(null);
        setNodeRight(null);
    }

    public void join(Ring ring) {
        if (ring.getNodes().isEmpty()) {
            // Si l'anneau est vide, le nouveau noeud est son propre voisin
            this.setNodeLeft(this);
            this.setNodeRight(this);
            ring.addNode(this);
        } else {
            // Recherche du noeud voisin pour l'insertion
            MyNode neighbor = ring.findNeighbor(this.ID);

            // Insertion du nouveau noeud entre le noeud voisin et son voisin de droite
            this.setNodeLeft(neighbor);
            this.setNodeRight(neighbor.getNodeRight());
            neighbor.getNodeRight().setNodeLeft(this);
            neighbor.setNodeRight(this);
            ring.addNode(this);
        }
    }
}

import peersim.core.Node;
import peersim.core.Protocol;
import peersim.edsim.EDProtocol;

public class MyNode implements Node, EDProtocol {

    long ID;
    MyNode nodeLeft;
    MyNode nodeRight;
    private String message = null;
    private static final int pid = 1;

    public MyNode(long ID){
        this.ID = ID;
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

    public void deliver(Node node, Object message) {
        EDProtocol protocol = (EDProtocol) node.getProtocol(pid);
        protocol.processEvent(this, pid, message);
    }

}

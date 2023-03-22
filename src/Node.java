public class Node {

    int identifiant;
    Node nodeLeft;
    Node nodeRight;

    public Node(int identifiant){
        this.identifiant = identifiant;
    }

    public int getIdentifiant(){
        return this.identifiant;
    }

    public void setNodeLeft(Node nodeLeft){
        this.nodeLeft = nodeLeft;
    }

    public int getLeftIdentifiant(){
        return this.nodeLeft.getIdentifiant();
    }

    public void setNodeRight(Node nodeRight){
        this.nodeRight = nodeRight;
    }

    public int getRightIdentifiant(){
        return this.nodeRight.getIdentifiant();
    }

}

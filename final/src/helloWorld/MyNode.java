package helloWorld;

import peersim.core.Node;
import peersim.core.Protocol;
import peersim.edsim.EDProtocol;
import java.util.Random;
public class MyNode implements Node{
    long ID;
    MyNode nodeLeft;
    MyNode nodeRight;
    private String message = null;

    private int ringPid = 1;

    public MyNode(String s){
        this.setID(new Random().nextLong());
        this.setNodeLeft(this);
        this.setNodeRight(this);

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


}

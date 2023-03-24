import peersim.core.Node;
import peersim.core.Protocol;

public class MyNode implements Node {

    long ID;
    MyNode nodeLeft;
    MyNode nodeRight;

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

    public long getLeftID(){
        return this.nodeLeft.getID();
    }

    public void setNodeRight(MyNode nodeRight){
        this.nodeRight = nodeRight;
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
}

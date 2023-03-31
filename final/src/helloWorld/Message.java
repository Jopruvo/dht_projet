package helloWorld;

import peersim.edsim.*;
import peersim.core.Node;

public class Message {

    public final static int HELLOWORLD = 0;

    private int type;
    private String content;

    private int dest;

    private Data data;

    Message(int type, String content, int dest) {
	this.type = type;
	this.content = content;
    this.dest = dest;
    this.data = null;
    }

    public String getContent() {
	return this.content;
    }

    public int getType() {
	return this.type;
    }

    public int getDest(){ return this.dest; }

    public Data getData(){
        return this.data;
    }

    public void setData(Data data){
        this.data = data;
    }
    
}
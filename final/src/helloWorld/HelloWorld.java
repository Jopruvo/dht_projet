package helloWorld;

import peersim.edsim.*;
import peersim.core.*;
import peersim.config.*;

public class HelloWorld implements EDProtocol {
    
    //identifiant de la couche transport
    private int transportPid;

    //objet couche transport
    private HWTransport transport;

    //identifiant de la couche courante (la couche applicative)
    private int mypid;

    //le numero de noeud
    private int nodeId;

    //prefixe de la couche (nom de la variable de protocole du fichier de config)
    private String prefix;


    private HelloWorld nodeLeft;

    private HelloWorld nodeRight;

    private Container container;

    public HelloWorld(String prefix) {
	this.prefix = prefix;
	//initialisation des identifiants a partir du fichier de configuration
	this.transportPid = Configuration.getPid(prefix + ".transport");
	this.mypid = Configuration.getPid(prefix + ".myself");
	this.transport = null;
    this.container = new Container();
    }

    public void setNodeLeft(HelloWorld node){
        this.nodeLeft = node;
    }

    public void setNodeRight(HelloWorld node){
        this.nodeRight = node;
    }

    public HelloWorld getNodeRight(){
        return this.nodeRight;
    }

    public HelloWorld getNodeLeft(){
        return this.nodeLeft;
    }

    public long getNodeID(){
        return this.nodeId;
    }

    public Container getContainer(){
        return this.container;
    }

    public void addNodeToDHT(HelloWorld node){
        long id = node.getNodeID();
        if (this.getNodeID() == this.getNodeRight().getNodeID()){
            //on set les voisins du noeud qu'on ajoute
            node.setNodeLeft(this);

            node.setNodeRight(this.getNodeRight());
            //on set le noeud qu'on ajoute comme voisin des deux noeuds
            this.getNodeRight().setNodeLeft(node);
            this.setNodeRight(node);
            System.out.println("node "+ id + " added");

        }
        if (this.nodeRight.getNodeID() < id) {
            if (this.getNodeID() > this.getNodeRight().getNodeID()) {
                //on set les voisins du noeud qu'on ajoute
                node.setNodeLeft(this);
                node.setNodeRight(this.getNodeRight());
                //on set le noeud qu'on ajoute comme voisin des deux noeuds
                this.getNodeRight().setNodeLeft(node);
                this.setNodeRight(node);
                System.out.println("node "+ id + " added");
            }

            else{
                this.nodeRight.addNodeToDHT(node);
            }
        }

        if (this.nodeRight.getNodeID() > id) {
            if (this.getNodeID() < id) {
                //on set les voisins du noeud qu'on ajoute
                node.setNodeLeft(this);
                node.setNodeRight(this.getNodeRight());
                //on set le noeud qu'on ajoute comme voisin des deux noeuds
                this.getNodeRight().setNodeLeft(node);
                this.setNodeRight(node);
                System.out.println("node " + id + " added");
            }

            else{
                this.nodeRight.addNodeToDHT(node);
            }
        }



    }

    public void removeNodeToDHT(){
        System.out.println(this.getNodeID() + " quit the DHT game");
        this.getNodeRight().setNodeLeft(this.getNodeLeft());
        this.getNodeLeft().setNodeRight(this.getNodeRight());
    }

    //methode appelee lorsqu'un message est recu par le protocole HelloWorld du noeud
    public void processEvent( Node node, int pid, Object event ) {
	this.receive((Message)event);
    }
    
    //methode necessaire pour la creation du reseau (qui se fait par clonage d'un prototype)
    public Object clone() {

	HelloWorld dolly = new HelloWorld(this.prefix);

	return dolly;
    }

    //liaison entre un objet de la couche applicative et un 
    //objet de la couche transport situes sur le meme noeud
    public void setTransportLayer(int nodeId) {
	this.nodeId = nodeId;
	this.transport = (HWTransport) Network.get(this.nodeId).getProtocol(this.transportPid);
    }


    //envoi d'un message (l'envoi se fait via la couche transport)
    public void send(Message msg, Node dest) {

	    this.transport.send(getMyNode(), this.getNodeRight().getMyNode(), msg, this.mypid);
    }

    //affichage a la reception
    private void receive(Message msg) {
        //classic message sending if there is no data transmitted
        if(msg.getData() == null) {
            if (msg.getDest() == this.getNodeID()) {
                System.out.println(this + " : Received " + msg.getContent());
            } else {
                this.send(msg, this.getNodeRight().getMyNode());

            }
        }
        //transmission with data
        else{
            int Id = msg.getData().getId();
            if(this.getNodeID()<=Id && this.getNodeRight().getNodeID()>Id){
                //si le noeud est plus proche du noeud courant que de son voisin de droite on l'ajoute au noeud courant
                if(Id - this.getNodeID()< this.getNodeRight().getNodeID() - Id){
                    this.getContainer().addData(msg.getData());
                    this.getNodeRight().getContainer().addData(msg.getData());
                    this.getNodeLeft().getContainer().addData(msg.getData());
                    System.out.println("data with Id : " + Id + " added to the node : " + this.getNodeID() );
                }

                else {
                    this.send(msg, this.getNodeRight().getMyNode());
                }
            }

            else if(this.getNodeLeft().getNodeID()<Id && this.getNodeID()>=Id){
                //si le noeud est plus proche du noeud courant que de son voisin de gauche on l'ajoute au noeud courant
                if(this.getNodeID() - Id < Id - this.getNodeLeft().getNodeID()){
                    this.getContainer().addData(msg.getData());
                    this.getNodeRight().getContainer().addData(msg.getData());
                    this.getNodeLeft().getContainer().addData(msg.getData());
                    System.out.println("data with Id : " + Id + " added to the node : " + this.getNodeID() );
                }

                else {
                    this.send(msg, this.getNodeRight().getMyNode());
                }
            }

            //cas à l'extrêmité, si l'id de la data est plus grand que tous les ids de tout le monde
            else if(this.getNodeID()<Id && this.getNodeRight().getNodeID()<Id && this.getNodeID()> this.getNodeRight().getNodeID()){
                //dans ce cas on ajoute la data au dernier noeud (cad le noeud courant) et a ses voisins
                this.getContainer().addData(msg.getData());
                this.getNodeRight().getContainer().addData(msg.getData());
                this.getNodeLeft().getContainer().addData(msg.getData());
                System.out.println("data with Id : " + Id + " added to the node : " + this.getNodeID() );
            }

            //sinon on transmet
            else {
                this.send(msg, this.getNodeRight().getMyNode());
            }
        }

    }

    //retourne le noeud courant
    private Node getMyNode() {
	return Network.get(this.nodeId);
    }

    public String toString() {
	return "Node "+ this.nodeId;
    }


}
package helloWorld;

import peersim.edsim.*;
import peersim.core.*;
import peersim.config.*;

import java.util.Random;

/*
  Module d'initialisation de helloWorld: 
  Fonctionnement:
    pour chaque noeud, le module fait le lien entre la couche transport et la couche applicative
    ensuite, il fait envoyer au noeud 0 un message "Hello" a tous les autres noeuds
 */
public class Initializer implements peersim.core.Control {
    
    private int helloWorldPid;

    public Initializer(String prefix) {
	//recuperation du pid de la couche applicative
	this.helloWorldPid = Configuration.getPid(prefix + ".helloWorldProtocolPid");
    }

    public boolean execute() {
	int nodeNb;
	HelloWorld emitter, current;
	Node dest;
	Node last;
	Message helloMsg;

	//recuperation de la taille du reseau
	nodeNb = Network.size();
	//creation du message

	if (nodeNb < 1) {
	    System.err.println("Network size is not positive");
	    System.exit(1);
	}

	//recuperation de la couche applicative de l'emetteur (le noeud 0)
	emitter = (HelloWorld)Network.get(0).getProtocol(this.helloWorldPid);
	emitter.setTransportLayer(0);

	emitter.setNodeRight(emitter);
	emitter.setNodeLeft(emitter);
	//pour chaque noeud, on fait le lien entre la couche applicative et la couche transport
	//puis on fait envoyer au noeud 0 un message "Hello"


	for (int i = 1; i < nodeNb; i++) {

		helloMsg = new Message(Message.HELLOWORLD,"Hello!!", i);

		dest = Network.get(i);
		current = (HelloWorld)dest.getProtocol(this.helloWorldPid);
		current.setTransportLayer(i);
		emitter.send(helloMsg, dest);
		//add node to dht
		emitter.addNodeToDHT(current);

		//test of data container
		int j = new Random().nextInt(i);
		Data data = new Data(j, "oui");
		Message msg= new Message(66, "execute order", -1);
		msg.setData(data);
		emitter.send(msg, dest);

	}



	System.out.println("Initialization completed");
	return false;
    }



}




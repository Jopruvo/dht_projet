public class Main {
    public static void main(String[] args) {
        MyNode node2 = new MyNode(2, 1);
        Ring ring = new Ring(1);
        ring.addNode(node2);
        MyNode node1 = new MyNode(1, 1);
        ring.addNode(node1);
        System.out.println(ring.getNodes().get(0).getID());
        System.out.println(ring.getNodes().get(1).getID());
        System.out.println("Et les voisins 1er noeud ?");
        System.out.println(ring.getNodes().get(0).getLeftID());
        System.out.println(ring.getNodes().get(0).getRightID());
        System.out.println("Et les voisins 2e noeud ?");
        System.out.println(ring.getNodes().get(1).getLeftID());
        System.out.println(ring.getNodes().get(1).getRightID());
        ring.removeNode(node1);
        System.out.println("hehe on l'a delete");
        System.out.println(ring.getNodes().get(0).getID());
        System.out.println("Et les voisins 1er noeud ?");
        System.out.println(ring.getNodes().get(0).getLeftID());
        System.out.println(ring.getNodes().get(0).getRightID());
    }
}
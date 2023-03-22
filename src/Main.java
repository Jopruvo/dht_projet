public class Main {
    public static void main(String[] args) {
        Node node2 = new Node(2);
        Ring ring = new Ring(1);
        ring.addNode(node2);
        Node node1 = new Node(1);
        ring.addNode(node1);
        System.out.println(ring.getlistNodes().get(0).getIdentifiant());
        System.out.println(ring.getlistNodes().get(1).getIdentifiant());
        System.out.println("Et les voisins 1er noeud ?");
        System.out.println(ring.getlistNodes().get(0).getLeftIdentifiant());
        System.out.println(ring.getlistNodes().get(0).getRightIdentifiant());
        System.out.println("Et les voisins 2e noeud ?");
        System.out.println(ring.getlistNodes().get(1).getLeftIdentifiant());
        System.out.println(ring.getlistNodes().get(1).getRightIdentifiant());
        ring.removeNode(node1);
        System.out.println("hehe on l'a delete");
        System.out.println(ring.getlistNodes().get(0).getIdentifiant());
        System.out.println("Et les voisins 1er noeud ?");
        System.out.println(ring.getlistNodes().get(0).getLeftIdentifiant());
        System.out.println(ring.getlistNodes().get(0).getRightIdentifiant());
    }
}
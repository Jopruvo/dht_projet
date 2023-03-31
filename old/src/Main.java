import java.io.FileInputStream;
import java.io.IOException;
import java.util.Properties;

import peersim.config.Configuration;

public class Main {
    public static void main(String[] args) throws IOException {
        // Chargement de la configuration
        Properties props = new Properties();
        FileInputStream fis = new FileInputStream("config.txt");
        props.load(fis);
        fis.close();
        Configuration.setConfig(props);

        // Création et lancement de la simulation
        DHTSimulator simulator = new DHTSimulator("simulation");
        simulator.execute();

    }
}

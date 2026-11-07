package serveur;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

public class ServeurMT extends Thread {

    private static int compteur = 0;

    public static synchronized int incrementerCompteur() {
        compteur++;
        return compteur;
    }

    public void run() {
        try {
            ServerSocket socketServeur = new ServerSocket(1234);

            System.out.println("Serveur calculatrice démarré...");
            System.out.println("En attente des clients...");

            while (true) {
                Socket socket = socketServeur.accept();

                System.out.println("Nouveau client : "
                        + socket.getRemoteSocketAddress());

                ClientProcess traitement =
                        new ClientProcess(socket);

                traitement.start();
            }

        } catch (IOException e) {
            System.out.println("Erreur serveur : " + e.getMessage());
        }
    }

    public static void main(String[] args) {
        new ServeurMT().start();
    }
}
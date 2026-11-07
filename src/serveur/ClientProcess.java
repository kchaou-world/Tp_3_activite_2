package serveur;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;

import operation.Operation;

public class ClientProcess extends Thread {

    private Socket socket;

    public ClientProcess(Socket socket) {
        this.socket = socket;
    }

    public void run() {
        try {
            ObjectOutputStream out =
                    new ObjectOutputStream(socket.getOutputStream());
            out.flush();

            ObjectInputStream in =
                    new ObjectInputStream(socket.getInputStream());

            Operation op = (Operation) in.readObject();

            double a = op.getOperande1();
            double b = op.getOperande2();
            char operateur = op.getOperateur();

            double resultat = 0;

            switch (operateur) {
                case '+':
                    resultat = a + b;
                    break;

                case '-':
                    resultat = a - b;
                    break;

                case '*':
                    resultat = a * b;
                    break;

                case '/':
                    if (b == 0) {
                        throw new ArithmeticException("Division par zéro");
                    }
                    resultat = a / b;
                    break;

                default:
                    throw new IllegalArgumentException("Opérateur invalide");
            }

            int nombreOperations =
                    ServeurMT.incrementerCompteur();

            System.out.println("Opération : " + a + " "
                    + operateur + " " + b);

            System.out.println("Résultat : " + resultat);

            System.out.println("Nombre total d'opérations : "
                    + nombreOperations);

            out.writeObject(Double.valueOf(resultat));
            out.flush();

        } catch (IOException | ClassNotFoundException
                 | ArithmeticException | IllegalArgumentException e) {

            System.out.println("Erreur traitement client : "
                    + e.getMessage());

        } finally {
            try {
                socket.close();
            } catch (IOException e) {
                System.out.println("Erreur fermeture socket");
            }
        }
    }
}
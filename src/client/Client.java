package client;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.util.Scanner;

import operation.Operation;

public class Client {

    public static void main(String[] args) {

        try {
            Socket socket = new Socket("localhost", 1234);

            ObjectOutputStream out =
                    new ObjectOutputStream(socket.getOutputStream());
            out.flush();

            ObjectInputStream in =
                    new ObjectInputStream(socket.getInputStream());

            Scanner clavier = new Scanner(System.in);

            System.out.print("Premier opérande : ");
            double a = clavier.nextDouble();

            System.out.print("Opérateur (+, -, *, /) : ");
            char operateur = clavier.next().charAt(0);

            System.out.print("Deuxième opérande : ");
            double b = clavier.nextDouble();

            Operation op = new Operation(a, operateur, b);

            out.writeObject(op);
            out.flush();

            Double resultat = (Double) in.readObject();

            System.out.println("Résultat reçu du serveur : "
                    + resultat);

            socket.close();

        } catch (IOException | ClassNotFoundException e) {
            System.out.println("Erreur client : " + e.getMessage());
        }
    }
}
package ucb.busca;

import ucb.busca.servidores.util.ArtigoCientifico;

import java.io.*;
import java.net.Socket;
import java.util.List;
import java.util.Scanner;

public class Main{
	private static final Scanner SCANNER = new Scanner(System.in);


    public static void main(String[] args) throws IOException {
    	
        final String SERVIDOR = "localhost";
        final int PORTA = 54321;

        try {
            Socket servidor = new Socket(SERVIDOR, PORTA);
            System.out.println("Conectado ao servidor!");

            System.out.print("Digite o que deseja pesquisar: ");
            String subString = SCANNER.next();

            PrintWriter dataToServidor = new PrintWriter(servidor.getOutputStream(), true);
            dataToServidor.println(subString);

            ObjectInputStream dataFromServidor = new ObjectInputStream(servidor.getInputStream());


            List<ArtigoCientifico> artigosTotal = (List<ArtigoCientifico>) dataFromServidor.readObject();

            for(int i = 0 ; i < artigosTotal.size(); i++){
                System.out.println(artigosTotal.get(i).getTitulo());

                System.out.println(i);
            }

            System.out.println(artigosTotal.size());

            dataFromServidor.close();
            dataToServidor.close();
            servidor.close();

        } catch(Exception e) {
            System.out.println("Erro no cliente: " + e.getMessage());
        }
    }

}

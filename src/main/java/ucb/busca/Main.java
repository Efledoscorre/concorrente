package ucb.busca;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;

import ucb.busca.testeAlgoritmos.SearchAlgorithm;
import ucb.busca.testeAlgoritmos.ZAlgorithm;

public class Main{

    private static SearchAlgorithm searchAlgorithm = new ZAlgorithm();

    public static void main(String[] args) throws IOException {

        String SERVIDOR = "localhost";
        int PORTA = 12345;
        try {
            Socket servidor = new Socket(SERVIDOR, PORTA);
            System.out.println("Conectado ao servidor!");

            //Permite com que o cliente leia mensagens
            BufferedReader entrada = new BufferedReader(new InputStreamReader(servidor.getInputStream()));

            //Permite com que o cliente envie mensagens
            PrintWriter saida = new PrintWriter(servidor.getOutputStream(), true);

            String mensagem = entrada.readLine();
            System.out.println("Mensagem do servidor: " + mensagem);

            saida.println("Obrigado, servidor! Estou conectado.");

            entrada.close();
            saida.close();
            servidor.close();

        } catch(IOException e) {
            System.out.println("Erro no cliente: " + e.getMessage());
        }
    }

}

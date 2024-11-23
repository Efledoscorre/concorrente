package ucb.busca;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.List;
import java.util.Scanner;

import ucb.busca.servidores.testeAlgoritmos.ZAlgorithm;

public class Main{
	static Scanner scanner = new Scanner(System.in);
	
    private static ZAlgorithm searchAlgorithm = new ZAlgorithm();

    public static void main(String[] args) throws IOException {

    	System.out.println("Digite o que deseja pesquisar: ");
    	String subString = scanner.next();
    	
        String SERVIDOR = "localhost";
        int PORTA = 54321;
        try {
            Socket servidor = new Socket(SERVIDOR, PORTA);
            System.out.println("Conectado ao servidor!");

            BufferedReader entrada = new BufferedReader(new InputStreamReader(servidor.getInputStream()));

            PrintWriter saida = new PrintWriter(servidor.getOutputStream(), true);

            saida.println(subString);

            List<String> mensagem = entrada.lines().toList();
            System.out.println("Mensagem do servidor: ");

            mensagem.forEach(msg -> System.out.println(msg));

            entrada.close();
            saida.close();
            servidor.close();

        } catch(IOException e) {
            System.out.println("Erro no cliente: " + e.getMessage());
        }
    }

}

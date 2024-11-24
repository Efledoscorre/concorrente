package ucb.busca.servidores;

import java.io.*;
import java.net.*;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

import ucb.busca.servidores.testeAlgoritmos.AhoCorasickAlgorithm;
import ucb.busca.servidores.testeAlgoritmos.SearchAlgorithm;

public class ServidorB {
	
	   private static final Path path = Paths.get("src/main/java/resources/data/data_B.json");
	   private static final SearchAlgorithm algoritmobusca = new AhoCorasickAlgorithm();
	    
    public static void main(String[] args) throws IOException {
    	byte[] bytes = Files.readAllBytes(path);
        String texto = new String(bytes);
         
        int PORTA = 12345;

        try {
            ServerSocket servidor = new ServerSocket(PORTA);
            System.out.println("Servidor escutando na porta: " + PORTA + " ...");


            Socket cliente = servidor.accept();
            System.out.println("Cliente conectado: " + cliente.getInetAddress().getHostAddress());


            BufferedReader entrada = new BufferedReader(new InputStreamReader(cliente.getInputStream()));


            PrintWriter saida = new PrintWriter(cliente.getOutputStream(), true);

            saida.println("Bem-vindo ao servidor!");
            saida.flush();
            String substring = entrada.readLine();
            algoritmobusca.buscaSubString(texto, substring);
            System.out.println("Mensagem do cliente: " + substring);

            entrada.close();
            saida.close();
            cliente.close();
            servidor.close();
        }catch(Exception e) {
            System.out.println("Erro no servidor: " + e.getMessage());
        }


    }
}
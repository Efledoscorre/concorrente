package ucb.busca.servidores;

import java.io.*;
import java.net.*;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

import ucb.busca.servidores.testeAlgoritmos.AhoCorasickAlgorithm;
import ucb.busca.servidores.testeAlgoritmos.SearchAlgorithm;

public class ServidorB {

    private static final Path PATH_DADOS_JSON = Paths.get("src/main/java/resources/data/data_B.json");
    private static final SearchAlgorithm algoritmobusca = new AhoCorasickAlgorithm();
	    
    public static void main(String[] args) throws IOException {
    	byte[] bytes = Files.readAllBytes(PATH_DADOS_JSON);
        String texto = new String(bytes);
         
        int PORTA = 12345;

        try {
            ServerSocket servidor = new ServerSocket(PORTA);
            System.out.println("Servidor escutando na porta: " + PORTA + " ...");

            Socket cliente = servidor.accept();
            System.out.println("Cliente conectado: " + cliente.getInetAddress().getHostAddress());

            BufferedReader entrada = new BufferedReader(new InputStreamReader(cliente.getInputStream()));

            System.out.println("Mensagem do SERVIDOR A: " + entrada.readLine());

            ObjectOutputStream saida = new ObjectOutputStream(cliente.getOutputStream());

            saida.writeObject("Bem-vindo ao servidor B!");
            saida.flush();

            entrada.close();
            saida.close();
            cliente.close();
            servidor.close();
        }catch(Exception e) {
            System.out.println("Erro no servidor: " + e.getMessage());
        }

    }
}
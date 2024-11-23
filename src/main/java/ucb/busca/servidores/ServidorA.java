package ucb.busca.servidores;

import ucb.busca.servidores.testeAlgoritmos.SearchAlgorithm;
import ucb.busca.servidores.testeAlgoritmos.ZAlgorithm;

import java.io.*;
import java.net.*;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;


public class ServidorA{
    private static final Path path = Paths.get("src/main/java/resources/data/data_A.json");
    private static final ZAlgorithm algoritmobusca = new ZAlgorithm();
    public static void main(String[] args) throws IOException {
        byte[] bytes = Files.readAllBytes(path);
        String texto = new String(bytes);
        criandoCliente();
    	criandoServidor(texto);

    }


    private static void criandoCliente() {
        String SERVIDOR = "localhost";
        int PORTA = 12345;
        try {
            Socket servidor = new Socket(SERVIDOR, PORTA);
            System.out.println("Conectado ao servidor!");


            BufferedReader entrada = new BufferedReader(new InputStreamReader(servidor.getInputStream()));


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
    
    
    private static void criandoServidor(String texto){
        int PORTA = 54321;

        try {
            ServerSocket servidor = new ServerSocket(PORTA);
            System.out.println("Servidor escutando na porta: " + PORTA + " ...");


            Socket cliente = servidor.accept();
            System.out.println("Cliente conectado: " + cliente.getInetAddress().getHostAddress());


            BufferedReader entrada = new BufferedReader(new InputStreamReader(cliente.getInputStream()));


            PrintWriter saida = new PrintWriter(cliente.getOutputStream(), true);


            String substring = entrada.readLine();
            algoritmobusca.buscaSubString(texto, substring, saida);
            System.out.println("Mensagem do cliente: " + substring);

            saida.println(algoritmobusca.palavrasEncontradas);
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
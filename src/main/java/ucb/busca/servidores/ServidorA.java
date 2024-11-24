package ucb.busca.servidores;

import ucb.busca.servidores.testeAlgoritmos.*;
import ucb.busca.servidores.util.ArtigoCientifico;

import java.io.*;
import java.net.*;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;


public class ServidorA{
    private static final Path PATH_DADOS_JSON = Paths.get("src/main/java/resources/data/data_A.json");
    private static final SearchAlgorithm ALGORITMO_BUSCA = new ZAlgorithm();
    private static String substring;

    public static void main(String[] args) throws IOException {
        byte[] bytes = Files.readAllBytes(PATH_DADOS_JSON);
        String texto = new String(bytes);

//        ALGORITMO_BUSCA.buscaSubString(texto, "probability distributions with");
// 		  ALGORITMO_BUSCA.buscaSubString(texto, "radiatively");

//        criandoCliente();
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
            
            saida.println(substring);
            
            String mensagem = entrada.readLine();
            System.out.println("Mensagem do servidor: " + mensagem);


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
            List<ArtigoCientifico> artigosCientificos = ALGORITMO_BUSCA.buscaSubString(texto, substring);
            System.out.println("Mensagem do cliente: " + substring);

            saida.println(artigosCientificos);
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
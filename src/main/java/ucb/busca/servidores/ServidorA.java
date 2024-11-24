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
    private static final SearchAlgorithm ALGORITMO_BUSCA = new AhoCorasickAlgorithm();
    private static String substring;

    public static void main(String[] args) throws IOException {
        byte[] bytes = Files.readAllBytes(PATH_DADOS_JSON);
        String texto = new String(bytes);

        List<ArtigoCientifico> artigoCientificos = ALGORITMO_BUSCA.buscaSubString(texto, "Probing new physics in");
//        artigoCientificos.forEach(System.out::println);
//        ALGORITMO_BUSCA.buscaSubString(texto, "probability distributions with");
// 		  ALGORITMO_BUSCA.buscaSubString(texto, "radiatively");

//        criaCliente();
        criaServidor(texto);

    }

    private static void criaServidor(String texto){
        int PORTA = 54321;

        try {
            ServerSocket servidor = new ServerSocket(PORTA);
            System.out.println("Servidor escutando na porta: " + PORTA + " ...");


            Socket cliente = servidor.accept();
            System.out.println("Cliente conectado: " + cliente.getInetAddress().getHostAddress());

            BufferedReader dataFromServidor = new BufferedReader(new InputStreamReader(cliente.getInputStream()));

            String substring = dataFromServidor.readLine();
            System.out.println("Mensagem do MAIN: " + substring);

    //            List<ArtigoCientifico> artigosCientificos = ALGORITMO_BUSCA.buscaSubString(texto, substring);

            mandaSubstringToServidorB(substring);



    //            PrintWriter saida = new PrintWriter(cliente.getOutputStream(), true);
            ObjectOutputStream saida = new ObjectOutputStream(cliente.getOutputStream());

    //            saida.println(artigosCientificos);
            saida.writeObject(List.of(1));
            saida.flush();

            dataFromServidor.close();
            saida.close();
            cliente.close();
            servidor.close();
        }catch(Exception e) {
            System.out.println("Erro no servidor: " + e.getMessage());
        }
    }

    private static void mandaSubstringToServidorB(String substring){

        String SERVIDOR = "localhost";
        int PORTA = 12345;
        try {
            Socket servidorB = new Socket(SERVIDOR, PORTA);
            System.out.println("Servidor A está conectado ao servidor B!");

            PrintWriter dataToServidorB = new PrintWriter(servidorB.getOutputStream(), true);
            dataToServidorB.println(substring);

//            BufferedReader entrada = new BufferedReader(new InputStreamReader(servidorB.getInputStream()));

            ObjectInputStream dataFromServidor = new ObjectInputStream(servidorB.getInputStream());

            Object object = dataFromServidor.readObject();
//            String resposta = entrada.readLine();

            System.out.println("Mensagem do servidor: " + object);

            dataFromServidor.close();
            dataToServidorB.close();
            servidorB.close();

        } catch(Exception e) {
            System.out.println("Erro no cliente: " + e.getMessage());
        }

    }
}
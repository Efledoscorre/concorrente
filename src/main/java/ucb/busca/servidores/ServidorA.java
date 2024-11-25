package ucb.busca.servidores;

import ucb.busca.servidores.testeAlgoritmos.*;
import ucb.busca.servidores.util.ArtigoCientifico;

import javax.swing.*;
import java.io.*;
import java.net.*;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;


public class ServidorA {
    private static final Path PATH_DADOS_JSON = Paths.get("src/main/java/resources/data/data_A.json");
    private static final SearchAlgorithm ALGORITMO_BUSCA = new ZAlgorithm();
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

    private static void criaServidor(String texto) {
        int PORTA = 54321;

        try {
            ServerSocket servidor = new ServerSocket(PORTA);
            System.out.println("Servidor escutando na porta: " + PORTA + " ...");


            Socket cliente = servidor.accept();
            System.out.println("Cliente conectado: " + cliente.getInetAddress().getHostAddress());

            BufferedReader dataFromCliente = new BufferedReader(new InputStreamReader(cliente.getInputStream()));

            String substring = dataFromCliente.readLine();
            System.out.println("Mensagem do MAIN: " + substring);

            List<ArtigoCientifico> artigosDoServidorA = ALGORITMO_BUSCA.buscaSubString(texto, substring);

            List<ArtigoCientifico> artigosDoServidorB = mandaSubstringToServidorB(substring);

            List<ArtigoCientifico> artigosTotal = new ArrayList<>();



            //            PrintWriter saida = new PrintWriter(cliente.getOutputStream(), true);
            ObjectOutputStream saida = new ObjectOutputStream(cliente.getOutputStream());

            //            saida.println(artigosCientificos);


            artigosTotal.addAll(artigosDoServidorA);
            artigosTotal.addAll(artigosDoServidorB);
            saida.writeObject(artigosTotal);
            saida.flush();

            dataFromCliente.close();
            saida.close();
            cliente.close();
            servidor.close();
        } catch (Exception e) {
            System.out.println("Erro no servidor: " + e.getMessage());
        }
    }

    private static List<ArtigoCientifico> mandaSubstringToServidorB(String substring) {

        String SERVIDOR = "localhost";
        int PORTA = 12345;
        try {
            Socket servidorB = new Socket(SERVIDOR, PORTA);
            System.out.println("Servidor A está conectado ao servidor B!");

            PrintWriter dataToServidorB = new PrintWriter(servidorB.getOutputStream(), true);
            dataToServidorB.println(substring);

//            BufferedReader entrada = new BufferedReader(new InputStreamReader(servidorB.getInputStream()));

            ObjectInputStream dataFromServidorB = new ObjectInputStream(servidorB.getInputStream());

            List<ArtigoCientifico> artigosDoServidorB = (List<ArtigoCientifico>) dataFromServidorB.readObject();
//            String resposta = entrada.readLine();


            dataFromServidorB.close();
            dataToServidorB.close();
            servidorB.close();

            return artigosDoServidorB;

        } catch (Exception e) {
            System.out.println("Erro no cliente: " + e.getMessage());
        }

    return Collections.emptyList();

    }

}
package ucb.busca.servidores;

import ucb.busca.servidores.testeAlgoritmos.*;
import ucb.busca.servidores.util.ArtigoCientifico;

import java.io.*;
import java.net.*;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

public class ServidorA {
    private static final Path PATH_DADOS_JSON = Paths.get("src/main/java/resources/data/data_A.json");
    private static final int PORTA = 54321;
    private static final SearchAlgorithm ALGORITMO_BUSCA = new ZAlgorithm();

    public static void main(String[] args) throws IOException {
        byte[] bytes = Files.readAllBytes(PATH_DADOS_JSON);
        String texto = new String(bytes);

        criaServidor(texto);
    }

    private static void criaServidor(String texto) {

        try {
            ServerSocket servidor = new ServerSocket(PORTA);
            System.out.println("Servidor escutando na porta: " + PORTA + " ...");


            Socket cliente = servidor.accept();
            System.out.println("Cliente conectado: " + cliente.getInetAddress().getHostAddress());

            BufferedReader dataFromCliente = new BufferedReader(new InputStreamReader(cliente.getInputStream()));

            String substring = dataFromCliente.readLine();
            System.out.println("Mensagem do MAIN: " + substring);

            ExecutorService executorThreadB = Executors.newSingleThreadExecutor();
            Future<List<ArtigoCientifico>> future = executorThreadB.submit(() -> mandaSubstringToServidorB(substring));

            List<ArtigoCientifico> artigosDoServidorB = future.get();
            executorThreadB.shutdown();

            long inicio = System.currentTimeMillis();
            List<ArtigoCientifico> artigosDoServidorA = ALGORITMO_BUSCA.buscaSubString(texto, substring);
            long fim = System.currentTimeMillis();

            List<ArtigoCientifico> artigosTotal = new ArrayList<>();

            ObjectOutputStream dataToClient = new ObjectOutputStream(cliente.getOutputStream());

            artigosTotal.addAll(artigosDoServidorA);
            artigosTotal.addAll(artigosDoServidorB);

            dataToClient.writeObject(artigosTotal);
            dataToClient.flush();

            System.out.printf("""
                    
                    %d artigos encontrados.
                    Levou %dms
                    """, artigosDoServidorA.size(), Math.abs(inicio - fim));

            dataFromCliente.close();
            dataToClient.close();
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


            ObjectInputStream dataFromServidorB = new ObjectInputStream(servidorB.getInputStream());

            List<ArtigoCientifico> artigosDoServidorB = (List<ArtigoCientifico>) dataFromServidorB.readObject();


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
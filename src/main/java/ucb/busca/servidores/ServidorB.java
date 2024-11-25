package ucb.busca.servidores;

import java.io.*;
import java.net.*;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;

import ucb.busca.servidores.testeAlgoritmos.SearchAlgorithm;
import ucb.busca.servidores.testeAlgoritmos.ZAlgorithm;
import ucb.busca.servidores.util.ArtigoCientifico;

public class ServidorB {

    private static final Path PATH_DADOS_JSON = Paths.get("src/main/java/resources/data/data_B.json");
    private static final int PORTA = 12345;
    private static final SearchAlgorithm ALGORITMO_BUSCA = new ZAlgorithm();

    public static void main(String[] args) throws IOException {
    	byte[] bytes = Files.readAllBytes(PATH_DADOS_JSON);
        String texto = new String(bytes);

        try {
            ServerSocket servidor = new ServerSocket(PORTA);
            System.out.println("Servidor escutando na porta: " + PORTA + " ...");

            Socket cliente = servidor.accept();
            System.out.println("Cliente conectado: " + cliente.getInetAddress().getHostAddress());

            BufferedReader entrada = new BufferedReader(new InputStreamReader(cliente.getInputStream()));

            String substring = entrada.readLine();
            System.out.println("Mensagem do SERVIDOR A: " + substring);

            ObjectOutputStream saida = new ObjectOutputStream(cliente.getOutputStream());

            long inicio = System.currentTimeMillis();
            List<ArtigoCientifico> artigoCientificos = ALGORITMO_BUSCA.buscaSubString(texto, substring);
            long fim = System.currentTimeMillis();

            saida.writeObject(artigoCientificos);
            saida.flush();

            System.out.printf("""
                    
                    %d artigos encontrados.
                    Levou %dms
                    """, artigoCientificos.size(), Math.abs(inicio - fim));

            entrada.close();
            saida.close();
            cliente.close();
            servidor.close();
        }catch(Exception e) {
            System.out.println("Erro no servidor: " + e.getMessage());
        }

    }
}
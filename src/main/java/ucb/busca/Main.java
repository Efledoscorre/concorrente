package ucb.busca;

import ucb.busca.testeAlgoritmos.RabinAlgorithm;
import ucb.busca.testeAlgoritmos.SearchAlgorithm;
import ucb.busca.testeAlgoritmos.ZAlgorithm;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

public class Main{

    private static SearchAlgorithm searchAlgorithm = new ZAlgorithm();

    public static void main(String[] args) throws IOException {

        Path path = Paths.get("src/main/java/resources/data/data_A.json");

        byte[] bytes = Files.readAllBytes(path);

        String text = new String(bytes);
        String substring = "Nucleosynthesis";

        long inicio = System.currentTimeMillis();
        searchAlgorithm.buscaSubString(text, substring);
        long fim = System.currentTimeMillis();

        System.out.println("Tempo de execução: " + Math.abs(inicio - fim) + "ms");


    }

}

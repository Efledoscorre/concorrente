package ucb.busca.servidores.testeAlgoritmos;

import java.util.regex.Pattern;

public interface SearchAlgorithm {

    Pattern pattern = Pattern.compile("\"(\\d+)\"");

    void buscaSubString(String text, String substring);

}

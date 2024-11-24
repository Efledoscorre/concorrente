package ucb.busca.servidores.testeAlgoritmos;

import ucb.busca.servidores.util.ArtigoCientifico;

import java.util.List;
import java.util.regex.Pattern;

public interface SearchAlgorithm {
    Pattern PATTERN = Pattern.compile("\"(\\d+)\":\"");

    List<ArtigoCientifico> buscaSubString(String text, String substring);

}

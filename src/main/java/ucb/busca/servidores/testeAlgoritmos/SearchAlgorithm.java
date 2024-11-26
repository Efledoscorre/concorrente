package ucb.busca.servidores.testeAlgoritmos;

import org.json.JSONObject;
import ucb.busca.servidores.util.ArtigoCientifico;

import java.util.List;

public interface SearchAlgorithm {

    List<ArtigoCientifico> buscaSubString(JSONObject json, String substring);

}

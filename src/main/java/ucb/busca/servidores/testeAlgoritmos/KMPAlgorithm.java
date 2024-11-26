package ucb.busca.servidores.testeAlgoritmos;

import org.json.JSONObject;
import ucb.busca.servidores.util.ArtigoCientifico;

import java.util.*;

public class KMPAlgorithm implements SearchAlgorithm{

    @Override
    public List<ArtigoCientifico> buscaSubString(JSONObject json, String substring) {
        System.out.println("SUBSTRING A SER ENCONTRADO: " + substring);
        LinkedList<ArtigoCientifico> artigosCientificos = new LinkedList<>();

        JSONObject tituloObj = json.getJSONObject("title");
        JSONObject abstractObj = json.getJSONObject("abstract");
        JSONObject labelObj = json.getJSONObject("label");

        Iterator<String> chaves = tituloObj.keys();

        while (chaves.hasNext()) {
            String chave = chaves.next();

            String titulo = tituloObj.getString(chave);
            String resumo = abstractObj.getString(chave);
            String rotulo = labelObj.getString(chave);

            if (existsSubstring(substring.toLowerCase(), titulo.toLowerCase())){
                artigosCientificos.add(new ArtigoCientifico(titulo, resumo, rotulo));
                continue;
            }

            if (existsSubstring(substring.toLowerCase(), resumo.toLowerCase())){
                artigosCientificos.add(new ArtigoCientifico(titulo, resumo, rotulo));
                continue;
            }

            if (existsSubstring(substring.toLowerCase(), rotulo.toLowerCase()))
                artigosCientificos.add(new ArtigoCientifico(titulo, resumo, rotulo));
        }

        return artigosCientificos;
    }

    private static boolean existsSubstring(String pat, String txt) {
        int n = txt.length();
        int m = pat.length();

        int[] lps = new int[m];
        boolean isFound = false;

        constructLps(pat, lps);

        int i = 0;
        int j = 0;

        while (i < n) {
            if (txt.charAt(i) == pat.charAt(j)) {
                i++;
                j++;

                if (j == m) {
                    isFound = true;
                    break;
                }
            }

            else {

                if (j != 0)
                    j = lps[j - 1];
                else
                    i++;
            }
        }
        return isFound;
    }

    private static void constructLps(String pat, int[] lps) {

        int len = 0;

        lps[0] = 0;

        int i = 1;
        while (i < pat.length()) {

            if (pat.charAt(i) == pat.charAt(len)) {
                len++;
                lps[i] = len;
                i++;
            }

            else {
                if (len != 0) {

                    len = lps[len - 1];
                }
                else {

                    lps[i] = 0;
                    i++;
                }
            }
        }
    }
}

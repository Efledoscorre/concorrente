package ucb.busca.servidores.testeAlgoritmos;

import org.json.JSONObject;
import ucb.busca.servidores.util.ArtigoCientifico;

import java.util.*;
import java.util.regex.Matcher;

public class KMPAlgorithm implements SearchAlgorithm{
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

    private static List<Integer> search(String pat, String txt) {
        int n = txt.length();
        int m = pat.length();

        int[] lps = new int[m];
        List<Integer> res = new ArrayList<>();

        constructLps(pat, lps);

        int i = 0;
        int j = 0;

        while (i < n) {
            if (txt.charAt(i) == pat.charAt(j)) {
                i++;
                j++;

                if (j == m) {
                    res.add(i - j);

                    j = lps[j - 1];
                }
            }

            else {

                if (j != 0)
                    j = lps[j - 1];
                else
                    i++;
            }
        }
        return res;
    }

    @Override
    public List<ArtigoCientifico> buscaSubString(String text, String substring) {
        List<Integer> res = search(substring, text);
        System.out.println(res);
        Set<Integer> chavesArtigosEncontrados = new HashSet<>();
        Matcher matcher = null;

        for (int i = 0; i < res.size(); i++){
            StringBuilder substringBuilder = new StringBuilder(substring);

            int indexSubstringNoTexto = res.get(i);

            while(true) {

                matcher = PATTERN.matcher(substringBuilder);

                if (matcher.find())
                    break;

                substringBuilder.insert(0, text.charAt(--indexSubstringNoTexto));
            }
            String chaveSubstring = matcher.group();

            Integer chaveComoNumero = Integer.valueOf(chaveSubstring.replaceAll("\"", "").replaceAll(":", ""));
            chavesArtigosEncontrados.add(chaveComoNumero);
        }
        chavesArtigosEncontrados.forEach(System.out::println);
        return retornaArtigosCientificos(chavesArtigosEncontrados, text);
    }

    private List<ArtigoCientifico> retornaArtigosCientificos(Set<Integer> chavesSubstrings, String text){

        List<ArtigoCientifico> artigosCientificos = new LinkedList<>();

        final JSONObject artigosJson = new JSONObject(text);

        final JSONObject titulosJson = artigosJson.getJSONObject("title");
        final JSONObject resumoJson = artigosJson.getJSONObject("abstract");
        final JSONObject labelJson = artigosJson.getJSONObject("label");

        chavesSubstrings.forEach(chave -> {
            String titulo = titulosJson.getString(String.valueOf(chave));
            String resumo = resumoJson.getString(String.valueOf(chave));
            String rotulo = labelJson.getString(String.valueOf(chave));

            artigosCientificos.add(new ArtigoCientifico(titulo, resumo, rotulo));
        });
        System.out.println(artigosCientificos.get(0).getTitulo());
        return artigosCientificos;
    }
}

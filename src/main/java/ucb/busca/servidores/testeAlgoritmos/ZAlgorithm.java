package ucb.busca.servidores.testeAlgoritmos;

import org.json.JSONObject;
import ucb.busca.servidores.util.ArtigoCientifico;

import java.util.*;
import java.util.regex.Matcher;

public class ZAlgorithm implements SearchAlgorithm{

    @Override
    public List<ArtigoCientifico> buscaSubString(String text, String substring){

        Set<Integer> chavesArtigosEncontrados = new HashSet<>();
        Matcher matcher = null;

        String concat = substring.toLowerCase() + "$" + text.toLowerCase();

        int l = concat.length();

        int[] Z = new int[l];

        getZarr(concat, Z);

        for(int i = 0; i < l; ++i){

            if(Z[i] == substring.length()){

                StringBuilder substringBuilder = new StringBuilder(substring);

                int indexSubstringNoTexto = i - substring.length() - 1;


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
        }

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

        return artigosCientificos;
    }

    private void getZarr(String str, int[] Z) {

        int n = str.length();

        int L = 0, R = 0;

        for(int i = 1; i < n; ++i) {

            if(i > R){

                L = R = i;

                while(R < n && str.charAt(R - L) == str.charAt(R))
                    R++;

                Z[i] = R - L;
                R--;

            }
            else{

                int k = i - L;

                if(Z[k] < R - i + 1)
                    Z[i] = Z[k];
                else{
                    L = i;
                    while(R < n && str.charAt(R - L) == str.charAt(R))
                        R++;

                    Z[i] = R - L;
                    R--;
                }
            }
        }
    }
}
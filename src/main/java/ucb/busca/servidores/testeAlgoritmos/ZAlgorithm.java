package ucb.busca.servidores.testeAlgoritmos;

import org.json.JSONObject;
import ucb.busca.servidores.util.ArtigoCientifico;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.regex.Matcher;

public class ZAlgorithm implements SearchAlgorithm{

    @Override
    public void buscaSubString(String text, String substring){

        Set<Integer> chavesSubstringsEncontradas = new HashSet<>();
        List<ArtigoCientifico> artigoCientificoList = new ArrayList<>();

        //TODO: Avaliar se faz sentido nossa busca ser case insensitive
        String concat = substring + "$" + text;

        int l = concat.length();

        int[] Z = new int[l];

        getZarr(concat, Z);

        for(int i = 0; i < l; ++i){

            if(Z[i] == substring.length()){
                StringBuilder substringBuilder = new StringBuilder(substring);
                Matcher matcher = PATTERN.matcher(substringBuilder);

                int indexSubstringNoTexto = i - substring.length() - 1;
                System.out.println("Caractére do começo da substring: " + text.charAt(indexSubstringNoTexto));

                while(!matcher.find()) {
//                    System.out.println("String antes:" + substringBuilder);
                    substringBuilder.insert(0, text.charAt(indexSubstringNoTexto));
                    indexSubstringNoTexto--;
//                    System.out.println("String depois:" + substringBuilder + "\n");
                }
                String chaveSubstring = matcher.group();
                System.out.printf("""
                        CHAVE DO OBJETO QUE CONTÉM A SUBSTRING FOI ENCONTRADO!!!
                        Chave: %s %n%n""", chaveSubstring);

                Integer chaveComoNumero = Integer.valueOf(chaveSubstring.replaceAll("\"", ""));
                chavesSubstringsEncontradas.add(chaveComoNumero);
            }
        }

        preencheListaArtigosCientificos(artigoCientificoList, chavesSubstringsEncontradas, text);

    }

    private void preencheListaArtigosCientificos(List<ArtigoCientifico> artigosCientificos, Set<Integer> chavesSubstrings, String text){
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
package ucb.busca.servidores.testeAlgoritmos;

import org.json.JSONObject;
import ucb.busca.servidores.util.ArtigoCientifico;

import java.util.*;

public class ZAlgorithm implements SearchAlgorithm{

    @Override
    public List<ArtigoCientifico> buscaSubString(JSONObject json, String substring){
        System.out.println("SUBSTRING A SER ENCONTRADO: " + substring);
        LinkedList<ArtigoCientifico> artigosCientificos = new LinkedList<>();

        JSONObject tituloObj = json.getJSONObject("title");
        JSONObject abstractObj = json.getJSONObject("abstract");
        JSONObject labelObj = json.getJSONObject("label");

        Iterator<String> chaves = tituloObj.keys();

        while(chaves.hasNext()){
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

    private boolean existsSubstring(String substring, String text){

        String concat = substring + "$" + text;

        int l = concat.length();

        int[] Z = new int[l];

        getZarr(concat, Z);

        boolean isFound = false;

        for(int i = 0; i < l; ++i){

            if(Z[i] == substring.length()){
                isFound = true;
                break;
            }
        }
        return isFound;
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
package ucb.busca.servidores.testeAlgoritmos;

import java.util.regex.Matcher;

public class ZAlgorithm implements SearchAlgorithm{

    @Override
    public void buscaSubString(String text, String substring){

        //TODO: Avaliar se faz sentido nossa busca ser case insensitive
        String concat = substring.toLowerCase() + "$" + text.toLowerCase();

        int l = concat.length();

        int[] Z = new int[l];

        getZarr(concat, Z);

        for(int i = 0; i < l; ++i){

            if(Z[i] == substring.length()){
                StringBuilder substringBuilder = new StringBuilder(substring);
                Matcher matcher = pattern.matcher(substringBuilder);

                int indexSubstringNoTexto = i - substring.length() - 1;
                System.out.println("Caractére do começo da substring: " + text.charAt(indexSubstringNoTexto));

                while(!matcher.find()) {
                    System.out.println("String antes:" + substringBuilder);
                    substringBuilder.insert(0, text.charAt(indexSubstringNoTexto));
                    indexSubstringNoTexto--;
                    System.out.println("String depois:" + substringBuilder + "\n");
                }
                System.out.printf("""
                        CHAVE DO OBJETO QUE CONTÉM A SUBSTRING FOI ENCONTRADO!!!
                        Chave: %s %n%n""", matcher.group());
            }
        }
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